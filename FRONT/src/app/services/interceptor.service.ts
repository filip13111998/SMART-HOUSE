import { HttpEvent, HttpHandler, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InterceptorService {

  constructor() { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(localStorage.getItem('user_token') == null) {
      let tokenizedReq = req.clone(
        {

          headers: req.headers.set('Authorization', 'Bearer ')
        }
      )
      console.log(tokenizedReq);
      return next.handle(tokenizedReq)
    }
    else{
      let tokenizedReq = req.clone(
        {

          headers: req.headers.set('Authorization', 'Bearer ' + localStorage.getItem('user_token'))
        }
      )
      console.log(tokenizedReq);
      return next.handle(tokenizedReq)
    }

  }
}
