import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Confirmation} from '../../models/education-data';

@Component({
  selector: 'app-confirm-dialog',
  template: `
    <div class="confirm">
      <h1 mat-dialog-title>{{data.title}}</h1>
      <div mat-dialog-content>
        <p>{{data.message}}</p>
      </div>
      <div mat-dialog-actions>
        <button mat-button (click)="onNoClick()" cdkFocusInitial>{{data.no}}</button>
        <button mat-button (click)="onYesClick()" >{{data.yes}}</button>
      </div>
    </div>
  `,
  styles: [`
    .confirm {
      font-family: 'Play', sans-serif;
      font-size: 1.2em;
      padding: 10px;
    }
    ::ng-deep .mat-slide-toggle.mat-checked .mat-slide-toggle-bar {
      background-color: #ff2d3f;
    }
    ::ng-deep .mat-slide-toggle.mat-checked .mat-slide-toggle-thumb {
      background-color: #bb2136;
    }
    button {
      color: #292729;
      padding: 20px;
      font-size: 1.2em ;
      font-family: 'Play', sans-serif;
      margin: 25px;
    }
  `]
})
export class ConfirmDialogComponent {

  constructor(public dialogRef: MatDialogRef<ConfirmDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Confirmation) { }


  onNoClick() {
    this.dialogRef.close('NO');
  }

  onYesClick() {
      this.dialogRef.close('YES');
  }
}
