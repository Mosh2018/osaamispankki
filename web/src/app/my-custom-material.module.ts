import {NgModule} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {
  MatFormFieldModule,
  MatInputModule,
  MatIconModule,
  MatProgressBarModule
} from '@angular/material';

@NgModule({
  imports: [
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatProgressBarModule
  ],
  exports: [
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatProgressBarModule
  ]
})
export class MyCustomMaterialModule {}
