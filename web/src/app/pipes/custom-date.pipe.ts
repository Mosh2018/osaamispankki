import { Pipe, PipeTransform } from '@angular/core';
import {DatePipe} from '@angular/common';

@Pipe({
  name: 'customDate'
})
export class CustomDatePipe implements PipeTransform {

  transform(value: any, ...args: any[]): any {
    if (value !== null && value !== undefined && value !== null) {
      const date = new Date(value.replace(/(\d{2}).(\d{2}).(\d{4})/, '$2/$1/$3'));
      new DatePipe('en-US').transform(date, 'dd.MM.yyyy');
      return date;
    }
    return value;
  }

}
