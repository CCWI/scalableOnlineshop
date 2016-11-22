import {Injectable} from '@angular/core';

@Injectable()
export class Payment {
    public id: number;
    public supplierId: number;
    public method: string;

    constructor(id: number, supplierId: number, method: string) {
        this.id = id;
        this.supplierId = supplierId;
        this.method = method;
    }
}

@Injectable()
export class PaymentList {
    public paymentList: Payment[];

    constructor(list: Payment[]) {
        this.paymentList = list;
    }
}

@Injectable()
export class List<T> {
    private items: Array<T>;

    constructor() {
        this.items = [];
    }

    size(): number {
        return this.items.length;
    }

    add(value: T): void {
        this.items.push(value);
    }

    get(index: number): T {
        return this.items[index];
    }
}
