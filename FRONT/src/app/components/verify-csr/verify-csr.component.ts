import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterCsrService } from 'src/app/services/register-csr.service';

@Component({
  selector: 'app-verify-csr',
  templateUrl: './verify-csr.component.html',
  styleUrls: ['./verify-csr.component.css']
})
export class VerifyCsrComponent {

  verifyCSRForm = new FormGroup({
    code: new FormControl(''),
  });

  constructor(private registerCsrService : RegisterCsrService ,private router:Router ) { }

  ngOnInit(): void {

  }

  public verifyCSR(){

    this.registerCsrService.verifyCsr(this.verifyCSRForm.value.code!).subscribe(
      (answer: Boolean) => {
        if(answer){
          this.router.navigate(['/','login']);
        }
        console.log(answer);
      },
    )

  }

}
