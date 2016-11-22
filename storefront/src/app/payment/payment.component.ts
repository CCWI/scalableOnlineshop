import {Component, OnInit} from '@angular/core';
import {Http} from '@angular/http';
import {LoginService} from './../login.service';
import {TokenService} from './../token.service';
import {Router} from '@angular/router';
import {BackendcallService} from './../backendcall.service';
import {Shoppingcart} from './../shoppingcart/shoppingcart.service';
import {JwtHelper} from 'angular2-jwt';
import {Article} from './../article/article.component';
import {Payment, PaymentList} from './payment.service';

@Component({
    selector: 'as-payment',
    templateUrl: 'app/payment/payment.html',
    styleUrls: ['app/articleCheckout/articleCheckout.css']
})
export class PaymentComponent implements OnInit {

  paymentList: PaymentList[];
  payments: Payment[];
  paymentArticleList: Article[];
  private jwtHelper: JwtHelper = new JwtHelper();

  constructor(private _http: Http, private _loginService: LoginService,
      private _tokenService: TokenService, private _router: Router) {
        this.paymentList = new Array();
      _loginService.loginNeeded$.subscribe(
          needForLogin => {
              needForLogin = true;
          });
  }

  ngOnInit() {
      if (this._tokenService.getToken() !== undefined) {
          new BackendcallService(this._http, 'token', this._tokenService.getToken(),
              'http://192.168.99.100:8084/shoppingcart/' +
              (this.jwtHelper.decodeToken(this._tokenService.getToken())).userId)
              .getAllShoppingcartItems()
              .subscribe((data: Shoppingcart[]) => this.getArticleOfShoppingcart(data),
              error => this.handleError(error),
              () => console.log('Get all Items complete'));
      } else {
          this._loginService.setLogin(true);
      }
  }

  getArticleOfShoppingcart(cart: Shoppingcart[]) {
    this.paymentArticleList = new Array;
    for (let cartItem of cart) {
        new BackendcallService(this._http, 'token', this._tokenService.getToken(),
            'http://192.168.99.100:8083/articles/' + cartItem.articleId)
            .getArticle().subscribe((data: Article) => this.getPaymentMethodsForArticles(data),
            error => this.handleError(error),
            () => console.log('Get all Items complete'));
    }
  }

  getPaymentMethodsForArticles(article: Article) {
     this.paymentArticleList.push(article);
     new BackendcallService(this._http, 'token', this._tokenService.getToken(),
        'http://192.168.99.100:8086/payments/' + article.supplierId)
        .getPayment().subscribe((data: Payment[]) => this.paymentList.push(new PaymentList(data)),
        error => this.handleError(error),
        () => console.log('Get all Items complete' + JSON.stringify(this.paymentList)));
  }

  handleError(error: any) {
      if (error.status === 401) {
          this._loginService.setLogin(true);
      }
      if (error.status === 404) {
          this._router.navigate(['/']);
      }
  }

  goBackToDelivery() {
    this._router.navigate(['checkout/delivery']);
  }

  goOn() {
    this._router.navigate(['checkout/confirmation']);
  }
}
