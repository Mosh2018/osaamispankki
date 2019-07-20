import {NgModule} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {
  MatFormFieldModule,
  MatInputModule,
  MatIconModule,
  MatProgressBarModule,
  MatTabsModule,
  MatToolbarModule,
  MatTableModule
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
    MatTableModule
  ],
  exports: [
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatProgressBarModule,
    MatTabsModule,
    MatToolbarModule,
    MatTableModule
  ]
})
export class MyCustomMaterialModule {}
