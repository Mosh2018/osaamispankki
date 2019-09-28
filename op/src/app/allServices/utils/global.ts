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
