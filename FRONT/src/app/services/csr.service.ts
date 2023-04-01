import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { TableCsr } from '../models/TableCsr';

@Injectable({
  providedIn: 'root'
})
export class CsrService {

  private path = "http://localhost:8080/api"

  constructor(private http: HttpClient) { }

  getCSRS():Observable<TableCsr[]> {

    return this.http.get<TableCsr[]>( this.path + '/get-all' );
  }

  acceptCSR(id:string):Observable<Boolean> {

    return this.http.get<Boolean>( this.path + '/accept' + '/' + id);
  }

  deleteCSR(id:string):Observable<Boolean> {

    return this.http.get<Boolean>( this.path + '/delete' + '/' + id);
  }




  error(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(() => {
      return errorMessage;
    });
  }
}
