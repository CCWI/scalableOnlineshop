import { Component, ViewChild, Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';
import { LoginService } from './login.service';
import { TokenService } from './token.service';
import { NavibarService } from './navbar.service';
import { CookieService } from 'angular2-cookie/core';

@Component({
    selector: 'as-main-app',
    providers: [CookieService],
    templateUrl: 'app/app.html',
    styleUrls: ['app/app.css']
})

export class AppComponent implements OnInit {

    @ViewChild('myModal')
    modal: ModalComponent;

    private _loginLogoutButtonText: string;

    constructor(private _router: Router, private _loginService: LoginService,
    private _navibarService: NavibarService, private _tokenService: TokenService) {
        _loginService.loginNeeded$.subscribe(
          needForLogin => {
            this.startLogin();
          });
          _navibarService.userHasToken$.subscribe(
              (hasAuthToken: boolean) => this.showLogoutButton(hasAuthToken)
          );

    }

    ngOnInit() {
      if (this._tokenService.getToken() === undefined) {
        this.showLogoutButton(false);
      } else {
        this.showLogoutButton(true);
      }
    }

    close() {
        this.modal.close();
        this._router.navigate(['']);
    }

    startLogin() {
        this.modal.open();
    }

    onLoginSubmit(username, password) {
      this._loginService.onSubmit(username, password);
      this.close();
    }

    showLogoutButton(changes) {
        if (changes) {
            this._loginLogoutButtonText = 'Logout';
        } else {
            this._loginLogoutButtonText = 'Login';
        }
    }

    searchArticle(enter: string, distance: number) {
      let searchstring = new SearchParameter(enter, distance);
      this._router.navigate(['article', searchstring]);
    }

    redirectUser() {
        if (this._loginLogoutButtonText === 'Logout') {
            this._router.navigate(['logout']);
        } else {
            this._loginService.setLogin(true);
        }
    }
}

@Injectable()
export class SearchParameter {

  private enter: string;
  private distance: number;

  constructor(enter: string, distance: number) {
    this.enter = enter;
    this.distance = distance;
  }
}
