export interface Task {
  id: string;
  address: string;
  city: string;
  state: string;
  country: string;
  name: string;
  status: string; // TODO: enum

  // organization
  // coordinates
  // lines {lat, lng}[]

  salvations: number; // TODO:
  timeSent: number; // TODO
}
