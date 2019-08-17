import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {CvComponent} from './cv.component';
import {AuthGuard} from '../../helpers/auth-guard';
import {CvMainComponent} from './cv-main/cv-main.component';
import {ProfileComponent} from './profile/profile.component';
import {EducationsComponent} from './educations/educations.component';


const cvRouting: Routes = [
  {
    path: '',
    component: CvComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        canActivateChild: [AuthGuard],
        children: [
          {path: '', component: CvMainComponent},
          {path: 'profile', component: ProfileComponent},
          {path: 'educations', component: EducationsComponent},
        ]
      }
    ]
  }
];
@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(cvRouting)
  ],
  exports: [
    RouterModule
  ]
})
export class CvRoutingModule { }
