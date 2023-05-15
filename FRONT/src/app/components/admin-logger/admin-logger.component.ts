import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { LogInterface } from 'src/app/models/LogInterface';
import { SharedMessageService } from 'src/app/shared/shared-message.service';
import { WebSocketAPI } from 'src/app/websocket/websocket-message.service';

@Component({
  selector: 'app-admin-logger',
  templateUrl: './admin-logger.component.html',
  styleUrls: ['./admin-logger.component.css']
})
export class AdminLoggerComponent {

  logs : string[] = [];

  logsFilter : string[] = [];

  filter : boolean = false

  filterForm = new FormGroup({
    filter: new FormControl(''),

  })

  constructor( private sharedMessage : SharedMessageService,
    private webSocketAPI: WebSocketAPI , public sanitizer: DomSanitizer) {

    this.webSocketAPI.connect_admin_logs();
    this.webSocketAPI.connect_home_logs();
    this.webSocketAPI.connect_device_logs();
    this.sharedMessage.log_subscriber$.subscribe((message:LogInterface)=>{
      console.log(message);
      const duplicate = this.logs.some((l) => l === message.message);
      if(duplicate){
        return;
      }

      this.logs = [...this.logs , message.message]
      if(this.filter){
        this.logsFilter = [...this.logs ]
        // this.messagesFilter = [...this.messages];
      }else{
        this.filter_log();
      }
      // this.logs = [...this.logs , message.message]

   });
  }

  ngOnInit(): void {

  }

  public filter_log(){
    this.filter = false;

    const filterRegex = new RegExp(this.filterForm.value.filter!);

    this.logsFilter = this.logs.filter(log =>{
      return filterRegex.test(log);
    })
    console.log("FILTER REGEX RADI");
    console.log(this.logsFilter);
  }
  public reset_log(){
    this.filter = true;
    this.logsFilter = this.logs;
    this.filterForm.reset();
  }

}
