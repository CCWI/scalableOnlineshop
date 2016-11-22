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
var token_service_1 = require('./token.service');
var backendcall_service_1 = require('./backendcall.service');
var UserService = (function () {
    function UserService(_http, _router, _tokenService) {
        this._http = _http;
        this._router = _router;
        this._tokenService = _tokenService;
    }
    UserService.prototype.getUserInformation = function (user, pw) {
        var _this = this;
        new backendcall_service_1.BackendcallService(this._http, user, pw, 'http://192.168.99.100:8088/user')
            .getToken().subscribe(function (data) { return _this._tokenService.saveToken(data)
            && console.log(_this._tokenService.getToken()); }, function (error) { return console.log(error); });
    };
    UserService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http, router_1.Router, token_service_1.TokenService])
    ], UserService);
    return UserService;
}());
exports.UserService = UserService;
var User = (function () {
    function User(id, username, firstname, lastname, password, address, postcode, city, country, supplierId, failedlogins) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.country = country;
        this.supplierId = supplierId;
        this.failedlogins = failedlogins;
    }
    User = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [Number, String, String, String, String, String, String, String, String, Number, Number])
    ], User);
    return User;
}());
exports.User = User;
//# sourceMappingURL=user.service.js.map