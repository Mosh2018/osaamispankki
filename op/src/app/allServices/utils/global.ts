import {DatePipe} from '@angular/common';

export function screenSize() {
  return {
    width: window.innerWidth,
    height: window.innerHeight
  };
}

export function localStorageKey() {
  return 'currentUser';
}

export class JwtResponse {
  jwt: string;
  success: boolean;
}

export const X_URL = {
  LOGIN: '/api/user/login',
  REGISTER: '/api/user/add_new_user',
  LOGOUT: '/api/user/logout',
};

export function byteToImage(stream: any) {

  let binary = '';
  const bytes = new Uint8Array( stream );
  const len = bytes.byteLength;
  for (let i = 0; i < len; i++) {
    binary += String.fromCharCode( bytes[ i ] );
  }
  return 'data:image/JPEG;base64' + ',' + window.btoa( binary );
}

export function formatDate(data: any) {
  if (data !== null && data !== undefined && data !== null) {
    const date = new Date(data.replace(/(\d{2}).(\d{2}).(\d{4})/, '$2/$1/$3'));
    new DatePipe('en-US').transform(date, 'dd.MM.yyyy');
    return date;
  }
  return data;
}

export function dateFormatChanger(date: any) {
  return new DatePipe('en-US').transform(date, 'dd.MM.yyyy');
}
