import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { RegisterCsrInterface } from 'src/app/models/RegisterCsr';
import { RegisterCsrService } from 'src/app/services/register-csr.service';
import * as moment from 'moment';
import { Router } from '@angular/router';
@Component({
  selector: 'app-register-csr-form',
  templateUrl: './register-csr-form.component.html',
  styleUrls: ['./register-csr-form.component.css']
})
export class RegisterCsrFormComponent {

  registerCSRForm = new FormGroup({

    username: new FormControl(''),
    password: new FormControl(''),
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

  });

  registerCsrInterface: RegisterCsrInterface = {
    username: '',
    password: '',
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
  };

  constructor(private registerCsrService : RegisterCsrService ,private router:Router) { }

  ngOnInit(): void {
  }

  public registerCsr(){
    console.log(this.registerCSRForm.value);

    let validityStart = moment(this.registerCSRForm.value.validityStart).valueOf();
    let validityPeriod = moment(this.registerCSRForm.value.validityPeriod).valueOf();

    this.registerCsrInterface.username = this.registerCSRForm.value.username!;
    this.registerCsrInterface.password = this.registerCSRForm.value.password!;
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

    /* CHECK FORM DATA ... password,country etc... */
    if (this.registerCSRForm.value.country != undefined && this.registerCSRForm.value.country != undefined) {
      if (this.registerCSRForm.value.country?.length < 2){
        console.log("Please enter a valid country");
        return;
      }

    }

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
