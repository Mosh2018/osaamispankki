import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileComponent} from './profile/profile.component';
import {CvRoutingModule} from './cv-routing.module';
import {CvMainComponent} from './cv-main/cv-main.component';
import {CvComponent} from './cv.component';
import {MyCustomMaterialModule} from '../../my-custom-material.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {EducationsComponent} from './educations/educations.component';
import {EducationComponent} from './educations/education/education.component';
import {DateAdapter, MAT_DATE_FORMATS} from '@angular/material';
import {APP_DATE_FORMAT, AppDateAdapter} from '../../helpers/dataAdapter';
import {ExperiencesComponent} from './experiences/experiences.component';
import {ExperienceComponent} from './experiences/experience/experience.component';
import {EnumToStringPipe} from '../../pipes/enum-to-string.pipe';
import { CustomDatePipe } from '../../pipes/custom-date.pipe';



@NgModule({
  declarations: [
    ProfileComponent,
    CvMainComponent,
    CvComponent,
    EducationsComponent,
    EducationComponent,
    ExperiencesComponent,
    ExperienceComponent,
    EnumToStringPipe,
    CustomDatePipe],
  entryComponents: [EducationComponent, ExperienceComponent],
  imports: [
    CommonModule,
    CvRoutingModule,
    MyCustomMaterialModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [
    EnumToStringPipe,
    CustomDatePipe
  ],
  providers: [
    {provide: DateAdapter, useClass: AppDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMAT}
  ]
})
export class CvModule { }
