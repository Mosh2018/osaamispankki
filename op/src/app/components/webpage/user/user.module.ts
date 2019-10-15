import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UserRoutingModule} from './user-routing.module';
import {BusinessCardComponent} from './business-card/business-card.component';
import {UserComponent} from './user.component';
import {PersonalComponent} from './personal/personal.component';
import {EducationsComponent} from './educations/educations.component';
import {ExperienceComponent} from './experience/experience.component';
import {UserAndCompanyService} from './services/user-and-company.service';
import {EducationAndExperienceService} from './services/education-and-experience.service';
import {EducationComponent} from './education/education.component';
import {ExperiencesComponent} from './experiences/experiences.component';
import {SharedModule} from '../../../shared.module';


@NgModule({
  declarations: [
    UserComponent,
    BusinessCardComponent,
    PersonalComponent,
    EducationComponent,
    EducationsComponent,
    ExperienceComponent,
    ExperiencesComponent
    ],
  imports: [
    CommonModule,
    UserRoutingModule,
    SharedModule
  ],
  exports: [
  ],
  providers: [
    UserAndCompanyService,
    EducationAndExperienceService
  ],
  entryComponents: [EducationComponent, ExperienceComponent],
})
export class UserModule { }
