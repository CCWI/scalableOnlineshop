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
var backendcall_service_1 = require('./../backendcall.service');
var login_service_1 = require('./../login.service');
var token_service_1 = require('./../token.service');
var router_1 = require('@angular/router');
var angular2_jwt_1 = require('angular2-jwt');
var ConfirmationComponent = (function () {
    function ConfirmationComponent(_http, _loginService, _tokenService, _router) {
        this._http = _http;
        this._loginService = _loginService;
        this._tokenService = _tokenService;
        this._router = _router;
        this.jwtHelper = new angular2_jwt_1.JwtHelper();
        this.readAgbs = false;
        _loginService.loginNeeded$.subscribe(function (needForLogin) {
            needForLogin = true;
        });
    }
    ConfirmationComponent.prototype.ngOnInit = function () {
        var _this = this;
        console.log('init start');
        this.shoppingcart = [];
        this.articles = [];
        this.totalOfCart = 0;
        this.totalOfCartNetto = 0;
        if (this._tokenService.getToken() !== undefined) {
            new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8084/shoppingcart/' +
                (this.jwtHelper.decodeToken(this._tokenService.getToken())).userId)
                .getAllShoppingcartItems()
                .subscribe(function (data) { return _this.getDetailsOfArticle(data); }, function (error) { return _this.handleError(error); }, function () { return console.log('Get all Items complete'); });
        }
        else {
            console.log('else: setLogin true');
            this._loginService.setLogin(true);
        }
    };
    ConfirmationComponent.prototype.getDetailsOfArticle = function (cartData) {
        var _this = this;
        this.shoppingcart = cartData;
        this.articles = new Array();
        // count Items for navigation list
        var cartCounter;
        for (var _i = 0, cartData_1 = cartData; _i < cartData_1.length; _i++) {
            var item = cartData_1[_i];
            cartCounter = cartCounter + item.quantity;
        }
        var _loop_1 = function(item) {
            new backendcall_service_1.BackendcallService(this_1._http, 'token', this_1._tokenService.getToken(), 'http://192.168.99.100:8083/articles/' + item.articleId)
                .getArticle().subscribe(function (data) { return _this.combineArticleData(data, item.quantity); }, function (error) { return _this.handleError(error); }, function () { return console.log('Get all Items complete'); });
        };
        var this_1 = this;
        for (var _a = 0, cartData_2 = cartData; _a < cartData_2.length; _a++) {
            var item = cartData_2[_a];
            _loop_1(item);
        }
    };
    ConfirmationComponent.prototype.combineArticleData = function (data, quantity) {
        var _this = this;
        data.quantity = quantity;
        this.totalOfCart = this.totalOfCart + data.articlePrice * quantity;
        this.totalOfCartNetto = Math.round(this.totalOfCart / 119 * 100);
        new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8087/user/supplierId/' + data.supplierId)
            .getUserData().subscribe(function (user) { return _this.completeArticleData(data, user.firstname, user.lastname); }, function (error) { return _this.articles.push(data); }, function () { return console.log('Get all Items complete'); });
    };
    ConfirmationComponent.prototype.completeArticleData = function (data, firstname, lastname) {
        data.supplierName = firstname + ' ' + lastname;
        this.articles.push(data);
    };
    ConfirmationComponent.prototype.handleError = function (error) {
        if (error.status === 401) {
            this._loginService.setLogin(true);
        }
        if (error.status === 404) {
            this._router.navigate(['/']);
        }
    };
    ConfirmationComponent.prototype.confirm = function () {
        this._router.navigate(['checkout/order']);
    };
    ConfirmationComponent = __decorate([
        core_1.Component({
            selector: 'as-confirmation',
            templateUrl: 'app/confirmation/confirmation.html',
            styleUrls: ['app/articleCheckout/articleCheckout.css']
        }), 
        __metadata('design:paramtypes', [http_1.Http, login_service_1.LoginService, token_service_1.TokenService, router_1.Router])
    ], ConfirmationComponent);
    return ConfirmationComponent;
}());
exports.ConfirmationComponent = ConfirmationComponent;
//# sourceMappingURL=confirmation.component.js.map