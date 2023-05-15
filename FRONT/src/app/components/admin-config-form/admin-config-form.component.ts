import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { ConfigInterface } from 'src/app/models/ConfigInterface';
import { ConfigService } from 'src/app/services/config.service';
import { SharedConfigService } from 'src/app/shared/shared-config.service';

@Component({
  selector: 'app-admin-config-form',
  templateUrl: './admin-config-form.component.html',
  styleUrls: ['./admin-config-form.component.css']
})
export class AdminConfigFormComponent {
  configForm = new FormGroup({
    name: new FormControl(''),
    minutes: new FormControl(''),
    regex: new FormControl('')
  })

  config : ConfigInterface = {
    name:"",
    minutes: "",
    regex:""
  }

  constructor(public sanitizer: DomSanitizer,private sharedConfigService : SharedConfigService
              , private configService : ConfigService) {

      this.sharedConfigService.congif_subscriber$.subscribe((config)=>{
        this.configForm.patchValue({
          name: config.name,
          minutes: config.minutes,
          regex: config.regex
        });
      });

  }

  ngOnInit(): void {

  }

  public sentConfig(){
    this.config.name = this.configForm.value.name!
    this.config.minutes = this.configForm.value.minutes!;
    this.config.regex = this.configForm.value.regex!;
    console.log(this.config);
    this.configService.updateConfig(this.config).subscribe((answer)=>{
      if (answer){
        console.log(answer);
        this.sharedConfigService.refreshTable();
      }
    });

  }

}
