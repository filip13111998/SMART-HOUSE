import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { DeviceMessageInterface } from 'src/app/models/DeviceMessageInterface';
import { DeviceTypeInterface } from 'src/app/models/DeviceTypeInterface';
import { DeviceHouseService } from 'src/app/services/device-house.service';

@Component({
  selector: 'app-admin-device-type',
  templateUrl: './admin-device-type.component.html',
  styleUrls: ['./admin-device-type.component.css']
})
export class AdminDeviceTypeComponent {

  deviceTypeForm = new FormGroup({
    name: new FormControl('')
  })

  messageForm = new FormGroup({
    message: new FormControl(''),
    status: new FormControl('')
  })

  messageList:DeviceMessageInterface[] = [];

  deviceType : DeviceTypeInterface = {
    name:"",
    messages: []
  }

  constructor(private deviceHouseService : DeviceHouseService , public sanitizer: DomSanitizer) { }

  ngOnInit(): void {


  }

  public add_message(){

    let message  = this.messageForm.value.message!;
    let status = this.messageForm.value.status!;
    console.log(message);
    this.messageList.push({"text":message, "status":status});

    // this.messageForm.reset();

  }

  public delete_message(index:number){
    this.messageList.splice(index, 1);
    // let index = this.messageList.indexOf(message);
    // if (index !== -1) {
    //   this.messageList.splice(index, 1);
    // }
  }

  public save_device_type(){
    this.deviceType.name = this.deviceTypeForm.value.name!;
    this.deviceType.messages = this.messageList;

    this.deviceHouseService.saveDeviceType(this.deviceType).subscribe(data =>{
      console.log(data);
    });
    console.log(this.deviceType);
  }

}
