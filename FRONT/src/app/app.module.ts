import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { MatMenuModule } from '@angular/material/menu';
import { NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { OwnerHomeComponent } from './components/owner-home/owner-home.component';
import { OwnerMenuComponent } from './components/owner-menu/owner-menu.component';
import { TenantMenuComponent } from './components/tenant-menu/tenant-menu.component';
import { TenantHomeComponent } from './components/tenant-home/tenant-home.component';
import { AdminHomeComponent } from './components/admin-home/admin-home.component';
import { AdminMenuComponent } from './components/admin-menu/admin-menu.component';
import { RegisterCsrFormComponent } from './components/register-csr-form/register-csr-form.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatListModule } from '@angular/material/list'; // Import MatListModule
import * as moment from 'moment';
import { InterceptorService } from './services/interceptor.service';
import { VerifyCsrComponent } from './components/verify-csr/verify-csr.component';
import { AdminCsrComponent } from './components/admin-csr/admin-csr.component';
import { AdminCertificatesComponent } from './components/admin-certificates/admin-certificates.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    OwnerHomeComponent,
    OwnerMenuComponent,
    TenantMenuComponent,
    TenantHomeComponent,
    AdminHomeComponent,
    AdminMenuComponent,
    RegisterCsrFormComponent,
    VerifyCsrComponent,
    AdminCsrComponent,
    AdminCertificatesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatMenuModule,
    NgbNavModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatListModule,
    MDBBootstrapModule.forRoot(),
  ],
  providers: [
    { provide: 'moment', useValue: moment },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
