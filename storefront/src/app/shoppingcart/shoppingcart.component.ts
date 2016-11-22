import {Component, OnInit} from '@angular/core';
import {Http} from '@angular/http';
import {BackendcallService} from './../backendcall.service';
import {LoginService} from './../login.service';
import {TokenService} from './../token.service';
import {Router} from '@angular/router';
import {Shoppingcart} from './shoppingcart.service';
import {JwtHelper} from 'angular2-jwt';
import {Article} from './../article/article.component';
import {User} from './../user.service';

@Component({
    selector: 'as-kebab-case',
    templateUrl: 'app/shoppingcart/shoppingcart.html'
})

export class ShoppingcartComponent implements OnInit {

    private shoppingcart: Shoppingcart[];
    private articles: Article[];
    private jwtHelper: JwtHelper = new JwtHelper();
    private totalOfCart: number;
    private totalOfCartNetto: number;


    constructor(private _http: Http, private _loginService: LoginService,
        private _tokenService: TokenService, private _router: Router) {
        _loginService.loginNeeded$.subscribe(
            needForLogin => {
                needForLogin = true;
            });
    }


    ngOnInit() {
        console.log('init start');
        this.shoppingcart = [];
        this.articles = [];
        this.totalOfCart = 0;
        this.totalOfCartNetto = 0;
        if (this._tokenService.getToken() !== undefined) {
            new BackendcallService(this._http, 'token', this._tokenService.getToken(),
                'http://192.168.99.100:8084/shoppingcart/' +
                (this.jwtHelper.decodeToken(this._tokenService.getToken())).userId)
                .getAllShoppingcartItems()
                .subscribe((data: Shoppingcart[]) => this.getDetailsOfArticle(data),
                error => this.handleError(error),
                () => console.log('Get all Items complete'));
        } else {
            console.log('else: setLogin true');
            this._loginService.setLogin(true);
        }
    }

    recalc() {
      let total: number;
      total = 0;
      for (let article of this.articles) {
         total = total + article.articlePrice * article.quantity;
      }
      this.totalOfCart = total;
      this.totalOfCartNetto = Math.round( total / 119 * 100 );
    }

    onDestroy() {
        // persist changes
        if (this._tokenService.getToken() !== undefined) {
            for (let art of this.articles) {
                new BackendcallService(this._http, 'token', this._tokenService.getToken(),
                    'http://192.168.99.100:8084/shoppingcart/' + art.id)
                    .updateArticleToShoppingcart(art.id,
                    this.jwtHelper.decodeToken(this._tokenService.getToken()).userId, art.quantity)
                    .subscribe((data: any) => console.log('Successfully saved Shoppingcart'),
                    error => this.handleError(error),
                    () => console.log('Get all Items complete'));
            }
        }
    }


    getDetailsOfArticle(cartData: Shoppingcart[]) {
        this.shoppingcart = cartData;
        this.articles = new Array();

        // count Items for navigation list
        let cartCounter: number;
        for (let item of cartData) {
          cartCounter = cartCounter + item.quantity;
        }

        for (let item of cartData) {
            new BackendcallService(this._http, 'token', this._tokenService.getToken(),
                'http://192.168.99.100:8083/articles/' + item.articleId)
                .getArticle().subscribe((data: Article) => this.combineArticleData(data, item.quantity),
                error => this.handleError(error),
                () => console.log('Get all Items complete'));
        }
    }


    combineArticleData(data: Article, quantity: number) {
        data.quantity = quantity;
        this.totalOfCart = this.totalOfCart + data.articlePrice * quantity;
        this.totalOfCartNetto = Math.round( this.totalOfCart / 119 * 100 );
        new BackendcallService(this._http, 'token', this._tokenService.getToken(),
            'http://192.168.99.100:8087/user/supplierId/' + data.supplierId)
            .getUserData().subscribe((user: User) => this.completeArticleData(data, user.firstname, user.lastname),
            error => this.articles.push(data),
            () => console.log('Get all Items complete'));
    }


    completeArticleData(data: Article, firstname: string, lastname: string) {
        data.supplierName = firstname + ' ' + lastname;
        this.articles.push(data);
    }


    handleError(error: any) {
        if (error.status === 401) {
            this._loginService.setLogin(true);
        }
        if (error.status === 404) {
            this._router.navigate(['article']).then((data: any) =>
              alert('Es liegen keine Artikel im Warenkorb!')
            ).catch((e: any) => alert('catch: ' + e));
        }
    }


    goOnShopping() {
        this.onDestroy();
        this._router.navigate(['article']);
    }


    goToCheckout() {
        this.onDestroy();
        this._router.navigate(['checkout/delivery']);
    }


    onDeleteEntry(articleId: number) {

        for (let cart of this.shoppingcart) {
          if (cart.articleId === articleId) {
            let index = this.shoppingcart.indexOf(cart, 0);
            if (index > -1) {
               this.totalOfCart = this.totalOfCart - (this.shoppingcart[index].quantity * this.articles[index].articlePrice);
               if ( this.totalOfCart <= 0) {
                 this.totalOfCart = 0;
                 this.totalOfCartNetto = 0;
               } else {
                 this.totalOfCartNetto = this.totalOfCart * 100 / 119;
               }
               this.shoppingcart.splice(index, 1);
               this.articles.splice(index, 1);
               if (this.shoppingcart.length <= 0) {
                 this.totalOfCart = 0;
                 this.totalOfCartNetto = 0;
               }
            }
          }
        }

        console.log('Eintrag soll gelöscht werden: ' + articleId);
        new BackendcallService(this._http, 'token', this._tokenService.getToken(),
            'http://192.168.99.100:8084/shoppingcart/' + articleId)
            .deleteItem().subscribe((data: any) => this._router.navigateByUrl('/shoppingcart'),
            error => this.handleError(error),
            () => console.log('Get all Items complete'));
    }
}
