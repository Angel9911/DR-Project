import { Injectable } from '@angular/core';
import {Customer} from '../models/user';
import {Courier} from '../models/Courier';
import {Administrator} from '../models/administrator';
import {Packages} from '../models/Packages';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  customer: Customer;
  courier: Courier;
  administrator: Administrator;
  packagesId: bigint;
  comment: string;

  constructor() { }
}
