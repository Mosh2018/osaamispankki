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


@NgModule({
  declarations: [
    ProfileComponent,
    CvMainComponent,
    CvComponent,
    EducationsComponent],
  imports: [
    CommonModule,
    CvRoutingModule,
    MyCustomMaterialModule,
    CvRoutingModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class CvModule { }
