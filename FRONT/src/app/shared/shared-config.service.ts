import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ConfigInterface } from '../models/ConfigInterface';

@Injectable({
  providedIn: 'root'
})
export class SharedConfigService {

  private config = new BehaviorSubject<ConfigInterface>(
    {
      name:"",
      minutes:"",
      regex:""
    }
  );
  congif_subscriber$ = this.config.asObservable();


  private refresh = new BehaviorSubject<any>(null);
  refresh_subscriber$ = this.refresh.asObservable();

  constructor() { }


  sentConfig(conf:ConfigInterface){
    this.config.next(conf);
  }

  refreshTable(){
    console.log("refresh table");
    this.refresh.next({});
  }

}
