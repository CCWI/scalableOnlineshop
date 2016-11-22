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
var LogoutComponent = (function () {
    function LogoutComponent(_router, _tokenService) {
        this._router = _router;
        this._tokenService = _tokenService;
        console.log('Logout constructor!');
    }
    LogoutComponent.prototype.ngOnInit = function () {
        console.log('Logout init!');
        this._tokenService.clearLoginToken();
        this.timeCounter();
    };
    LogoutComponent.prototype.timeCounter = function () {
        var _this = this;
        Observable_1.Observable.timer(5000).subscribe(function (a) { return _this.navigate(); });
    };
    LogoutComponent.prototype.navigate = function () {
        this._router.navigateByUrl('/');
    };
    LogoutComponent = __decorate([
        core_1.Component({
            selector: 'as-logout',
            templateUrl: 'app/logout/logout.html',
            styleUrls: ['app/logout/logout.css']
        }), 
        __metadata('design:paramtypes', [router_1.Router, token_service_1.TokenService])
    ], LogoutComponent);
    return LogoutComponent;
}());
exports.LogoutComponent = LogoutComponent;
//# sourceMappingURL=logout.component.js.map