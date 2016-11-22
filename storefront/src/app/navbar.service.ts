import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';


@Injectable()
export class NavibarService {

    public hasAuthToken = new Subject<boolean>();
    public userHasToken$ = this.hasAuthToken.asObservable();

    hasToken(hasToken: boolean) {
        this.hasAuthToken.next(hasToken);
    }
}
