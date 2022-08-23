import {Customer} from './user';
import {City} from './City';

export class SendRequest {
  customer: Customer;
  username: string;
  password: string;
  roles: Array<string>;
}
