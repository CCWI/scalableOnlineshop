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
var router_1 = require('@angular/router');
var http_1 = require('@angular/http');
var backendcall_service_1 = require('./../backendcall.service');
var token_service_1 = require('./../token.service');
var login_service_1 = require('./../login.service');
var article_component_1 = require('./../article/article.component');
var angular2_jwt_1 = require('angular2-jwt');
var delivery_component_1 = require('./../delivery/delivery.component');
var CreateArticleComponent = (function () {
    function CreateArticleComponent(_http, _router, _tokenService, _loginService) {
        var _this = this;
        this._http = _http;
        this._router = _router;
        this._tokenService = _tokenService;
        this._loginService = _loginService;
        this.jwtHelper = new angular2_jwt_1.JwtHelper();
        _loginService.loginNeeded$.subscribe(function (needForLogin) {
            needForLogin = true;
        });
        if (this._tokenService.getToken() == null) {
            this.handleError('loginNeeded');
        }
        else {
            this._userId = (this.jwtHelper.decodeToken(this._tokenService.getToken())).userId;
            new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8087/user/'
                + this._userId).getUserData().subscribe(function (data) { return _this._supplierId = data.supplierId; }, function (error) { return _this.handleError(error); });
        }
    }
    CreateArticleComponent.prototype.onCreateArticle = function (articleTitle, articleDescription, articleEAN, articlePrice, articleStock, shippmentReady, shippingDays, shippingMethod) {
        var _this = this;
        this._newArticle = new article_component_1.Article(articleTitle, articleDescription, articleEAN, articlePrice, articleStock, this._supplierId);
        this._newDelivery = new delivery_component_1.Delivery(0, shippmentReady, shippingDays, shippingMethod);
        new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8083/articles/')
            .postArticle(this._newArticle)
            .subscribe(function (userId) {
            return new backendcall_service_1.BackendcallService(_this._http, 'token', _this._tokenService.getToken(), 'http://192.168.99.100:8085/shippments/')
                .postDelivery(null, _this._newDelivery.shipmentReady, _this._newDelivery.shippingDays, _this._newDelivery.shippingMethod)
                .subscribe(function (anything) { return _this._router.navigateByUrl('/'); }, function (error) { return console.log(error); });
        }, function (error) { return console.log(error); });
    };
    CreateArticleComponent.prototype.handleError = function (error) {
        this._loginService.setLogin(true);
    };
    CreateArticleComponent = __decorate([
        core_1.Component({
            selector: 'as-kebab-case',
            templateUrl: 'app/createArticle/createArticle.html',
            styleUrls: ['app/createArticle/createArticle.css']
        }), 
        __metadata('design:paramtypes', [http_1.Http, router_1.Router, token_service_1.TokenService, login_service_1.LoginService])
    ], CreateArticleComponent);
    return CreateArticleComponent;
}());
exports.CreateArticleComponent = CreateArticleComponent;
//# sourceMappingURL=createArticle.component.js.map