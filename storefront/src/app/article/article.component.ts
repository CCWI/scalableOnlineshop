import {Component, OnInit, Injectable} from '@angular/core';
// import {CORE_DIRECTIVES} from '@angular/common';
import {Http} from '@angular/http';
import {BackendcallService} from './../backendcall.service';
import {TokenService} from '../token.service';
// import {ROUTER_DIRECTIVES} from '@angular/router';
import {Router, ActivatedRoute} from '@angular/router';
import {LoginService} from './../login.service';
import {JwtHelper} from 'angular2-jwt';

@Component({
    selector: 'as-article',
    templateUrl: 'app/article/article.html',
    // directives: [ROUTER_DIRECTIVES, CORE_DIRECTIVES],
    // viewProviders: [HTTP_PROVIDERS],
    providers: [TokenService]
})

export class ArticleComponent implements OnInit {

      public  selectedArticle: Article;
      private jwtHelper: JwtHelper = new JwtHelper();
      private _quantityToHigh: boolean;

      constructor(private _http: Http,
                  private _tokenService: TokenService,
                  private _loginService: LoginService,
                  private _activeRoute: ActivatedRoute,
                  private _router: Router) {
        this._quantityToHigh = false;
        console.log('Article-Component constructor()');
        _loginService.loginNeeded$.subscribe(
          needForLogin => {
            needForLogin = true;
          });
        let token = this._tokenService.getToken();
        console.log(this.jwtHelper.decodeToken(token),
                    this.jwtHelper.getTokenExpirationDate(token),
                    this.jwtHelper.isTokenExpired(token));
      }

      ngOnInit() {
        console.log(this._tokenService.getToken());
        this._activeRoute.params.subscribe((params: {id: string}) => {
        new BackendcallService(this._http, 'token', this._tokenService.getToken(),
            'http://192.168.99.100:8083/articles/' + params.id).getArticle()
            .subscribe(( data: Article ) => this.selectedArticle = data,
                error => this.handleError(error),
                () => console.log('Get all Items complete' + JSON.stringify(this.selectedArticle)));
        });
      }

      handleError(error: any) {
        this._loginService.setLogin(true);
      }

      onShoppingcartSubmit(quantity) {
        if (quantity <= this.selectedArticle.articleStock ) {
        console.log('Shoppingcart-Eintrag:'
              + this.jwtHelper.decodeToken(this._tokenService.getToken()).userId + ', '
              + this.selectedArticle.supplierId + ', ' + quantity);
        new BackendcallService(this._http, 'token', this._tokenService.getToken(),
            'http://192.168.99.100:8084/shoppingcart/')
            .postArticleToShoppingcart(this.selectedArticle.id,
              this.jwtHelper.decodeToken(this._tokenService.getToken()).userId, quantity)
              .subscribe(( data: any ) => this._router.navigate(['/shoppingcart']),
                  error => this.handleError(error));
        } else {
          this._quantityToHigh = true;
        }
      }

      goBackToArticleInventory() {
        this._router.navigate(['article']);
      }
}

@Injectable()
export class Article {
  public id: number;
  public articleTitle: string;
  public articleDescription: string;
  public articleEAN: string;
  public articlePrice: number;
  public articleStock: number;
  public supplierId: number;
  public quantity: number;
  public supplierName: string;

  constructor(articleTitle: string, articleDescription: string, articleEAN: string,
                    articlePrice: number, articleStock: number, supplierId: number) {
      this.articleTitle = articleTitle;
      this.articleDescription = articleDescription;
      this.articleEAN = articleEAN;
      this.articlePrice = articlePrice;
      this.articleStock = articleStock;
      this.supplierId = supplierId;
  }
}
