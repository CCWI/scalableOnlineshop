import { RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ArticleComponent } from './article/article.component';
import { ArticleInventoryComponent } from './articleInventory/articleInventory.component';
import { LogoutComponent } from './logout/logout.component';
import { RegisterComponent } from './register/register.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { ImpressumComponent } from './impressum/impressum.component';
import { ShoppingcartComponent } from './shoppingcart/shoppingcart.component';
import { CreateArticleComponent } from './createArticle/createArticle.component';
import { ProfileComponent } from './profile/profile.component';
import { LoginComponent } from './login/login.component';
import { DeliveryComponent } from './delivery/delivery.component';
import { PaymentComponent } from './payment/payment.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { AGBComponent } from './agb/agb.component';
import { SendOrderComponent } from './sendOrder/sendOrder.component';

export const ROUTES = [
  { path: '', component: HomeComponent },
  { path: 'article', component: ArticleInventoryComponent },
  { path: 'article/:id', component: ArticleComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'login', component: LoginComponent },
  { path: 'shoppingcart', component: ShoppingcartComponent },
  { path: 'about', component: AboutComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'impressum', component: ImpressumComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'insertArticle', component: CreateArticleComponent},
  { path: 'profile', component: ProfileComponent},
  { path: 'checkout/delivery', component: DeliveryComponent },
  { path: 'checkout/payment', component: PaymentComponent },
  { path: 'checkout/confirmation', component: ConfirmationComponent },
  { path: 'agb', component: AGBComponent },
  { path: 'checkout/order', component: SendOrderComponent}
];

export const routing = RouterModule.forRoot(ROUTES);
