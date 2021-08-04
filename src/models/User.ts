export interface CreateUser {
  firstName: string;
  lastName: string;
  email: string;
  addressOne: string;
  addressTwo: string;
  city: string;
  state: string;
  country: string;
  zipCode: string;
  organization: string;
  phoneNumber: string;
  role: string;
}

export interface User extends CreateUser {
  id: string;
}
