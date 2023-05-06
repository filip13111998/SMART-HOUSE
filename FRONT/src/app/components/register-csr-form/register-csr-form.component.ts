import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { RegisterCsrInterface } from 'src/app/models/RegisterCsr';
import { RegisterCsrService } from 'src/app/services/register-csr.service';
import * as moment from 'moment';
import { Router } from '@angular/router';
import { MatSelectionList } from '@angular/material/list';
@Component({
  selector: 'app-register-csr-form',
  templateUrl: './register-csr-form.component.html',
  styleUrls: ['./register-csr-form.component.css']
})
export class RegisterCsrFormComponent {

  list1Items:string[] = ['Basic Constraints', 'Key Usage'];
  list2Items:string[] = [];

  registerCSRForm = new FormGroup({

    username: new FormControl(''),
    password: new FormControl(''),
    pin: new FormControl(''),
    name: new FormControl(''),
    role: new FormControl(''),
    validityStart: new FormControl(''),
    validityPeriod: new FormControl(''),
    commonName: new FormControl(''),
    organizationUnion: new FormControl(''),
    organizationName: new FormControl(''),
    localityName: new FormControl(''),
    stateName: new FormControl(''),
    country: new FormControl(''),
    template: new FormControl(''),
  });

  registerCsrInterface: RegisterCsrInterface = {
    username: '',
    password: '',
    pin:'',
    name: '',
    role:'',
    validityStart: 0,
    validityPeriod: 0,
    commonName: '',
    organizationUnion: '',
    organizationName: '',
    localityName: '',
    stateName: '',
    country: '',
    template:'SSL Client',
    extensions:''
  };

  constructor(private registerCsrService : RegisterCsrService ,private router:Router) { }

  ngOnInit(): void {
  }

  public onTemplateChange(){
    // console.log("USO")
    const selectedTemplate = this.registerCSRForm.value.template;
    // console.log(selectedTemplate);
    if(selectedTemplate == 'SSL Client'){
      this.list2Items = ['Key Usage'];
      this.list1Items = []
    }
    else{
      this.list2Items = ['Basic Constraints', 'Key Usage'];
      this.list1Items = []
    }
  }

  moveSelected(sourceList: MatSelectionList, targetList: MatSelectionList) {
    const selectedItems = sourceList.selectedOptions.selected.map(option => option.value);
    targetList.options.toArray().forEach(option => {
      if (selectedItems.includes(option.value)) {
        option.selected = true;
      }
    });
    selectedItems.forEach((item: string) => {
      const index = this.list1Items.findIndex(option => option === item);
      if (index !== -1) {
        this.list1Items.splice(index, 1);
      }
      this.list2Items.push(item);
    });
    sourceList.selectedOptions.clear();
    // console.log(this.list1Items);
    // console.log(this.list2Items);
  }

  returnSelected(sourceList: MatSelectionList, targetList: MatSelectionList) {
    const selectedItems = sourceList.selectedOptions.selected.map(option => option.value);
    targetList.options.toArray().forEach(option => {
      if (selectedItems.includes(option.value)) {
        option.selected = true;
      }
    });
    selectedItems.forEach((item: string) => {
      const index = this.list2Items.findIndex(option => option === item);
      if (index !== -1) {
        this.list2Items.splice(index, 1);
      }
      this.list1Items.push(item);
    });
    sourceList.selectedOptions.clear();
  }



  public registerCsr(){
    // console.log(this.registerCSRForm.value);
    // console.log("LIST2")
    // console.log(this.list2Items);
    // console.log("END LIST 2");
    // alert("HAHA ALRT");

    let validityStart = moment(this.registerCSRForm.value.validityStart).valueOf();
    let validityPeriod = moment(this.registerCSRForm.value.validityPeriod).valueOf();

    this.registerCsrInterface.username = this.registerCSRForm.value.username!;
    this.registerCsrInterface.password = this.registerCSRForm.value.password!;
    this.registerCsrInterface.pin = this.registerCSRForm.value.pin!;
    this.registerCsrInterface.name = this.registerCSRForm.value.name!;
    this.registerCsrInterface.role = this.registerCSRForm.value.role!;
    this.registerCsrInterface.validityStart = validityStart;
    this.registerCsrInterface.validityPeriod = validityPeriod;
    this.registerCsrInterface.commonName = this.registerCSRForm.value.commonName!;
    this.registerCsrInterface.organizationUnion = this.registerCSRForm.value.organizationUnion!;
    this.registerCsrInterface.organizationName = this.registerCSRForm.value.organizationName!;
    this.registerCsrInterface.localityName = this.registerCSRForm.value.localityName!;
    this.registerCsrInterface.stateName = this.registerCSRForm.value.stateName!;
    this.registerCsrInterface.country = this.registerCSRForm.value.country!;
    this.registerCsrInterface.template = this.registerCSRForm.value.template!;
    this.registerCsrInterface.extensions = this.list2Items.join('|')
    // console.log(this.registerCsrInterface);

    this.registerCsrInterface.username = this.registerCsrInterface.username.replace(/[^\w]/gi, '');
    this.registerCsrInterface.password =  this.registerCsrInterface.password.replace(/[^\w]/gi, '');
    this.registerCsrInterface.pin = this.registerCsrInterface.pin.replace(/[^\w]/gi, '');
    this.registerCsrInterface.name = this.registerCsrInterface.name.replace(/[^\w]/gi, '');
    this.registerCsrInterface.commonName = this.registerCsrInterface.commonName.replace(/[^\w]/gi, '');
    this.registerCsrInterface.organizationUnion = this.registerCsrInterface.organizationUnion.replace(/[^\w]/gi, '');
    this.registerCsrInterface.organizationName = this.registerCsrInterface.organizationName.replace(/[^\w]/gi, '');
    this.registerCsrInterface.localityName = this.registerCsrInterface.localityName.replace(/[^\w]/gi, '');
    this.registerCsrInterface.stateName = this.registerCsrInterface.stateName.replace(/[^\w]/gi, '');
    this.registerCsrInterface.country = this.registerCsrInterface.country.replace(/[^\w]/gi, '');
    console.log(this.registerCsrInterface);

    if (this.registerCsrInterface.password.length < 7
      || !/\d/.test(this.registerCsrInterface.password)
      || !/[A-Z]/.test(this.registerCsrInterface.password)
      || !/[a-z]/.test(this.registerCsrInterface.password)
      || !/[^a-zA-Z0-9]/.test(this.registerCsrInterface.password)) {
      console.log("WRONG PASSWORD!");
      return;
    }
    // console.log(this.registerCsrInterface);
    /* CHECK FORM DATA ... password,country etc... */
    if (this.registerCSRForm.value.country != undefined && this.registerCSRForm.value.country != undefined) {
      if (this.registerCSRForm.value.country?.length < 2){
        console.log("Please enter a valid country");
        return;
      }

    }
    console.log(this.registerCsrInterface)
    this.registerCsrService.registerCsr(this.registerCsrInterface).subscribe(
      (answer: Boolean) => {
        if(answer){
          this.router.navigate(['/','verify']);
        }
        console.log(answer);
      },
    )

  }

}
