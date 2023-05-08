import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MessageInterface } from '../models/MessageInterface';

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

  constructor() { }

  sentMessage(new_message:MessageInterface){
    this.message.next(new_message);
  }

}
