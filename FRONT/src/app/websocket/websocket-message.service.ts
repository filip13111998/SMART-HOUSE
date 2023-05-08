import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';


import { Injectable } from '@angular/core';
import { SharedMessageService } from '../shared/shared-message.service';

@Injectable({
  providedIn: 'root'
})
export class WebSocketAPI {
    webSocketEndPoint: string = 'http://localhost:9090/ws';
    topic: string = "/topic/message";
    stompClient: any;

    constructor( private sharedService:SharedMessageService){
      this.connect();
    }


    connect() {
        console.log("Initialize WebSocket Connection");
        let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        this.stompClient.connect({},  (frame:any)=> {
          this.stompClient.subscribe(this.topic, (message:any)=> {
            // this.onMessageReceived(message);
              console.log("PRISTIGLA")
              console.log(message)
              this.sharedService.sentMessage(JSON.parse(message.body));
            });
            this.stompClient.reconnect_delay = 2000;
        });
    };

}
