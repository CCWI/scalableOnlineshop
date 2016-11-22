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
var ng2_bs3_modal_1 = require('ng2-bs3-modal/ng2-bs3-modal');
var login_service_1 = require('./login.service');
var token_service_1 = require('./token.service');
var navbar_service_1 = require('./navbar.service');
var core_2 = require('angular2-cookie/core');
var AppComponent = (function () {
    function AppComponent(_router, _loginService, _navibarService, _tokenService) {
        var _this = this;
        this._router = _router;
        this._loginService = _loginService;
        this._navibarService = _navibarService;
        this._tokenService = _tokenService;
        _loginService.loginNeeded$.subscribe(function (needForLogin) {
            _this.startLogin();
        });
        _navibarService.userHasToken$.subscribe(function (hasAuthToken) { return _this.showLogoutButton(hasAuthToken); });
    }
    AppComponent.prototype.ngOnInit = function () {
        if (this._tokenService.getToken() === undefined) {
            this.showLogoutButton(false);
        }
        else {
            this.showLogoutButton(true);
        }
    };
    AppComponent.prototype.close = function () {
        this.modal.close();
        this._router.navigate(['']);
    };
    AppComponent.prototype.startLogin = function () {
        this.modal.open();
    };
    AppComponent.prototype.onLoginSubmit = function (username, password) {
        this._loginService.onSubmit(username, password);
        this.close();
    };
    AppComponent.prototype.showLogoutButton = function (changes) {
        if (changes) {
            this._loginLogoutButtonText = 'Logout';
        }
        else {
            this._loginLogoutButtonText = 'Login';
        }
    };
    AppComponent.prototype.searchArticle = function (enter, distance) {
        var searchstring = new SearchParameter(enter, distance);
        this._router.navigate(['article', searchstring]);
    };
    AppComponent.prototype.redirectUser = function () {
        if (this._loginLogoutButtonText === 'Logout') {
            this._router.navigate(['logout']);
        }
        else {
            this._loginService.setLogin(true);
        }
    };
    __decorate([
        core_1.ViewChild('myModal'), 
        __metadata('design:type', ng2_bs3_modal_1.ModalComponent)
    ], AppComponent.prototype, "modal", void 0);
    AppComponent = __decorate([
        core_1.Component({
            selector: 'as-main-app',
            providers: [core_2.CookieService],
            templateUrl: 'app/app.html',
            styleUrls: ['app/app.css']
        }), 
        __metadata('design:paramtypes', [router_1.Router, login_service_1.LoginService, navbar_service_1.NavibarService, token_service_1.TokenService])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
var SearchParameter = (function () {
    function SearchParameter(enter, distance) {
        this.enter = enter;
        this.distance = distance;
    }
    SearchParameter = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [String, Number])
    ], SearchParameter);
    return SearchParameter;
}());
exports.SearchParameter = SearchParameter;
//# sourceMappingURL=app.component.js.map