import {NgModule} from '@angular/core';
import {
  MatButtonModule,
  MatCardModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatMenuModule,
  MatNativeDateModule,
  MatRadioModule,
  MatSelectModule,
  MatSidenavModule,
  MatSlideToggleModule,
  MatStepperModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule
} from '@angular/material';

@NgModule({
  declarations: [],
  imports: [
    MatMenuModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatTabsModule,
    MatToolbarModule,
    MatTableModule,
    MatCardModule,
    MatSelectModule,
    MatStepperModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    MatSlideToggleModule,
    MatExpansionModule,
    MatRadioModule,
    MatSidenavModule
    ],

  exports: [
    MatMenuModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatTabsModule,
    MatToolbarModule,
    MatTableModule,
    MatCardModule,
    MatSelectModule,
    MatStepperModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    MatSlideToggleModule,
    MatExpansionModule,
    MatRadioModule,
    MatSidenavModule
  ]
})
export class MaterialModule { }
