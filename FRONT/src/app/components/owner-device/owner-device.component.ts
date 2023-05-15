import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { DeviceInterface } from 'src/app/models/DeviceInterface';
import { DeviceHouseService } from 'src/app/services/device-house.service';

@Component({
  selector: 'app-owner-device',
  templateUrl: './owner-device.component.html',
  styleUrls: ['./owner-device.component.css']
})
export class OwnerDeviceComponent {

  deviceForm = new FormGroup({
    name: new FormControl(''),
    houseName : new FormControl(''),
    deviceTypeName : new FormControl('')
  })


  device:DeviceInterface = {
    name:"",
    houseName: "",
    deviceTypeName:""
  };

  houseList:string[] = [];

  deviceTypeList:string[] = [];


  constructor(private deviceHouseService : DeviceHouseService , public sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.deviceHouseService.getAllHouses().subscribe(data=>{
      console.log("HOUSES");
      console.log(data);
      this.houseList = data;
      this.deviceHouseService.getAllDeviceTypes().subscribe(data=>{
        console.log("DEV TYPES");
        console.log(data);
        this.deviceTypeList = data;
      });
    });



  }

  public save_device(){
    this.device.name = this.deviceForm.value.name!;
    this.device.houseName = this.deviceForm.value.houseName!;
    this.device.deviceTypeName = this.deviceForm.value.deviceTypeName!;
    console.log(this.device);
    this.deviceHouseService.saveDevice(this.device).subscribe(data =>{
      console.log(data);
    });
  }

}
