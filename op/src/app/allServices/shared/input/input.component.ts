import {Component, Input, OnInit} from '@angular/core';
import {ControlValueAccessor} from '@angular/forms';

@Component({
  selector: 'app-input',
  template: `
      <mat-form-field fxLayout="{{mFxLayout}}">
          <input
                  matInput
                  placeholder="{{placeholder}}"
                  (change)="changeDate($event)">
      </mat-form-field>
  `,
  styles: [`

      mat-form-field {
          width: 100%;
          color: #242441 !important;
          font-size: 1em;
          font-family: 'Poppins', sans-serif;
      }
  `]
})
export class InputComponent implements ControlValueAccessor {
  @Input() private placeholder: string;
  @Input() private mFxLayout: string;
  private exportedData: any;

  constructor() { }


  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.propagateChange = fn;
  }

  writeValue(obj: any): void {
    if (obj) {
      this.exportedData = obj;
    }
  }

  propagateChange = ( x: any) => {};

  changeDate(event: Event) {
    this.exportedData = event.target;
    this.propagateChange(event.target);
  }
}
