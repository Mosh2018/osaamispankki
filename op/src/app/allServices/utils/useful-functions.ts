export function valid(x: any) {
  return x !== null && x !== undefined;
}

export function notEmpty(x: string) {
  return valid(x) && x !== '';
}

export function isTrue(x: boolean) {
  return valid(x) && x === true;
}
