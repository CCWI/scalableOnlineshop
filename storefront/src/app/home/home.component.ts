import {Component} from '@angular/core';
import {Http} from '@angular/http';
import {TokenService} from '../token.service';
import {JwtHelper} from 'angular2-jwt';

@Component({
    selector: 'as-home',
    templateUrl: 'app/home/home.html',
    styleUrls: [
        'app/home/home.css'
    ]
})
export class HomeComponent {

    private jwtHelper: JwtHelper = new JwtHelper();
    private _username: String;
    private _failedLogins: number;
    private _loggedIn: boolean;

    constructor(private _http: Http, private _tokenService: TokenService) {

      this._loggedIn = this._tokenService.getToken() !== undefined;
      console.log(this._loggedIn);
      console.log('token: ' + this._tokenService.getToken());
      if ( this._loggedIn ) {
        this._username = this.jwtHelper.decodeToken(this._tokenService.getToken()).username;
        this._failedLogins = this.jwtHelper.decodeToken(this._tokenService.getToken()).failedLogins;
        console.log('username: ' + this._username + ', failedLogins: ' + this._failedLogins);
      }
    }
}
