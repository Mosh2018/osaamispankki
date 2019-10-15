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
