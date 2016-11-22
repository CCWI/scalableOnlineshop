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
var core_2 = require('angular2-cookie/core');
var navbar_service_1 = require('./navbar.service');
var Token = (function () {
    function Token(token) {
        this.jwtToken = token;
    }
    Token.prototype.getJwtToken = function () {
        return this.jwtToken;
    };
    Token = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [String])
    ], Token);
    return Token;
}());
exports.Token = Token;
var TokenService = (function () {
    function TokenService(_cookieservice, _navibarservice) {
        this._cookieservice = _cookieservice;
        this._navibarservice = _navibarservice;
        this._cookieService = _cookieservice;
        this.tokenstorekey = 'jwtAuthToken';
        this._navibarService = _navibarservice;
        this._navibarService.userHasToken$.subscribe(function (hasAuthToken) {
            hasAuthToken = true;
        });
    }
    TokenService.prototype.saveToken = function (value) {
        console.log('Token wird gespeichert: ' + JSON.stringify(value));
        this._cookieService.put(this.tokenstorekey, JSON.stringify(value));
        this._navibarService.hasToken(true);
    };
    TokenService.prototype.getToken = function () {
        var token = this._cookieService.get(this.tokenstorekey);
        console.log('tokenservice gettoken: ' + token);
        return token;
    };
    TokenService.prototype.clearLoginToken = function () {
        this._cookieService.remove(this.tokenstorekey);
        this._navibarService.hasToken(false);
    };
    TokenService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [core_2.CookieService, navbar_service_1.NavibarService])
    ], TokenService);
    return TokenService;
}());
exports.TokenService = TokenService;
//# sourceMappingURL=token.service.js.map