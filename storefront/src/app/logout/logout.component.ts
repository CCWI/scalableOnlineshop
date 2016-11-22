import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TokenService} from './../token.service';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/timer';

@Component({
  selector: 'as-logout',
  templateUrl: 'app/logout/logout.html',
  styleUrls: ['app/logout/logout.css']
})

export class LogoutComponent implements OnInit {

    constructor(private _router: Router, private _tokenService: TokenService) {
      console.log('Logout constructor!');
    }

    ngOnInit() {
      console.log('Logout init!');
      this._tokenService.clearLoginToken();
      this.timeCounter();
    }

    public timeCounter() {
      Observable.timer(5000).subscribe((a: any) => this.navigate());
    }

    navigate() {
      this._router.navigateByUrl('/');
    }
}
