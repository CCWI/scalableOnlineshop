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
var platform_browser_1 = require('@angular/platform-browser');
var forms_1 = require('@angular/forms');
var app_component_1 = require('./app.component');
var app_routes_1 = require('./app.routes');
var home_component_1 = require('./home/home.component');
var header_component_1 = require('./header/header.component');
var http_1 = require('@angular/http'); // Http, ConnectionBackend, HTTP_PROVIDERS,
var token_service_1 = require('./token.service');
var login_service_1 = require('./login.service');
var core_2 = require('angular2-cookie/core');
var angular2_jwt_1 = require('angular2-jwt');
var navbar_service_1 = require('./navbar.service');
var article_component_1 = require('./article/article.component');
var articleInventory_component_1 = require('./articleInventory/articleInventory.component');
var logout_component_1 = require('./logout/logout.component');
var login_component_1 = require('./login/login.component');
var register_component_1 = require('./register/register.component');
var about_component_1 = require('./about/about.component');
var contact_component_1 = require('./contact/contact.component');
var impressum_component_1 = require('./impressum/impressum.component');
var shoppingcart_component_1 = require('./shoppingcart/shoppingcart.component');
var router_1 = require('@angular/router');
var createArticle_component_1 = require('./createArticle/createArticle.component');
var profile_component_1 = require('./profile/profile.component');
var delivery_component_1 = require('./delivery/delivery.component');
var payment_component_1 = require('./payment/payment.component');
var confirmation_component_1 = require('./confirmation/confirmation.component');
var user_service_1 = require('./user.service');
var AppModule = (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        core_1.NgModule({
            imports: [platform_browser_1.BrowserModule, app_routes_1.routing, router_1.RouterModule, http_1.HttpModule, forms_1.FormsModule],
            declarations: [app_component_1.AppComponent, home_component_1.HomeComponent, header_component_1.HeaderComponent, article_component_1.ArticleComponent,
                articleInventory_component_1.ArticleInventoryComponent, logout_component_1.LogoutComponent, register_component_1.RegisterComponent,
                about_component_1.AboutComponent, contact_component_1.ContactComponent, impressum_component_1.ImpressumComponent, shoppingcart_component_1.ShoppingcartComponent,
                createArticle_component_1.CreateArticleComponent, profile_component_1.ProfileComponent, delivery_component_1.DeliveryComponent,
                payment_component_1.PaymentComponent, confirmation_component_1.ConfirmationComponent, login_component_1.LoginComponent],
            providers: [token_service_1.TokenService, login_service_1.LoginService,
                core_2.CookieService, angular2_jwt_1.AuthHttp, navbar_service_1.NavibarService, user_service_1.User],
            bootstrap: [app_component_1.AppComponent]
        }), 
        __metadata('design:paramtypes', [])
    ], AppModule);
    return AppModule;
}());
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map