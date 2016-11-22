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
var Payment = (function () {
    function Payment(id, supplierId, method) {
        this.id = id;
        this.supplierId = supplierId;
        this.method = method;
    }
    Payment = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [Number, Number, String])
    ], Payment);
    return Payment;
}());
exports.Payment = Payment;
var PaymentList = (function () {
    function PaymentList(list) {
        this.paymentList = list;
    }
    PaymentList = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [Array])
    ], PaymentList);
    return PaymentList;
}());
exports.PaymentList = PaymentList;
var List = (function () {
    function List() {
        this.items = [];
    }
    List.prototype.size = function () {
        return this.items.length;
    };
    List.prototype.add = function (value) {
        this.items.push(value);
    };
    List.prototype.get = function (index) {
        return this.items[index];
    };
    List = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [])
    ], List);
    return List;
}());
exports.List = List;
//# sourceMappingURL=payment.service.js.map