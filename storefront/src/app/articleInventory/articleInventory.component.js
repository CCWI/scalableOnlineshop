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
var token_service_1 = require('../token.service');
var router_1 = require('@angular/router');
var login_service_1 = require('./../login.service');
var ArticleInventoryComponent = (function () {
    function ArticleInventoryComponent(_http, _tokenService, _loginService, _router, _route) {
        this._http = _http;
        this._tokenService = _tokenService;
        this._loginService = _loginService;
        this._router = _router;
        this._route = _route;
    }
    ArticleInventoryComponent.prototype.ngOnInit = function () {
        var _this = this;
        this._route.params.subscribe(function (params) {
            return _this.getArticles(_this.searchstring = params.enter, _this.distance = params.distance);
        }, function (error) { return _this.handleError(error); });
    };
    ArticleInventoryComponent.prototype.getArticles = function (enter, distance) {
        var _this = this;
        if (this.searchstring !== undefined) {
            // alert('1 searchstring: ' + this.searchstring + ', distance: ' + this.distance);
            this.backend = new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8083/articles/search?enter='
                + this.searchstring + '&distance=' + this.distance);
        }
        else {
            this.backend = new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8083/articles');
        }
        this.backend.getAllArticle()
            .subscribe(function (data) { return _this.articles = data; }, function (error) { return _this.handleError(error); }, function () { return console.log('Get all Items complete'); });
    };
    ArticleInventoryComponent.prototype.handleError = function (error) {
        this._loginService.setLogin(true);
    };
    ArticleInventoryComponent.prototype.onSelect = function (id) {
        console.log('onSelect aufgerufen mit der id: ' + id);
        this._router.navigate(['/article', id]);
    };
    ArticleInventoryComponent = __decorate([
        core_1.Component({
            selector: 'as-kebab-case',
            templateUrl: 'app/articleInventory/articleInventory.html',
            styleUrls: ['app/articleInventory/articleInventory.css']
        }), 
        __metadata('design:paramtypes', [http_1.Http, token_service_1.TokenService, login_service_1.LoginService, router_1.Router, router_1.ActivatedRoute])
    ], ArticleInventoryComponent);
    return ArticleInventoryComponent;
}());
exports.ArticleInventoryComponent = ArticleInventoryComponent;
//# sourceMappingURL=articleInventory.component.js.map