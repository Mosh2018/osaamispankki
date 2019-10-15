import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

export interface Confirmation {
  title: string;
  message: string;
  yes: string;
  no: string;
}

@Component({
  selector: 'app-confirm-dialog',
  template: `
    <div class="confirm">
      <h1 mat-dialog-title>{{data.title}}</h1>
      <div mat-dialog-content>
        <p>{{data.message}}</p>
      </div>
      <div mat-dialog-actions>
        <button mat-raised-button color="primary" (click)="onNoClick()" cdkFocusInitial>{{data.no}}</button>
        <button mat-raised-button color="primary" (click)="onYesClick()" >{{data.yes}}</button>
      </div>
    </div>
  `,
  styles: [`
    .confirm {
        color: #242441 !important;
        font-size: 1.3em;
        font-family: 'Poppins', sans-serif;
    }
    ::ng-deep .mat-slide-toggle.mat-checked .mat-slide-toggle-bar {
      background-color: #ff2d3f;
    }
    ::ng-deep .mat-slide-toggle.mat-checked .mat-slide-toggle-thumb {
      background-color: #bb2136;
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
