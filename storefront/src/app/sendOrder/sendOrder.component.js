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
var token_service_1 = require('./../token.service');
var Observable_1 = require('rxjs/Observable');
require('rxjs/add/observable/timer');
var SendOrderComponent = (function () {
    function SendOrderComponent(_router, _tokenService) {
        this._router = _router;
        this._tokenService = _tokenService;
    }
    SendOrderComponent.prototype.ngOnInit = function () {
        this.timeCounter();
    };
    SendOrderComponent.prototype.timeCounter = function () {
        var _this = this;
        Observable_1.Observable.timer(2000).subscribe(function (a) { return _this.navigate(); });
    };
    SendOrderComponent.prototype.navigate = function () {
        this._router.navigateByUrl('/');
    };
    SendOrderComponent = __decorate([
        core_1.Component({
            selector: 'as-login-page',
            templateUrl: 'app/sendOrder/sendOrder.html',
            styleUrls: ['app/sendOrder/sendOrder.css']
        }), 
        __metadata('design:paramtypes', [router_1.Router, token_service_1.TokenService])
    ], SendOrderComponent);
    return SendOrderComponent;
}());
exports.SendOrderComponent = SendOrderComponent;
//# sourceMappingURL=sendOrder.component.js.map