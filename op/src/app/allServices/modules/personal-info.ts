export class PersonalInfo {
  address: {
    street: string;
    postcode: string;
    city: string;
    country: string
  };
  city: string;
  country: string;
  postcode: string;
  street: string;
  birthday: string;
  firstName: string;
  phoneNo: string;
  surname: string;
}


export function initPersonalInfo() {
  return {
    address: {
      street: '',
      postcode: '',
      city: '',
      country: '',
    },
    city: '',
    country: '',
    postcode: '',
    street: '',
    birthday: '',
    firstName: '',
    phoneNo: '',
    surname: ''
  };
}
