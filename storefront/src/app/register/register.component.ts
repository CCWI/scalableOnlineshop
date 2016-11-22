import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Http} from '@angular/http';
import {BackendcallService} from './../backendcall.service';
import {TokenService, Token} from './../token.service';
import {User} from './../user.service';

@Component({
    selector: 'as-kebab-case',
    templateUrl: 'app/register/register.html'
})

export class RegisterComponent {

    private _newUser: User;

    constructor(private _http: Http, private _router: Router, private _tokenService: TokenService) {
    }

    onRegisterSubmit(username: string, firstname: string, lastname: string,
                    password: string, address: string, postcode: string, city: string, country: string) {
      this._newUser = new User(0, username, firstname, lastname, password, address, postcode, city, country, 0, 0);

      new BackendcallService(this._http, null, null, 'http://192.168.99.100:8088/user/')
               .saveAuthData(username, password)
               .subscribe(
                    (userId: number) => this.getTokenToSaveUser(userId),
                    error =>  console.log(<any>error));
    }

    getTokenToSaveUser(userId: number) {
         this._newUser.id = userId;
         new BackendcallService(this._http, this._newUser.username, this._newUser.password, 'http://192.168.99.100:8088/authentication')
         .getToken().subscribe((data: Token) => this.saveUserDetail(data)
         , error =>  console.log(<any>error));
      }

    saveUserDetail(token: Token) {
      this._tokenService.saveToken(token);
      new BackendcallService(this._http, 'token', JSON.stringify(token), 'http://192.168.99.100:8087/user/')
              .saveUserData(this._newUser.id, this._newUser.firstname, this._newUser.lastname, this._newUser.address,
                this._newUser.postcode, this._newUser.city, this._newUser.country)
              .subscribe(
                   (data: User) => this.onRegisterSuccess(),
                   error =>  console.log(<any>error));
    }


    onRegisterSuccess() {
      alert('Erfolgreich angemeldet.');
      this._router.navigateByUrl('/');
    }
}
