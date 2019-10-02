import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UserRoutingModule} from './user-routing.module';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {BusinessCardComponent} from './business-card/business-card.component';
import {MaterialModule} from '../../../material.module';
import {SidenavComponent} from './sidenav/sidenav.component';
import {UserComponent} from './user.component';
import {PersonalComponent} from './personal/personal.component';
import {EducationComponent} from './education/education.component';
import {ExperienceComponent} from './experience/experience.component';
import {ReactiveFormsModule} from '@angular/forms';
import {UserAndCompanyService} from './services/user-and-company.service';
import {EnumToStringPipe} from '../../../allServices/pipes/enum-to-string.pipe';
import {FlexModule} from '@angular/flex-layout';


@NgModule({
  declarations: [
    UserComponent,
    PageNotFoundComponent,
    BusinessCardComponent,
    SidenavComponent,
    PersonalComponent,
    EducationComponent,
    ExperienceComponent,
    EnumToStringPipe
    ],
  imports: [
    CommonModule,
    UserRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FlexModule,
  ],
  exports: [
    EnumToStringPipe
  ],
  providers: [
    UserAndCompanyService
  ]
})
export class UserModule { }
