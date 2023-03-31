import { AdminHomeComponent } from './components/admin-home/admin-home.component';
import { TenantHomeComponent } from './components/tenant-home/tenant-home.component';
import { OwnerHomeComponent } from './components/owner-home/owner-home.component';
import { LoginComponent } from './components/login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterCsrFormComponent } from './components/register-csr-form/register-csr-form.component';

const routes: Routes = [

  {path: 'login', component:LoginComponent},
  {path: 'owner-home', component:OwnerHomeComponent},
  {path: 'tenant-home', component:TenantHomeComponent},
  {path: 'admin-home', component:AdminHomeComponent},
  {path: 'register-csr', component:RegisterCsrFormComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
