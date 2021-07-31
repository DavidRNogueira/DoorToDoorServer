export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  password?: string;
  country: string;
  organization: string;
  phoneNumber: string;
  role: string;
}
