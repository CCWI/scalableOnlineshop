import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/observable/throw';
import {Observable} from 'rxjs/Observable';
import {Token} from './token.service';
import {Article} from './article/article.component';
import {Shoppingcart} from './shoppingcart/shoppingcart.service';
import {User} from './user.service';
import {Delivery} from './delivery/delivery.component';
import {Payment} from './payment/payment.service';

@Injectable()
export class BackendcallService {

      private actionUrl: string;
      private headers: Headers;
      private encodedString: string;
      private options: RequestOptions;

      constructor(private _http: Http, user: string, pw: string, actionUrl: string) {
        if (user === 'token') {
          this.encodedString = pw;
        } else {
          this.encodedString = 'Basic ' + btoa( user + ':' + pw);
        }

        this.actionUrl = actionUrl;
        this.headers = new Headers();
        this.headers.append('Authorization', this.encodedString);
        this.headers.append('Content-Type', 'application/json');
        this.headers.append('Accept', 'application/json');

        this.options = new RequestOptions({
          body: '',
          headers: this.headers
        });
      }

      // ArticleList
      public getAllArticle = (): Observable<string[]> => {
        return this._http.get(this.actionUrl, this.options)
                   .map(this.extractArticleList)
                   .catch(this.handleError);
      }

      // Article-Detailpage
      public getArticle = (): Observable<Article> => {
        return this._http.get(this.actionUrl, this.options)
                   .map(this.extract)
                   .catch(this.handleError);
      }

      public postArticle = (article: Article): Observable<any> => {
            let body = JSON.stringify(article);
            let options = new RequestOptions();
            options.headers = this.headers;

            return this._http.post(this.actionUrl, body, options)
                       .catch(this.handleError);
        }

        public updateArticle = (article: Article): Observable<any> => {
              let body = JSON.stringify(article);
              let options = new RequestOptions();
              options.headers = this.headers;

              return this._http.put(this.actionUrl, body, options)
                         .catch(this.handleError);
          }

      // Authentication
      public getToken = (): Observable<Token> => {
        return this._http.get(this.actionUrl, this.options)
                   .map(this.extractToken)
                   .catch(this.handleError);
      }

      // Shoppingcart
      public getAllShoppingcartItems = (): Observable<Shoppingcart[]> => {
        return this._http.get(this.actionUrl, this.options)
                   .map(this.extractShoppingCartList)
                   .catch(this.handleError);
      }
      public deleteItem = (): Observable<any> => {
        return this._http.delete(this.actionUrl, this.options)
                   .catch(this.handleError);
      }
      public postArticleToShoppingcart = (articleId: number, userId: number, quantity: number): Observable<any> => {
          let body = JSON.stringify({ articleId, userId, quantity });
          let options = new RequestOptions();
          options.headers = this.headers;

          return this._http.post(this.actionUrl, body, options)
                     .catch(this.handleError);
      }
      public updateArticleToShoppingcart = (articleId: number, userId: number, quantity: number): Observable<any> => {
          let body = JSON.stringify({ articleId, userId, quantity });
          let options = new RequestOptions();
          options.headers = this.headers;

          return this._http.put(this.actionUrl, body, options)
                     .catch(this.handleError);
      }

      // User
      public getUserData = (): Observable<User> => {
        return this._http.get(this.actionUrl, this.options)
                   .map(this.extract)
                   .catch(this.handleError);
      }
      public saveUserData = (id: number, firstname: string, lastname: string, address: string, postcode: string,
                             city: string, country: string): Observable<User> => {
          let body = JSON.stringify({id, firstname, lastname, address, postcode, city, country });
          let options = new RequestOptions();
          options.headers = this.headers;

          return this._http.post(this.actionUrl, body, options)
                     .map(this.extract)
                     .catch(this.handleError);
      }
      public updateUserData = (id: number, firstname: string, lastname: string, address: string, postcode: string,
                             city: string, country: string): Observable<User> => {
          let body = JSON.stringify({id, firstname, lastname, address, postcode, city, country });
          let options = new RequestOptions();
          options.headers = this.headers;

          return this._http.put(this.actionUrl, body, options)
                     .map(this.extract)
                     .catch(this.handleError);
      }
      public saveAuthData = (username: string, password: string): Observable<Number> => {
          let body = JSON.stringify({username, password});
          let options = new RequestOptions();
          options.headers = this.headers;

          return this._http.post(this.actionUrl, body, options)
                     .map(this.extract)
                     .catch(this.handleError);
      }

      // Delivery
      public getDelivery = (): Observable<Delivery[]> => {
        return this._http.get(this.actionUrl, this.options)
                   .map(this.extractDeliveryList)
                   .catch(this.handleError);
      }

      public postDelivery = (id: number, shipmentReady: boolean, shippingDays: number, shippingMethod: string ): Observable<Delivery> => {
          let body = JSON.stringify({ id, shipmentReady, shippingDays, shippingMethod });
          let options = new RequestOptions();
          options.headers = this.headers;

          return this._http.post(this.actionUrl, body, options)
                     .map(this.extract)
                     .catch(this.handleError);
      }

      // Payment
      public getPayment = (): Observable<Payment[]> => {
        return this._http.get(this.actionUrl, this.options)
                   .map(this.extractPaymentList)
                   .catch(this.handleError);
      }

      public postPaymentMethod = (supplierId: number, method: string): Observable<Payment> => {
          let body = JSON.stringify({ supplierId, method });
          let options = new RequestOptions();
          options.headers = this.headers;

          return this._http.post(this.actionUrl, body, options)
                     .map(this.extract)
                     .catch(this.handleError);
      }


      private extractArticleList(res: Response) {
          console.log('extractData() is executed.');
          let body = res.json();
          return body.articleList || { };
      }

      private extractDeliveryList(res: Response) {
          console.log('extractData() is executed.');
          let body = res.json();
          return body.shippmentList || { };
      }

      private extract(res: Response) {
          console.log('extract() is executed.');
          let body = res.json();
          return body || { };
      }

      private extractPaymentList(res: Response) {
          console.log('extract() is executed.');
          let body = res.json();
          return body.paymentList || { };
      }

      private extractShoppingCartList(res: Response) {
          console.log('extract() is executed.');
          let body = res.json();
          return body.shoppingcartList || { };
      }

      private extractToken(res: Response) {
          console.log('extractData() is executed.');
          let token = res.json().jwtToken;
          return token || { };
      }

      private handleError (error: any) {
        return Observable.throw(error);
      }
}
