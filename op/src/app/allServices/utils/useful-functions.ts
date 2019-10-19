export function valid(x: any) {
  return x !== null && x !== undefined;
}

export function notEmpty(x: string) {
  return valid(x) && x !== '';
}

export function isTrue(x: boolean) {
  return valid(x) && x === true;
}

export function m_null(x: any) {
  return x === null || x === undefined;
}

export function m_Empty(x: string) {
  return x === null || x === undefined || x === '';
}
