import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserGuard} from '../../../allServices/guards/user-guard';
import {UserComponent} from './user.component';
import {BusinessCardComponent} from './business-card/business-card.component';
import {PersonalComponent} from './personal/personal.component';
import {EducationsComponent} from './educations/educations.component';
import {ExperiencesComponent} from './experiences/experiences.component';


const routes: Routes = [
  {path: '', component: UserComponent, children: [
    {path: 'business_card', component: BusinessCardComponent, canActivate: [UserGuard]},
    {path: 'personal', component: PersonalComponent, canActivate: [UserGuard]},
    {path: 'education', component: EducationsComponent, canActivate: [UserGuard]},
    {path: 'experience', component: ExperiencesComponent, canActivate: [UserGuard]},
    {path: '', redirectTo: 'business_card', pathMatch: 'full', canActivate: [UserGuard]}
    ]
  }];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
