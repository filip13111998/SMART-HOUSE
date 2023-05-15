import { ConfigInterface } from './../../models/ConfigInterface';
import { ConfigService } from './../../services/config.service';
import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { SharedConfigService } from 'src/app/shared/shared-config.service';

@Component({
  selector: 'app-admin-config-table',
  templateUrl: './admin-config-table.component.html',
  styleUrls: ['./admin-config-table.component.css']
})
export class AdminConfigTableComponent {

  displayedColumns: string[] = [ 'name', 'minutes' , 'regex' , 'update'];

  confs : ConfigInterface[] = [];

  config : ConfigInterface = {
    name:"",
    minutes: "",
    regex:""
  }

  constructor(public sanitizer: DomSanitizer,private sharedConfigService : SharedConfigService
    , private configService : ConfigService) {
      this.sharedConfigService.refresh_subscriber$.subscribe(data => {
        console.log("REFRESH");
        this.ngOnInit();
      });
  }

  ngOnInit(): void {
    this.getConfigs();
  }

  public getConfigs(){
    this.configService.getConfigs().subscribe((data)=>{
      this.confs = data;
    });
  }


  public update(name:string , minutes:string , regex:string){

    this.config.name = name;
    this.config.minutes =minutes;
    this.config.regex = regex;

    this.sharedConfigService.sentConfig(this.config);

  }

}
