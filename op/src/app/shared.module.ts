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
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClientModule} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';


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
  ],
})
export class SharedModule { }
