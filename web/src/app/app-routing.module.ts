import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';
import {HomeComponent} from './components/home/home.component';
import {AuthGuard} from './helpers/auth-guard';
import {MainPageComponent} from './auth/main-page/main-page.component';
import {RegistrationSuccessComponent} from './auth/registration-success/registration-success.component';


const routes: Routes = [
  {path: 'log', component: MainPageComponent},

  {path: '', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'cv',
    loadChildren: () => import('./components/cv/cv.module').then(mod =>   mod.CvModule),
    canActivate: [AuthGuard]},

  {path: 'success-registration', component: RegistrationSuccessComponent},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
