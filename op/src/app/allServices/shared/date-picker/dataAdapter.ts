import {NativeDateAdapter} from '@angular/material';
import {MatDateFormats} from '@angular/material/core';
import {DatePipe} from '@angular/common';


export class AppDateAdapter extends NativeDateAdapter {
  // tslint:disable-next-line:ban-types
  format(date: Date, displayFormat: Object): string {
    return new DatePipe('en-US').transform(date, 'dd.MM.yyyy');
  }
}


export const APP_DATE_FORMAT: MatDateFormats = {
  parse: {
    dateInput: {month: 'short', year: 'numeric', day: 'numeric'},
  },
  display: {
    dateInput: 'input',
    monthYearLabel: {year: 'numeric', month: 'numeric'},
    dateA11yLabel: {
      year: 'numeric', month: 'long', day: 'numeric'
    },
    monthYearA11yLabel: {year: 'numeric', month: 'long'},
  }
};



