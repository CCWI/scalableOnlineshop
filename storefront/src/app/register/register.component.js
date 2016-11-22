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
var user_service_1 = require('./../user.service');
var RegisterComponent = (function () {
    function RegisterComponent(_http, _router, _tokenService) {
        this._http = _http;
        this._router = _router;
        this._tokenService = _tokenService;
    }
    RegisterComponent.prototype.onRegisterSubmit = function (username, firstname, lastname, password, address, postcode, city, country) {
        var _this = this;
        this._newUser = new user_service_1.User(0, username, firstname, lastname, password, address, postcode, city, country, 0, 0);
        new backendcall_service_1.BackendcallService(this._http, null, null, 'http://192.168.99.100:8088/user/')
            .saveAuthData(username, password)
            .subscribe(function (userId) { return _this.getTokenToSaveUser(userId); }, function (error) { return console.log(error); });
    };
    RegisterComponent.prototype.getTokenToSaveUser = function (userId) {
        var _this = this;
        this._newUser.id = userId;
        new backendcall_service_1.BackendcallService(this._http, this._newUser.username, this._newUser.password, 'http://192.168.99.100:8088/authentication')
            .getToken().subscribe(function (data) { return _this.saveUserDetail(data); }, function (error) { return console.log(error); });
    };
    RegisterComponent.prototype.saveUserDetail = function (token) {
        var _this = this;
        this._tokenService.saveToken(token);
        new backendcall_service_1.BackendcallService(this._http, 'token', JSON.stringify(token), 'http://192.168.99.100:8087/user/')
            .saveUserData(this._newUser.id, this._newUser.firstname, this._newUser.lastname, this._newUser.address, this._newUser.postcode, this._newUser.city, this._newUser.country)
            .subscribe(function (data) { return _this.onRegisterSuccess(); }, function (error) { return console.log(error); });
    };
    RegisterComponent.prototype.onRegisterSuccess = function () {
        alert('Erfolgreich angemeldet.');
        this._router.navigateByUrl('/');
    };
    RegisterComponent = __decorate([
        core_1.Component({
            selector: 'as-kebab-case',
            templateUrl: 'app/register/register.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http, router_1.Router, token_service_1.TokenService])
    ], RegisterComponent);
    return RegisterComponent;
}());
exports.RegisterComponent = RegisterComponent;
//# sourceMappingURL=register.component.js.map