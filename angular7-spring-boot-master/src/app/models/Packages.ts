import {Courier} from './Courier';
import {Customer} from './user';
import {TypePackage} from './TypePackage';
import {StatusPackage} from './StatusPackage';
import {Offices} from './offices';

export class Packages {
  // tslint:disable-next-line:variable-name
  package_id: bigint;
  // tslint:disable-next-line:variable-name
  name_package: string;
  statusPackage: StatusPackage;
  typePackage: TypePackage;
  couirer: Courier;
  customer: Customer;
  office: Offices;
  receiver: Customer;
  // tslint:disable-next-line:variable-name
  weight_package: any;
  // tslint:disable-next-line:variable-name
  size_height: any;
  // tslint:disable-next-line:variable-name
  size_width: any;
  // tslint:disable-next-line:variable-name
  review_package: boolean;
  // tslint:disable-next-line:variable-name
  package_price: any;
  // tslint:disable-next-line:variable-name
  total_cost: any;
  // tslint:disable-next-line:variable-name
  date_register_package: any;
  // tslint:disable-next-line:variable-name
  date_delivery_package: any;
}
