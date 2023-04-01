import { TableCsr } from 'src/app/models/TableCsr';
import { CsrService } from './../../services/csr.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-csr',
  templateUrl: './admin-csr.component.html',
  styleUrls: ['./admin-csr.component.css']
})
export class AdminCsrComponent {
  // displayedColumns: string[] = ['id', 'username', 'validityStart', 'validityPeriod',
  //                               'serialNumber', 'commonName', 'organizationUnion', 'organizationName',
  //                               'localityName', 'stateName', 'country' , 'Accept' , 'Delete'];

  displayedColumns: string[] = [ 'username', 'validityStart', 'validityPeriod',
                                'serialNumber' , 'Accept' , 'Delete'];

  csrs: TableCsr[] = [];


  constructor(private CsrService: CsrService) { }

  ngOnInit(): void {
    this.getCSRS();

  }

  public getCSRS() {
    // this.tkn = localStorage.getItem('user_token');
    // this.username = JSON.parse(atob(this.tkn.split('.')[1]))['sub'];

    this.CsrService.getCSRS().subscribe((data: TableCsr[]) => {
      this.csrs = data;

    });

  }

  public acceptCSR(id: string) {
    this.CsrService.acceptCSR(id).subscribe((b: Boolean) => {
      this.ngOnInit();
    });

  }

  public deleteCSR(id: string) {
    this.CsrService.deleteCSR(id).subscribe((b: Boolean) => {
      this.ngOnInit();
    });

  }



}
