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
var user_service_1 = require('./../user.service');
var angular2_jwt_1 = require('angular2-jwt');
var ProfileComponent = (function () {
    function ProfileComponent(_http, _router, _tokenService, _loginService) {
        var _this = this;
        this._http = _http;
        this._router = _router;
        this._tokenService = _tokenService;
        this._loginService = _loginService;
        this.jwtHelper = new angular2_jwt_1.JwtHelper();
        this._paymentModel = [];
        this._userModel = new user_service_1.User(0, '', '', '', '', '', '', '', '', 0, 0);
        _loginService.loginNeeded$.subscribe(function (needForLogin) {
            needForLogin = true;
        });
        if (this._tokenService.getToken() == null) {
            this.handleError('loginNeeded');
        }
        else {
            this._userId = (this.jwtHelper.decodeToken(this._tokenService.getToken())).userId;
            new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8087/user/'
                + this._userId).getUserData().subscribe(function (data) { return _this.setupUserModel(data); }, function (error) { return _this.handleError(error); });
        }
    }
    ProfileComponent.prototype.onSubmitProfileUpdate = function () {
        var _this = this;
        new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8087/user/' + this._userId)
            .updateUserData(this._userModel.id, this._userModel.firstname, this._userModel.lastname, this._userModel.address, this._userModel.postcode, this._userModel.city, this._userModel.country)
            .subscribe(function (data) { return _this.onRegisterSuccess(); }, function (error) { return console.log(error); });
    };
    ProfileComponent.prototype.setupUserModel = function (user) {
        this._userModel = user;
        this._userModel.username = (this.jwtHelper.decodeToken(this._tokenService.getToken())).username;
        this.getPaymentMethods(user);
    };
    ProfileComponent.prototype.getPaymentMethods = function (user) {
        var _this = this;
        console.log('getPaymentMethods started');
        this._paymentModel = [];
        new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8086/payments/' + user.supplierId)
            .getPayment().subscribe(function (data) { return _this.setPaymentModelAndFlag(data); }, function (error) { return _this._savedPaymentMethods = false; });
    };
    ProfileComponent.prototype.setPaymentModelAndFlag = function (data) {
        this._paymentModel = data;
        if (data === undefined) {
            this._savedPaymentMethods = false;
        }
        this._savedPaymentMethods = true;
    };
    ProfileComponent.prototype.handleError = function (error) {
        this._loginService.setLogin(true);
    };
    ProfileComponent.prototype.onRegisterSuccess = function () {
        alert('Erfolgreich angemeldet.');
        this._router.navigateByUrl('/');
    };
    ProfileComponent.prototype.onDeletePaymethod = function (id) {
        var _this = this;
        var _loop_1 = function(paymethod) {
            if (paymethod.id === id) {
                var index_1 = this_1._paymentModel.indexOf(paymethod, 0);
                if (index_1 > -1) {
                    new backendcall_service_1.BackendcallService(this_1._http, 'token', this_1._tokenService.getToken(), 'http://192.168.99.100:8086/payments/' + id)
                        .deleteItem().subscribe(function (data) { return _this._paymentModel.splice(index_1, 1); }, function (error) { return _this.handleError(error); }, function () { return console.log('Get all Items complete'); });
                }
            }
        };
        var this_1 = this;
        for (var _i = 0, _a = this._paymentModel; _i < _a.length; _i++) {
            var paymethod = _a[_i];
            _loop_1(paymethod);
        }
    };
    ProfileComponent.prototype.onSubmitNewPaymethod = function (paymethod) {
        var _this = this;
        new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8086/payments')
            .postPaymentMethod(this._userModel.supplierId, paymethod).subscribe(function (data) {
            return _this._paymentModel.push(data);
        }, function (error) { return _this.handleError(error); }, function () { return console.log('Get all Items complete'); });
    };
    ProfileComponent = __decorate([
        core_1.Component({
            selector: 'as-kebab-case',
            templateUrl: 'app/profile/profile.html',
            styleUrls: ['app/profile/profile.css']
        }), 
        __metadata('design:paramtypes', [http_1.Http, router_1.Router, token_service_1.TokenService, login_service_1.LoginService])
    ], ProfileComponent);
    return ProfileComponent;
}());
exports.ProfileComponent = ProfileComponent;
//# sourceMappingURL=profile.component.js.map