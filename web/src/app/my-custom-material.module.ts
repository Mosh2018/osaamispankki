import {NgModule} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {
  MatFormFieldModule,
  MatInputModule,
  MatIconModule,
  MatProgressBarModule,
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
} from '@angular/material';

@NgModule({
  imports: [
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatProgressBarModule,
    MatTabsModule,
    MatToolbarModule,
    MatTableModule,
    MatCardModule,
    MatSelectModule,
    MatStepperModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    MatSlideToggleModule
  ],
  exports: [
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatProgressBarModule,
    MatTabsModule,
    MatToolbarModule,
    MatTableModule,
    MatCardModule,
    MatSelectModule,
    MatStepperModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    MatSlideToggleModule
  ]
})
export class MyCustomMaterialModule {}
