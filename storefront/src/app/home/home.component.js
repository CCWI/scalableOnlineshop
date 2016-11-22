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
var token_service_1 = require('../token.service');
var angular2_jwt_1 = require('angular2-jwt');
var HomeComponent = (function () {
    function HomeComponent(_http, _tokenService) {
        this._http = _http;
        this._tokenService = _tokenService;
        this.jwtHelper = new angular2_jwt_1.JwtHelper();
        this._loggedIn = this._tokenService.getToken() !== undefined;
        console.log(this._loggedIn);
        console.log('token: ' + this._tokenService.getToken());
        if (this._loggedIn) {
            this._username = this.jwtHelper.decodeToken(this._tokenService.getToken()).username;
            this._failedLogins = this.jwtHelper.decodeToken(this._tokenService.getToken()).failedLogins;
            console.log('username: ' + this._username + ', failedLogins: ' + this._failedLogins);
        }
    }
    HomeComponent = __decorate([
        core_1.Component({
            selector: 'as-home',
            templateUrl: 'app/home/home.html',
            styleUrls: [
                'app/home/home.css'
            ]
        }), 
        __metadata('design:paramtypes', [http_1.Http, token_service_1.TokenService])
    ], HomeComponent);
    return HomeComponent;
}());
exports.HomeComponent = HomeComponent;
//# sourceMappingURL=home.component.js.map