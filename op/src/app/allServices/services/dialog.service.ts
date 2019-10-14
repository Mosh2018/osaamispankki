import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material';
import {map} from 'rxjs/operators';

export interface DialogData {
  width: string;
  height: string;
  maxWidth: string;
  minWidth: string;
  maxHeight: string;
  minHeight: string;
  component: any;
  data: any;
}

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(public dialog: MatDialog) { }

  openDialog(dialog: DialogData) {
    const employmentDialog = this.dialog.open(dialog.component, {
      width: dialog.width,
      height: dialog.height,
      maxWidth: dialog.maxWidth,
      data: dialog.data
    });

    return employmentDialog.afterClosed().pipe(map( x => {
      return x;
    }));
  }
}
