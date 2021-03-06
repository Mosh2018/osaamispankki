import {DatePipe} from '@angular/common';
import {map} from 'rxjs/operators';

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

export const API_CONSTANTS = {
  EDUCATION: 'education',
  EXPERIENCE: 'experience'
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

export function p(y: any) {
  return y.pipe(map( x => {
    return x;
  }));
}
