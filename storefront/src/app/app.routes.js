"use strict";
var router_1 = require('@angular/router');
var home_component_1 = require('./home/home.component');
var article_component_1 = require('./article/article.component');
var articleInventory_component_1 = require('./articleInventory/articleInventory.component');
var logout_component_1 = require('./logout/logout.component');
var register_component_1 = require('./register/register.component');
var about_component_1 = require('./about/about.component');
var contact_component_1 = require('./contact/contact.component');
var impressum_component_1 = require('./impressum/impressum.component');
var shoppingcart_component_1 = require('./shoppingcart/shoppingcart.component');
var createArticle_component_1 = require('./createArticle/createArticle.component');
var profile_component_1 = require('./profile/profile.component');
var login_component_1 = require('./login/login.component');
var delivery_component_1 = require('./delivery/delivery.component');
var payment_component_1 = require('./payment/payment.component');
var confirmation_component_1 = require('./confirmation/confirmation.component');
var agb_component_1 = require('./agb/agb.component');
var sendOrder_component_1 = require('./sendOrder/sendOrder.component');
exports.ROUTES = [
    { path: '', component: home_component_1.HomeComponent },
    { path: 'article', component: articleInventory_component_1.ArticleInventoryComponent },
    { path: 'article/:id', component: article_component_1.ArticleComponent },
    { path: 'logout', component: logout_component_1.LogoutComponent },
    { path: 'login', component: login_component_1.LoginComponent },
    { path: 'shoppingcart', component: shoppingcart_component_1.ShoppingcartComponent },
    { path: 'about', component: about_component_1.AboutComponent },
    { path: 'contact', component: contact_component_1.ContactComponent },
    { path: 'impressum', component: impressum_component_1.ImpressumComponent },
    { path: 'register', component: register_component_1.RegisterComponent },
    { path: 'insertArticle', component: createArticle_component_1.CreateArticleComponent },
    { path: 'profile', component: profile_component_1.ProfileComponent },
    { path: 'checkout/delivery', component: delivery_component_1.DeliveryComponent },
    { path: 'checkout/payment', component: payment_component_1.PaymentComponent },
    { path: 'checkout/confirmation', component: confirmation_component_1.ConfirmationComponent },
    { path: 'agb', component: agb_component_1.AGBComponent },
    { path: 'checkout/order', component: sendOrder_component_1.SendOrderComponent }
];
exports.routing = router_1.RouterModule.forRoot(exports.ROUTES);
//# sourceMappingURL=app.routes.js.map