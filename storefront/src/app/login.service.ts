import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {Router} from '@angular/router';
import {Http} from '@angular/http';
import {TokenService, Token} from './token.service';
import {BackendcallService} from './backendcall.service';

@Injectable()
export class LoginService {

  needForLogin = new Subject<boolean>();
  loginNeeded$ = this.needForLogin.asObservable();
  private submitted = false;

  constructor(private _http: Http, private _router: Router, private _tokenService: TokenService) {
  }

  setLogin(needForLogin: boolean) {
    console.log('setLogin() handleError: ' + needForLogin);
    this.needForLogin.next(needForLogin);
  }

  onSubmit(username, password) {
      this.submitted = true;
      console.log('loginservice onsubmit: ' + username + ', ' + password);
      this.authenticateForToken(username, password);
  }

  authenticateForToken(user: string, pw: string) {
    console.log('loginservice authenticatefortoken: ' + user + ', ' + pw);
     new BackendcallService(this._http, user, pw, 'http://192.168.99.100:8088/authentication')
              .getToken().subscribe((data: Token) => this.saveTokenAndNavigate(data)
              , error =>  console.log(<any>error));
  }

  saveTokenAndNavigate(token: Token) {
    this._tokenService.saveToken(token);
    this._router.navigateByUrl('/login');
  }
}
