import {Component, OnInit} from '@angular/core';
import {Http} from '@angular/http';
import {BackendcallService} from './../backendcall.service';
import {LoginService} from './../login.service';
import {TokenService} from './../token.service';
import {Router} from '@angular/router';
import {Shoppingcart} from './../shoppingcart/shoppingcart.service';
import {JwtHelper} from 'angular2-jwt';
import {Article} from './../article/article.component';
import {User} from './../user.service';

@Component({
    selector: 'as-confirmation',
    templateUrl: 'app/confirmation/confirmation.html',
    styleUrls: ['app/articleCheckout/articleCheckout.css']
})

export class ConfirmationComponent implements OnInit {

    private jwtHelper: JwtHelper = new JwtHelper();
    private totalOfCart: number;
    private totalOfCartNetto: number;
    private shoppingcart: Shoppingcart[];
    private articles: Article[];
    private readAgbs: boolean;

    constructor(private _http: Http, private _loginService: LoginService,
        private _tokenService: TokenService, private _router: Router) {
          this.readAgbs = false;
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
            this._router.navigate(['/']);
        }
    }

    confirm() {
        this._router.navigate(['checkout/order']);
    }

}
