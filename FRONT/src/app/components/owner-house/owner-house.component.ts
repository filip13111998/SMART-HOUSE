import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { HouseInterface } from 'src/app/models/HouseInterface';
import { DeviceHouseService } from 'src/app/services/device-house.service';

@Component({
  selector: 'app-owner-house',
  templateUrl: './owner-house.component.html',
  styleUrls: ['./owner-house.component.css']
})
export class OwnerHouseComponent {
  houseForm = new FormGroup({
    name: new FormControl('')
  })

  ownersForm = new FormGroup({
    owner: new FormControl('')
  })


  tenantsForm = new FormGroup({
    tenant: new FormControl('')
  })

  ownersList:string[] = [];

  tenantsList:string[] = [];

  house : HouseInterface = {
    name:"",
    owners:[],
    tenants:[]
  }

  constructor(private deviceHouseService : DeviceHouseService , public sanitizer: DomSanitizer) { }

  ngOnInit(): void {


  }

  public add_owner(){

    let owner  = this.ownersForm.value.owner!;

    this.ownersList.push(owner);

    // this.messageForm.reset();

  }

  public add_tenant(){

    let tenant  = this.tenantsForm.value.tenant!;
    this.tenantsList.push(tenant);

    // this.messageForm.reset();

  }

  public delete_owner(index:number){
    this.ownersList.splice(index, 1);
    // let index = this.messageList.indexOf(message);
    // if (index !== -1) {
    //   this.messageList.splice(index, 1);
    // }
  }

  public delete_tenant(index:number){
    this.tenantsList.splice(index, 1);
    // let index = this.messageList.indexOf(message);
    // if (index !== -1) {
    //   this.messageList.splice(index, 1);
    // }
  }

  public save_house(){
    let usrn  = localStorage.getItem('user_token');
    if(usrn !== null){
      let username = JSON.parse(atob(usrn.split('.')[1]))['sub'];
      this.ownersList.push(username);
    }

    this.house.name = this.houseForm.value.name!;
    this.house.owners = this.ownersList;
    this.house.tenants = this.tenantsList;

    console.log(this.house);
    this.deviceHouseService.saveHouse(this.house).subscribe(data =>{
      console.log(data);
    });

  }
}
