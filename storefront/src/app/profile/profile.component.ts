import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Http} from '@angular/http';
import {BackendcallService} from './../backendcall.service';
import {TokenService} from './../token.service';
import {LoginService} from './../login.service';
import {User} from './../user.service';
import {JwtHelper} from 'angular2-jwt';
import {Payment} from './../payment/payment.service';

@Component({
    selector: 'as-kebab-case',
    templateUrl: 'app/profile/profile.html',
    styleUrls: ['app/profile/profile.css']
})

export class ProfileComponent {

    private _userId: Number;
    private _userModel: User;
    private _paymentModel: Payment[];
    private _savedPaymentMethods: boolean;
    private jwtHelper: JwtHelper = new JwtHelper();

    constructor(private _http: Http, private _router: Router,
        private _tokenService: TokenService, private _loginService: LoginService) {
        this._paymentModel = [];
        this._userModel = new User(0, '', '', '', '', '', '', '', '', 0, 0);
        _loginService.loginNeeded$.subscribe(
            needForLogin => {
                needForLogin = true;
            });
        if (this._tokenService.getToken() == null) {
            this.handleError('loginNeeded');
        } else {
            this._userId = (this.jwtHelper.decodeToken(this._tokenService.getToken())).userId;
            new BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8087/user/'
                + this._userId).getUserData().subscribe((data: User) => this.setupUserModel(data),
                error => this.handleError(error));
        }
    }

    onSubmitProfileUpdate() {
        new BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8087/user/' + this._userId)
            .updateUserData(this._userModel.id, this._userModel.firstname, this._userModel.lastname, this._userModel.address,
            this._userModel.postcode, this._userModel.city, this._userModel.country)
            .subscribe((data: User) => this.onRegisterSuccess(),
            error => console.log(<any>error));
    }

    setupUserModel(user: User) {
        this._userModel = user;
        this._userModel.username = (this.jwtHelper.decodeToken(this._tokenService.getToken())).username;
        this.getPaymentMethods(user);
    }

    getPaymentMethods(user: User) {
      console.log('getPaymentMethods started');
      this._paymentModel = [];
    new BackendcallService(this._http, 'token', this._tokenService.getToken(), 'http://192.168.99.100:8086/payments/' + user.supplierId)
        .getPayment().subscribe((data: Payment[]) => this.setPaymentModelAndFlag(data),
        error => this._savedPaymentMethods = false);
    }

    setPaymentModelAndFlag(data: Payment[]) {
      this._paymentModel = data;
      if (data === undefined) {
        this._savedPaymentMethods = false;
      }
      this._savedPaymentMethods = true;
    }

    handleError(error: any) {
        this._loginService.setLogin(true);
    }

    onRegisterSuccess() {
        alert('Erfolgreich angemeldet.');
        this._router.navigateByUrl('/');
    }

    onDeletePaymethod(id: number) {
        for (let paymethod of this._paymentModel) {
            if (paymethod.id === id) {
                let index = this._paymentModel.indexOf(paymethod, 0);
                if (index > -1) {
                    new BackendcallService(this._http, 'token', this._tokenService.getToken(),
                        'http://192.168.99.100:8086/payments/' + id)
                        .deleteItem().subscribe((data: any) => this._paymentModel.splice(index, 1),
                        error => this.handleError(error),
                        () => console.log('Get all Items complete'));
                }
            }
        }
    }

    onSubmitNewPaymethod(paymethod: string) {
      new BackendcallService(this._http, 'token', this._tokenService.getToken(),
          'http://192.168.99.100:8086/payments')
          .postPaymentMethod(this._userModel.supplierId, paymethod).subscribe((data: Payment) =>
          this._paymentModel.push(data),
          error => this.handleError(error),
          () => console.log('Get all Items complete'));
    }
}
