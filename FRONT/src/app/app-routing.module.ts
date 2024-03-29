import { AdminHomeComponent } from './components/admin-home/admin-home.component';
import { TenantHomeComponent } from './components/tenant-home/tenant-home.component';
import { OwnerHomeComponent } from './components/owner-home/owner-home.component';
import { LoginComponent } from './components/login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterCsrFormComponent } from './components/register-csr-form/register-csr-form.component';
import { VerifyCsrComponent } from './components/verify-csr/verify-csr.component';
import { AdminCsrComponent } from './components/admin-csr/admin-csr.component';
import { AdminCertificatesComponent } from './components/admin-certificates/admin-certificates.component';
import { AdminUsersComponent } from './components/admin-users/admin-users.component';
import { AdminMessageComponent } from './components/admin-message/admin-message.component';
import { TenantMessageComponent } from './components/tenant-message/tenant-message.component';
import { OwnerMessageComponent } from './components/owner-message/owner-message.component';
import { AdminDeviceTypeComponent } from './components/admin-device-type/admin-device-type.component';
import { OwnerDeviceComponent } from './components/owner-device/owner-device.component';
import { OwnerHouseComponent } from './components/owner-house/owner-house.component';
import { AdminLoggerComponent } from './components/admin-logger/admin-logger.component';
import { AdminConfigComponent } from './components/admin-config/admin-config.component';

const routes: Routes = [

  {path: 'login', component:LoginComponent},
  {path: 'register-csr', component:RegisterCsrFormComponent},
  {path: 'verify', component:VerifyCsrComponent},

  {path: 'owner-home', component:OwnerHomeComponent},
  {path: 'owner-message', component:OwnerMessageComponent},
  {path: 'owner-device', component:OwnerDeviceComponent},
  {path: 'owner-house', component:OwnerHouseComponent},



  {path: 'tenant-home', component:TenantHomeComponent},
  {path: 'tenant-message', component:TenantMessageComponent},

  {path: 'admin-home', component:AdminHomeComponent},
  {path: 'admin-csr', component:AdminCsrComponent},
  {path: 'admin-certificate', component:AdminCertificatesComponent},
  {path: 'admin-users', component:AdminUsersComponent},
  {path: 'admin-message', component:AdminMessageComponent},
  {path: 'admin-device-type' , component:AdminDeviceTypeComponent},
  {path: 'admin-logger', component:AdminLoggerComponent},
  {path: 'admin-config', component:AdminConfigComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
