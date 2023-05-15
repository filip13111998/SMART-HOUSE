import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { ConfigInterface } from '../models/ConfigInterface';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private path = "http://localhost:8080/config"

  constructor(private http: HttpClient) { }

  getConfigs():Observable<ConfigInterface[]> {

    return this.http.get<ConfigInterface[]>( this.path + '/get-all' );
  }

  updateConfig(config:ConfigInterface):Observable<Boolean> {

    return this.http.post<Boolean>( this.path + '/update', config);
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
