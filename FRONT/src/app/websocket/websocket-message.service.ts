import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';


import { Injectable } from '@angular/core';
import { SharedMessageService } from '../shared/shared-message.service';

@Injectable({
  providedIn: 'root'
})
export class WebSocketAPI {
    webSocketEndPointHOME: string = 'http://localhost:9090/ws';
    webSocketEndPointADMIN: string = 'http://localhost:8080/ws';
    webSocketEndPointDEVICE: string = 'http://localhost:9091/ws';
    topic: string = "/topic/message";
    topic_log: string = "/topic/logs";
    stompClient: any;
    stompClientAdminLogs: any;
    stompClientHomeLogs: any;
    stompClientDeviceLogs: any;
    constructor( private sharedService:SharedMessageService){
      // this.connect();
      // this.connect_logs();
    }


    connect() {
        console.log("Initialize WebSocket Connection");
        let ws = new SockJS(this.webSocketEndPointHOME);
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

    connect_admin_logs() {
      console.log("Initialize WebSocket Connection");
      let ws = new SockJS(this.webSocketEndPointADMIN);
      this.stompClientAdminLogs = Stomp.over(ws);
      this.stompClientAdminLogs.connect({},  (frame:any)=> {
        this.stompClientAdminLogs.subscribe(this.topic_log, (message:any)=> {
          // this.onMessageReceived(message);
            console.log("LOGOVI")
            console.log(message)
            this.sharedService.sentLogs(JSON.parse(message.body));
          });
          this.stompClientAdminLogs.reconnect_delay = 2000;
      });
  };

  connect_home_logs() {
    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPointHOME);
    this.stompClientHomeLogs = Stomp.over(ws);
    this.stompClientHomeLogs.connect({},  (frame:any)=> {
      this.stompClientHomeLogs.subscribe(this.topic_log, (message:any)=> {
        // this.onMessageReceived(message);
          console.log("LOGOVI")
          console.log(message)
          this.sharedService.sentLogs(JSON.parse(message.body));
        });
        this.stompClientHomeLogs.reconnect_delay = 2000;
    });
};

connect_device_logs() {
  console.log("Initialize WebSocket Connection");
  let ws = new SockJS(this.webSocketEndPointDEVICE);
  this.stompClientDeviceLogs = Stomp.over(ws);
  this.stompClientDeviceLogs.connect({},  (frame:any)=> {
    this.stompClientDeviceLogs.subscribe(this.topic_log, (message:any)=> {
      // this.onMessageReceived(message);
        console.log("LOGOVI")
        console.log(message)
        this.sharedService.sentLogs(JSON.parse(message.body));
      });
      this.stompClientDeviceLogs.reconnect_delay = 2000;
  });
};

}
