import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserMainPageComponent} from './components/webpage/user/user-main-page/user-main-page.component';
import {UserGuard} from './allServices/guards/user-guard';


const routes: Routes = [
  {path: 'user', component: UserMainPageComponent, canActivate: [UserGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
