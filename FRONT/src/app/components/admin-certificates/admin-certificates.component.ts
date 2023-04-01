import { TableCertificate } from 'src/app/models/TableCertificate';
import { CertificateService } from './../../services/certificate.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-certificates',
  templateUrl: './admin-certificates.component.html',
  styleUrls: ['./admin-certificates.component.css']
})
export class AdminCertificatesComponent {

  displayedColumns: string[] = [ 'issuer', 'subject' , 'validityStart', 'validityPeriod' , 'Delete' ,'Revoke', 'Validate'];

  certificates: TableCertificate[] = [];


  constructor(private certificatService: CertificateService) { }

  ngOnInit(): void {
    this.getCertificates();

  }

  public getCertificates() {
    // this.tkn = localStorage.getItem('user_token');
    // this.username = JSON.parse(atob(this.tkn.split('.')[1]))['sub'];

    this.certificatService.getCertificates().subscribe((data: TableCertificate[]) => {
      this.certificates = data;

    });

  }

  public deleteCertificate(id: string) {
    // this.certificates.forEach((certificate: TableCertificate) => {if(certificate.id === id ){
    //   certificate.delete = true;
    // }})
    this.certificatService.deleteCertificates(id).subscribe((b: Boolean) => {
      this.ngOnInit();
    });

  }

  public revokeCertificate(id: string) {
    this.certificatService.revokeCertificate(id).subscribe((b: Boolean) => {
      this.ngOnInit();
    });

  }

  public validateCertificate(id: string) {
    this.certificatService.validateCertificate(id).subscribe((answer: Boolean) => {
      if(answer){
        alert("CERTIFICATE WITH ID: " + id + " IS VALID.");
      }
      else{
        alert("CERTIFICATE WITH ID: " + id + " IS INVALID.");
      }
      this.ngOnInit();
    });

  }

}
