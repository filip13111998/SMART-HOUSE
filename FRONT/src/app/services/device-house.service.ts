import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DeviceTypeInterface } from '../models/DeviceTypeInterface';
import { HouseInterface } from '../models/HouseInterface';
import { DeviceInterface } from '../models/DeviceInterface';

@Injectable({
  providedIn: 'root'
})
export class DeviceHouseService {

  private path = "http://localhost:9090"

  constructor(private http: HttpClient) { }

  saveDeviceType(type:DeviceTypeInterface):Observable<Boolean> {
    return this.http.post<Boolean>( this.path + '/type/save' , type );
  }

  getAllDeviceTypes():Observable<string[]> {
    return this.http.get<string[]>( this.path + '/type/all' );
  }

  saveHouse(house:HouseInterface):Observable<Boolean> {
    return this.http.post<Boolean>( this.path + '/house/save' , house );
  }

  getAllHouses():Observable<string[]> {
    return this.http.get<string[]>( this.path + '/house/all' );
  }

  saveDevice(device:DeviceInterface):Observable<Boolean> {
    return this.http.post<Boolean>( this.path + '/device/save' , device );
  }

}
