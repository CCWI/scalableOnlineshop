import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { routing } from './app.routes';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { HttpModule } from '@angular/http'; // Http, ConnectionBackend, HTTP_PROVIDERS,
import { TokenService } from './token.service';
import { LoginService } from './login.service';
import { CookieService } from 'angular2-cookie/core';
import { AuthHttp } from 'angular2-jwt';
import { NavibarService } from './navbar.service';
import { ArticleComponent } from './article/article.component';
import { ArticleInventoryComponent } from './articleInventory/articleInventory.component';
import { LogoutComponent } from './logout/logout.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { ImpressumComponent } from './impressum/impressum.component';
import { ShoppingcartComponent } from './shoppingcart/shoppingcart.component';
import { RouterModule } from '@angular/router';
import { CreateArticleComponent } from './createArticle/createArticle.component';
import { ProfileComponent } from './profile/profile.component';
import { DeliveryComponent } from './delivery/delivery.component';
import { PaymentComponent } from './payment/payment.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { User } from './user.service';

@NgModule({
  imports:      [ BrowserModule, routing, RouterModule, HttpModule, FormsModule ],
  declarations: [ AppComponent, HomeComponent, HeaderComponent, ArticleComponent,
                  ArticleInventoryComponent, LogoutComponent, RegisterComponent,
                  AboutComponent, ContactComponent, ImpressumComponent, ShoppingcartComponent,
                  CreateArticleComponent, ProfileComponent, DeliveryComponent,
                  PaymentComponent, ConfirmationComponent, LoginComponent ],
  providers:    [ TokenService, LoginService,
                  CookieService, AuthHttp, NavibarService, User ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
