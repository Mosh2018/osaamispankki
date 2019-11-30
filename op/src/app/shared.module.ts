import {NgModule} from '@angular/core';
import {PageNotFoundComponent} from './allServices/shared/page-not-found/page-not-found.component';
import {SidenavComponent} from './allServices/shared/sidenav/sidenav.component';
import {EnumToStringPipe} from './allServices/pipes/enum-to-string.pipe';
import {DatePickerComponent} from './allServices/shared/date-picker/date-picker.component';
import {ConfirmDialogComponent} from './allServices/shared/confirm-dialog/confirm-dialog.component';
import {MaterialModule} from './material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FlexLayoutModule} from '@angular/flex-layout';
import {InputComponent} from './allServices/shared/input/input.component';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {DateAdapter, MAT_DATE_FORMATS} from '@angular/material';
import {APP_DATE_FORMAT, AppDateAdapter} from './allServices/shared/date-picker/dataAdapter';


@NgModule({
  declarations: [
    PageNotFoundComponent,
    SidenavComponent,
    InputComponent,
    EnumToStringPipe,
    DatePickerComponent,
    ConfirmDialogComponent,
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    CommonModule,
    MaterialModule,
    FlexLayoutModule,

  ],
  exports: [
    // Components
    EnumToStringPipe,
    DatePickerComponent,
    SidenavComponent,
    // Modules
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    MaterialModule,
    FlexLayoutModule,
    InputComponent,
  ],
  entryComponents: [ConfirmDialogComponent],
  providers: [
    {provide: DateAdapter, useClass: AppDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMAT}
  ]
})
export class SharedModule { }
