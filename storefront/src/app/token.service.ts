import {Injectable} from '@angular/core';
import {CookieService} from 'angular2-cookie/core';
import {NavibarService} from './navbar.service';

@Injectable()
export class Token {
  private jwtToken: string;

  constructor(token: string) {
      this.jwtToken = token;
  }

  public getJwtToken() {
    return this.jwtToken;
  }
}

@Injectable()
export class TokenService {

  private tokenstorekey: string;
  private _cookieService: CookieService;
  private _navibarService: NavibarService;

  constructor(private _cookieservice: CookieService, private _navibarservice: NavibarService) {
      this._cookieService = _cookieservice;
      this.tokenstorekey = 'jwtAuthToken';
      this._navibarService = _navibarservice;

      this._navibarService.userHasToken$.subscribe(
          hasAuthToken => {
              hasAuthToken = true;
          });
  }

  public saveToken(value: Token) {
      console.log('Token wird gespeichert: ' + JSON.stringify(value));
      this._cookieService.put(this.tokenstorekey, JSON.stringify(value));
      this._navibarService.hasToken(true);
  }

  public getToken() {
      let token = this._cookieService.get(this.tokenstorekey);
      console.log('tokenservice gettoken: ' + token);
      return token;
  }

  public clearLoginToken() {
    this._cookieService.remove(this.tokenstorekey);
    this._navibarService.hasToken(false);
  }
}
