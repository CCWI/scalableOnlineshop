import {Component, ChangeDetectionStrategy} from '@angular/core';

@Component({
    selector: 'as-header',
    templateUrl: 'app/header/header.html',
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderComponent {
}
