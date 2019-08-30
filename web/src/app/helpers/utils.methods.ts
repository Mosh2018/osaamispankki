import {DatePipe} from '@angular/common';
import {FormGroup} from '@angular/forms';

export enum apiItems {
  EDUCATION = 'education'
}
export function dateFormatChanger(date: any) {
  return new DatePipe('en-US').transform(date, 'dd.MM.yyyy');
}

export function fb(data: FormGroup) {
  return data.controls;
}

export function formatDate(data: any) {
  if (data !== null && data !== undefined && data !== null) {
    const date = new Date(data.replace(/(\d{2}).(\d{2}).(\d{4})/, '$2/$1/$3'));
    new DatePipe('en-US').transform(date, 'dd.MM.yyyy');
    return date;
  }
  return data;
}

export function byteToImage(stream: any) {

  let binary = '';
  const bytes = new Uint8Array( stream );
  const len = bytes.byteLength;
  for (let i = 0; i < len; i++) {
    binary += String.fromCharCode( bytes[ i ] );
  }
  return 'data:image/JPEG;base64' + ',' + window.btoa( binary );
}
