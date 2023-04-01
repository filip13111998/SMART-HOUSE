import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterCsrInterface } from '../models/RegisterCsr';

@Injectable({
  providedIn: 'root'
})
export class RegisterCsrService {

  private path = "http://localhost:8080/api"

  constructor(private http: HttpClient) { }

  registerCsr(entity: RegisterCsrInterface):Observable<Boolean> {
    console.log('login request send');
    return this.http.post<Boolean>( this.path + '/register-csr' , entity );
  }

  verifyCsr(entity: String):Observable<Boolean> {
    console.log('login request send');
    return this.http.post<Boolean>( this.path + '/verify-csr' , entity );
  }

}
