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
var router_1 = require('@angular/router');
var backendcall_service_1 = require('./../backendcall.service');
var token_service_1 = require('../token.service');
var login_service_1 = require('./../login.service');
var DeliveryComponent = (function () {
    function DeliveryComponent(_http, _tokenService, _loginService, _router) {
        var _this = this;
        this._http = _http;
        this._tokenService = _tokenService;
        this._loginService = _loginService;
        this._router = _router;
        _loginService.loginNeeded$.subscribe(function (needForLogin) {
            needForLogin = true;
        });
        new backendcall_service_1.BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8085/shippments/').getDelivery()
            .subscribe(function (data) { return _this._delivery = data; }, function (error) { return _this.handleError(error); }, function () { return console.log('Get all Items in delivery complete: ' + JSON.stringify(_this._delivery)); });
    }
    DeliveryComponent.prototype.handleError = function (error) {
        this._loginService.setLogin(true);
    };
    DeliveryComponent.prototype.goBackToShoppingcart = function () {
        this._router.navigate(['shoppingcart']);
    };
    DeliveryComponent.prototype.goOn = function () {
        this._router.navigate(['checkout/payment']);
    };
    DeliveryComponent = __decorate([
        core_1.Component({
            selector: 'as-delivery',
            templateUrl: 'app/delivery/delivery.html',
            styleUrls: ['app/articleCheckout/articleCheckout.css']
        }), 
        __metadata('design:paramtypes', [http_1.Http, token_service_1.TokenService, login_service_1.LoginService, router_1.Router])
    ], DeliveryComponent);
    return DeliveryComponent;
}());
exports.DeliveryComponent = DeliveryComponent;
var Delivery = (function () {
    function Delivery(id, shipmentReady, shippingDays, shippingMethod) {
        this.id = id;
        this.shipmentReady = shipmentReady;
        this.shippingDays = shippingDays;
        this.shippingMethod = shippingMethod;
    }
    Delivery = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [Number, Boolean, Number, String])
    ], Delivery);
    return Delivery;
}());
exports.Delivery = Delivery;
//# sourceMappingURL=delivery.component.js.map