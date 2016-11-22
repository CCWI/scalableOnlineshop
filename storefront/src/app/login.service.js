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
var Subject_1 = require('rxjs/Subject');
var router_1 = require('@angular/router');
var http_1 = require('@angular/http');
var token_service_1 = require('./token.service');
var backendcall_service_1 = require('./backendcall.service');
var LoginService = (function () {
    function LoginService(_http, _router, _tokenService) {
        this._http = _http;
        this._router = _router;
        this._tokenService = _tokenService;
        this.needForLogin = new Subject_1.Subject();
        this.loginNeeded$ = this.needForLogin.asObservable();
        this.submitted = false;
    }
    LoginService.prototype.setLogin = function (needForLogin) {
        console.log('setLogin() handleError: ' + needForLogin);
        this.needForLogin.next(needForLogin);
    };
    LoginService.prototype.onSubmit = function (username, password) {
        this.submitted = true;
        console.log('loginservice onsubmit: ' + username + ', ' + password);
        this.authenticateForToken(username, password);
    };
    LoginService.prototype.authenticateForToken = function (user, pw) {
        var _this = this;
        console.log('loginservice authenticatefortoken: ' + user + ', ' + pw);
        new backendcall_service_1.BackendcallService(this._http, user, pw, 'http://192.168.99.100:8088/authentication')
            .getToken().subscribe(function (data) { return _this.saveTokenAndNavigate(data); }, function (error) { return console.log(error); });
    };
    LoginService.prototype.saveTokenAndNavigate = function (token) {
        this._tokenService.saveToken(token);
        this._router.navigateByUrl('/login');
    };
    LoginService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http, router_1.Router, token_service_1.TokenService])
    ], LoginService);
    return LoginService;
}());
exports.LoginService = LoginService;
//# sourceMappingURL=login.service.js.map