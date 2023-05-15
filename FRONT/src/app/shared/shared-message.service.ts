import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MessageInterface } from '../models/MessageInterface';
import { LogInterface } from '../models/LogInterface';

@Injectable({
  providedIn: 'root'
})
export class SharedMessageService {

  private message = new BehaviorSubject<MessageInterface>({id:"pera",
    text:"poruka",
    status:"NORMAL",
    device:"rud1",
    date:"312321",
    type:"XSA"}
    );
  message_subscriber$ = this.message.asObservable();

  private log = new BehaviorSubject<LogInterface>({
    message:"msg"
  });
  log_subscriber$ = this.log.asObservable();

  constructor() { }

  sentMessage(new_message:MessageInterface){
    this.message.next(new_message);
  }

  sentLogs(log:LogInterface){
    this.log.next(log);
  }

}
