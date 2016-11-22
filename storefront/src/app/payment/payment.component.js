"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var http_1 = require('@angular/http');
var login_service_1 = require('./../login.service');
var token_service_1 = require('./../token.service');
var router_1 = require('@angular/router');
var backendcall_service_1 = require('./../backendcall.service');
var angular2_jwt_1 = require('angular2-jwt');
var payment_service_1 = require('./payment.service');
var PaymentComponent = (function () {
    function PaymentComponent(_http, _loginService, _tokenService, _router) {
        this._http = _http;
        this._loginService = _loginService;
        this._tokenService = _tokenService;
        this._router = _router;
        this.jwtHelper = new angular2_jwt_1.JwtHelper();
        this.paymentList = new Array();
        _loginService.loginNeeded$.subscribe(function (needForLogin) {
            needForLogin = true;
        });
    }
    PaymentComponent.prototype.ngOnInit = function () {
        var _this = this;
        if (this._tokenService.getToken() !== undefined) {
            new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8084/shoppingcart/' +
                (this.jwtHelper.decodeToken(this._tokenService.getToken())).userId)
                .getAllShoppingcartItems()
                .subscribe(function (data) { return _this.getArticleOfShoppingcart(data); }, function (error) { return _this.handleError(error); }, function () { return console.log('Get all Items complete'); });
        }
        else {
            this._loginService.setLogin(true);
        }
    };
    PaymentComponent.prototype.getArticleOfShoppingcart = function (cart) {
        var _this = this;
        this.paymentArticleList = new Array;
        for (var _i = 0, cart_1 = cart; _i < cart_1.length; _i++) {
            var cartItem = cart_1[_i];
            new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8083/articles/' + cartItem.articleId)
                .getArticle().subscribe(function (data) { return _this.getPaymentMethodsForArticles(data); }, function (error) { return _this.handleError(error); }, function () { return console.log('Get all Items complete'); });
        }
    };
    PaymentComponent.prototype.getPaymentMethodsForArticles = function (article) {
        var _this = this;
        this.paymentArticleList.push(article);
        new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8086/payments/' + article.supplierId)
            .getPayment().subscribe(function (data) { return _this.paymentList.push(new payment_service_1.PaymentList(data)); }, function (error) { return _this.handleError(error); }, function () { return console.log('Get all Items complete' + JSON.stringify(_this.paymentList)); });
    };
    PaymentComponent.prototype.handleError = function (error) {
        if (error.status === 401) {
            this._loginService.setLogin(true);
        }
        if (error.status === 404) {
            this._router.navigate(['/']);
        }
    };
    PaymentComponent.prototype.goBackToDelivery = function () {
        this._router.navigate(['checkout/delivery']);
    };
    PaymentComponent.prototype.goOn = function () {
        this._router.navigate(['checkout/confirmation']);
    };
    PaymentComponent = __decorate([
        core_1.Component({
            selector: 'as-payment',
            templateUrl: 'app/payment/payment.html',
            styleUrls: ['app/articleCheckout/articleCheckout.css']
        }), 
        __metadata('design:paramtypes', [http_1.Http, login_service_1.LoginService, token_service_1.TokenService, router_1.Router])
    ], PaymentComponent);
    return PaymentComponent;
}());
exports.PaymentComponent = PaymentComponent;
//# sourceMappingURL=payment.component.js.map