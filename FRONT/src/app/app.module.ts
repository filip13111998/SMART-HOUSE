import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { MatMenuModule } from '@angular/material/menu';
import { NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
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
    RegisterCsrFormComponent
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
    MDBBootstrapModule.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
