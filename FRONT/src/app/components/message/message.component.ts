import { Component } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { MessageInterface } from 'src/app/models/MessageInterface';
import { MessageService } from 'src/app/services/message.service';
import { SharedMessageService } from 'src/app/shared/shared-message.service';
import { WebSocketAPI } from 'src/app/websocket/websocket-message.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent {

  displayedColumns: string[] = [ 'id', 'text' , 'status', 'device','date' , 'type'];

  devices:string[] = [];

  messages : MessageInterface[] = [];

  constructor(private messageService : MessageService , private sharedMessage : SharedMessageService,
     private webSocketAPI: WebSocketAPI , public sanitizer: DomSanitizer) {
    this.webSocketAPI.connect();
    this.sharedMessage.message_subscriber$.subscribe(message=>{

      if(!this.devices.includes(message.device)){
        return;
      }
      const hasMessageWithDate = this.messages.some((m) => m.date === message.date);
      if(hasMessageWithDate){
        return;
      }
      this.messages = [...this.messages,message];
    });
   }

  ngOnInit(): void {
    this.getAllDevices()
  }

  public getAllDevices(){
    this.messageService.getAllDevices().subscribe(
      (data: string[]) => {
        this.devices = data
      }
    )

  }


}
