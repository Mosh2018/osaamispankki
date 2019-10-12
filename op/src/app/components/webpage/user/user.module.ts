import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UserRoutingModule} from './user-routing.module';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {BusinessCardComponent} from './business-card/business-card.component';
import {MaterialModule} from '../../../material.module';
import {SidenavComponent} from './sidenav/sidenav.component';
import {UserComponent} from './user.component';
import {PersonalComponent} from './personal/personal.component';
import {EducationsComponent} from './educations/educations.component';
import {ExperienceComponent} from './experience/experience.component';
import {ReactiveFormsModule} from '@angular/forms';
import {UserAndCompanyService} from './services/user-and-company.service';
import {EnumToStringPipe} from '../../../allServices/pipes/enum-to-string.pipe';
import {FlexModule} from '@angular/flex-layout';
import {DatePickerComponent} from '../../../allServices/common/date-picker/date-picker.component';
import {APP_DATE_FORMAT, AppDateAdapter} from '../../../allServices/common/date-picker/dataAdapter';
import {DateAdapter, MAT_DATE_FORMATS} from '@angular/material';
import {EducationAndExperienceService} from './services/education-and-experience.service';
import {ConfirmDialogComponent} from '../../../allServices/common/confirm-dialog/confirm-dialog.component';
import {EducationComponent} from './education/education.component';
import {ExperiencesComponent} from './experiences/experiences.component';


@NgModule({
  declarations: [
    UserComponent,
    PageNotFoundComponent,
    BusinessCardComponent,
    SidenavComponent,
    PersonalComponent,
    EnumToStringPipe,
    DatePickerComponent,
    ConfirmDialogComponent,
    EducationComponent,
    EducationsComponent,
    ExperienceComponent,
    ExperiencesComponent
    ],
  imports: [
    CommonModule,
    UserRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FlexModule,
  ],
  exports: [
    EnumToStringPipe,
    DatePickerComponent
  ],
  providers: [
    UserAndCompanyService,
    EducationAndExperienceService,
    {provide: DateAdapter, useClass: AppDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMAT}
  ],
  entryComponents: [EducationComponent, ExperienceComponent, ConfirmDialogComponent]
})
export class UserModule { }
