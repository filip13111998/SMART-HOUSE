import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
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

  statusList :string[] = ['NORMAL','OKAY','WARNING','ERROR','ALARM'];

  messages : MessageInterface[] = [];

  messagesFilter : MessageInterface[] = [];

  filter:boolean = true

  messageForm = new FormGroup({
    device: new FormControl(''),
    status: new FormControl(''),
  })

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
      if(this.filter){

        this.messagesFilter = [...this.messages];
      }else{
        this.filter_message();
      }

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

  getStatusClass(status: string): string {
    switch (status) {
      case 'NORMAL':
        return 'normal-status';
      case 'OKAY':
        return 'okay-status';
      case 'WARNING':
        return 'warning-status';
      case 'ERROR':
        return 'error-status';
      case 'ALARM':
        return 'alarm-status';
      default:
        return '';
    }
  }

  public filter_message(){
    this.filter = false;
    console.log(this.messageForm.value);
    if(this.messageForm.value.device!  == '' || this.messageForm.value.device! == null){
      this.messagesFilter = this.messages.filter(msg =>msg.status == this.messageForm.value.status!)
      return;
    }

    if(this.messageForm.value.status!  == '' || this.messageForm.value.status! == null){
      this.messagesFilter = this.messages.filter(msg =>{
        return msg.device == this.messageForm.value.device!
      })

      return;
    }

    this.messagesFilter = this.messages.filter(msg =>{
      return msg.device == this.messageForm.value.device! && msg.status == this.messageForm.value.status!;
    })

  }
  public reset_message(){
    this.filter = true;
    this.messagesFilter = this.messages;
    this.messageForm.reset();
  }
}
