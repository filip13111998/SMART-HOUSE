import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserTableInterface } from '../models/UserTableInterface';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private path = "http://localhost:8080/users"

  constructor(private http: HttpClient) { }

  getAllUsers():Observable<UserTableInterface[]> {
    return this.http.get<UserTableInterface[]>( this.path + '/all'  );
  }

  ownerRoleUser(username:string , flag:boolean):Observable<Boolean> {
    console.log('login request send');
    return this.http.get<Boolean>( this.path + `/owner-role/${username}/${flag}`);
  }

  tenantRoleUser(username:string , flag:boolean):Observable<Boolean> {
    console.log('login request send');
    return this.http.get<Boolean>( this.path + `/tenant-role/${username}/${flag}` );
  }
}
