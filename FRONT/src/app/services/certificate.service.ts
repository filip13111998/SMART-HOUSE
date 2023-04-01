import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { TableCertificate } from '../models/TableCertificate';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  private path = "http://localhost:8080/api/certificate"

  constructor(private http: HttpClient) { }

  getCertificates():Observable<TableCertificate[]> {

    return this.http.get<TableCertificate[]>( this.path + '/get-all' );
  }

  deleteCertificates(id:string):Observable<Boolean> {

    return this.http.get<Boolean>( this.path + '/delete' + '/' + id);
  }

  revokeCertificate(id:string):Observable<Boolean> {

    return this.http.get<Boolean>( this.path + '/revoke' + '/' + id);
  }

  validateCertificate(id:string):Observable<Boolean> {

    return this.http.get<Boolean>( this.path + '/validate' + '/' + id);
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
