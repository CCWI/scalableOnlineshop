import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Http} from '@angular/http';
import {BackendcallService} from './../backendcall.service';
import {TokenService} from './../token.service';
import {LoginService} from './../login.service';
import {Article} from './../article/article.component';
import {JwtHelper} from 'angular2-jwt';
import {User} from './../user.service';
import {Delivery} from './../delivery/delivery.component';

@Component({
    selector: 'as-kebab-case',
    templateUrl: 'app/createArticle/createArticle.html',
    styleUrls: ['app/createArticle/createArticle.css']
})

export class CreateArticleComponent {

    private _newArticle: Article;
    private _newDelivery: Delivery;
    private _userId: number;
    private _supplierId: number;
    private jwtHelper: JwtHelper = new JwtHelper();

    constructor(private _http: Http, private _router: Router, private _tokenService: TokenService,
        private _loginService: LoginService) {
        _loginService.loginNeeded$.subscribe(
            needForLogin => {
                needForLogin = true;
            });
        if (this._tokenService.getToken() == null) {
            this.handleError('loginNeeded');
        } else {
            this._userId = (this.jwtHelper.decodeToken(this._tokenService.getToken())).userId;
            new BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8087/user/'
                + this._userId).getUserData().subscribe((data: User) => this._supplierId = data.supplierId,
                error => this.handleError(error));
        }
    }

    onCreateArticle(articleTitle: string, articleDescription: string, articleEAN: string,
        articlePrice: number, articleStock: number, shippmentReady: boolean,
        shippingDays: number, shippingMethod: string) {
        this._newArticle = new Article(articleTitle, articleDescription, articleEAN, articlePrice, articleStock, this._supplierId);
        this._newDelivery = new Delivery(0, shippmentReady, shippingDays, shippingMethod);

        new BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8083/articles/')
            .postArticle(this._newArticle)
            .subscribe((userId: Number) =>
            new BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8085/shippments/')
                .postDelivery(null, this._newDelivery.shipmentReady, this._newDelivery.shippingDays, this._newDelivery.shippingMethod)
                .subscribe((anything: any) => this._router.navigateByUrl('/'),
                error => console.log(<any>error)),
            error => console.log(<any>error));
    }

    handleError(error: any) {
        this._loginService.setLogin(true);
    }
}
