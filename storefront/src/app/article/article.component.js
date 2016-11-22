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
// import {CORE_DIRECTIVES} from '@angular/common';
var http_1 = require('@angular/http');
var backendcall_service_1 = require('./../backendcall.service');
var token_service_1 = require('../token.service');
// import {ROUTER_DIRECTIVES} from '@angular/router';
var router_1 = require('@angular/router');
var login_service_1 = require('./../login.service');
var angular2_jwt_1 = require('angular2-jwt');
var ArticleComponent = (function () {
    function ArticleComponent(_http, _tokenService, _loginService, _activeRoute, _router) {
        this._http = _http;
        this._tokenService = _tokenService;
        this._loginService = _loginService;
        this._activeRoute = _activeRoute;
        this._router = _router;
        this.jwtHelper = new angular2_jwt_1.JwtHelper();
        this._quantityToHigh = false;
        console.log('Article-Component constructor()');
        _loginService.loginNeeded$.subscribe(function (needForLogin) {
            needForLogin = true;
        });
        var token = this._tokenService.getToken();
        console.log(this.jwtHelper.decodeToken(token), this.jwtHelper.getTokenExpirationDate(token), this.jwtHelper.isTokenExpired(token));
    }
    ArticleComponent.prototype.ngOnInit = function () {
        var _this = this;
        console.log(this._tokenService.getToken());
        this._activeRoute.params.subscribe(function (params) {
            new backendcall_service_1.BackendcallService(_this._http, 'token', _this._tokenService.getToken(), 'http://192.168.99.100:8083/articles/' + params.id).getArticle()
                .subscribe(function (data) { return _this.selectedArticle = data; }, function (error) { return _this.handleError(error); }, function () { return console.log('Get all Items complete' + JSON.stringify(_this.selectedArticle)); });
        });
    };
    ArticleComponent.prototype.handleError = function (error) {
        this._loginService.setLogin(true);
    };
    ArticleComponent.prototype.onShoppingcartSubmit = function (quantity) {
        var _this = this;
        if (quantity <= this.selectedArticle.articleStock) {
            console.log('Shoppingcart-Eintrag:'
                + this.jwtHelper.decodeToken(this._tokenService.getToken()).userId + ', '
                + this.selectedArticle.supplierId + ', ' + quantity);
            new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8084/shoppingcart/')
                .postArticleToShoppingcart(this.selectedArticle.id, this.jwtHelper.decodeToken(this._tokenService.getToken()).userId, quantity)
                .subscribe(function (data) { return _this._router.navigate(['/shoppingcart']); }, function (error) { return _this.handleError(error); });
        }
        else {
            this._quantityToHigh = true;
        }
    };
    ArticleComponent.prototype.goBackToArticleInventory = function () {
        this._router.navigate(['article']);
    };
    ArticleComponent = __decorate([
        core_1.Component({
            selector: 'as-article',
            templateUrl: 'app/article/article.html',
            // directives: [ROUTER_DIRECTIVES, CORE_DIRECTIVES],
            // viewProviders: [HTTP_PROVIDERS],
            providers: [token_service_1.TokenService]
        }), 
        __metadata('design:paramtypes', [http_1.Http, token_service_1.TokenService, login_service_1.LoginService, router_1.ActivatedRoute, router_1.Router])
    ], ArticleComponent);
    return ArticleComponent;
}());
exports.ArticleComponent = ArticleComponent;
var Article = (function () {
    function Article(articleTitle, articleDescription, articleEAN, articlePrice, articleStock, supplierId) {
        this.articleTitle = articleTitle;
        this.articleDescription = articleDescription;
        this.articleEAN = articleEAN;
        this.articlePrice = articlePrice;
        this.articleStock = articleStock;
        this.supplierId = supplierId;
    }
    Article = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [String, String, String, Number, Number, Number])
    ], Article);
    return Article;
}());
exports.Article = Article;
//# sourceMappingURL=article.component.js.map