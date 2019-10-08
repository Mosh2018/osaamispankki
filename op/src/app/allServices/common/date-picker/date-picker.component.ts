import {Component, forwardRef, Input} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {MatDatepickerInputEvent} from '@angular/material';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-date-picker',
  template: `
      <mat-form-field fxLayout="{{mFxLayout}}">
          <input matInput
                 [matDatepicker]="picker"
                 [value]="exportedData"
                 (dateInput)="changeDate($event)"
                 placeholder="{{placeholder}}">
          <mat-datepicker-toggle matSuffix [for]="picker">
              <mat-icon matDatepickerToggleIcon>keyboard_arrow_down</mat-icon>
          </mat-datepicker-toggle>
          <mat-datepicker #picker></mat-datepicker>
      </mat-form-field>
  `,
  styles: [`
      mat-form-field {
          width: 100%;
          color: #242441 !important;
          font-size: 1.3em;
          font-family: 'Poppins', sans-serif;
      }
  `],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => DatePickerComponent),
    multi: true
  }]
})
export class DatePickerComponent implements ControlValueAccessor {
  @Input() private placeholder: string;
  @Input() private max: string;
  @Input() private min: string;
  @Input() private mFxLayout: string;

  private exportedData: any;

  constructor() {
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {}

  writeValue(obj: any): void {
    if (obj) {
      this.exportedData = this.formatDate(obj);
    }
  }

  changeDate(event: MatDatepickerInputEvent<Date>) {
    this.exportedData = event.value;
    this.propagateChange(this.dateFormatChanger(event.value));
  }

   propagateChange = ( x: any) => {};

formatDate(data: any) {
    if (data !== null && data !== undefined && data !== null) {
      const date = new Date(data.replace(/(\d{2}).(\d{2}).(\d{4})/, '$2/$1/$3'));
      this.dateFormatChanger(date)
      return date;
    }
    return data;
  }

dateFormatChanger(date: any) {
    return new DatePipe('en-US').transform(date, 'dd.MM.yyyy');
  }
}
