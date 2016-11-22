import {Component, Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Router} from '@angular/router';
import {BackendcallService} from './../backendcall.service';
import {TokenService} from '../token.service';
import {LoginService} from './../login.service';

@Component({
    selector: 'as-delivery',
    templateUrl: 'app/delivery/delivery.html',
    styleUrls: ['app/articleCheckout/articleCheckout.css']
})

export class DeliveryComponent {

    private _delivery: Delivery[];

    constructor(private _http: Http, private _tokenService: TokenService,
                private _loginService: LoginService, private _router: Router) {
      _loginService.loginNeeded$.subscribe(
          needForLogin => {
            needForLogin = true;
      });
      new BackendcallService(this._http, 'token', this._tokenService.getToken(),
          'http://192.168.99.100:8085/shippments/').getDelivery()
          .subscribe(( data: Delivery[] ) => this._delivery = data,
              error => this.handleError(error),
              () => console.log('Get all Items in delivery complete: ' + JSON.stringify(this._delivery))
      );
    }

    handleError(error: any) {
      this._loginService.setLogin(true);
    }

    goBackToShoppingcart() {
      this._router.navigate(['shoppingcart']);
    }

    goOn() {
      this._router.navigate(['checkout/payment']);
    }

}

@Injectable()
export class Delivery {
  public id: number;
  public shipmentReady: boolean;
  public shippingDays: number;
  public shippingMethod: string;

  constructor(id: number, shipmentReady: boolean,
              shippingDays: number, shippingMethod: string) {
      this.id = id;
      this.shipmentReady = shipmentReady;
      this.shippingDays = shippingDays;
      this.shippingMethod = shippingMethod;
  }
}
