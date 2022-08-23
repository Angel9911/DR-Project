import {User_account} from './user_account';
import {City} from './City';

export class Customer {
  // tslint:disable-next-line:variable-name
  user_id: bigint;
  // tslint:disable-next-line:variable-name
  user_account: User_account;
  name: string;
  // tslint:disable-next-line:variable-name
  last_name: string;
  email: string;
  address: string;
  // tslint:disable-next-line:variable-name
  // city_id: string;
   city: string;
  phone: string;

  // tslint:disable-next-line:variable-name
  // tslint:disable-next-line:variable-name max-line-length
  /* constructor(user_account: User_account, name: string, last_name: string, email: string, city: City, address: string, phone: string) {
    this.user_account = user_account;
    this.name = name;
    this.last_name = last_name;
    this.email = email;
    this.city = city;
    this.address = address;
    this.phone = phone;
  } */
}

