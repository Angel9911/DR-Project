import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Customer} from '../../models/user';
import {User_account} from '../../models/user_account';
import {Courier} from '../../models/Courier';
import {Packages} from '../../models/Packages';

const customersUrl = 'http://localhost:8082/customers/';
const couriersUrl = 'http://localhost:8082/couriers/';
const adminUrl = 'http://localhost:8082/administrator/';

const reqHeader = new HttpHeaders({
  'Content-Type': 'application/json',
});

@Injectable({
  providedIn: 'root'
})
export class HttpAdiministratorService {

  constructor(private http: HttpClient) {
  }

  getUsers() {
    const url = adminUrl + 'customers';
    return this.http.get<Customer[]>(url, {headers: reqHeader});
  }

  getCouriers() {
    const url = adminUrl + 'couriers';
    return this.http.get<Courier[]>(url, {headers: reqHeader});
  }

  loginAdministrator(username: string) {
    console.log(JSON.parse(localStorage.getItem('token')));
    const url = adminUrl + username;
    // @ts-ignore
    return this.http.get<User_account>(url, {headers: reqHeader});
  }

//  DO TUK V DOKUMENTACIQ
  /* getUserById(id: number) {
      return this.http.get(this.baseUrl + id);
    } */
  createCourier(coureir: Courier) {
    const url = adminUrl + 'courier' + '/' + 'create';
    return this.http.post(url, coureir, {headers: reqHeader});
  }

  updateCourier() {

  }

// tslint:disable-next-line:variable-name
  deleteCourier(courier_id: bigint) {
    const url = couriersUrl + 'delete' + '/' + courier_id;
    return this.http.delete(url);
  }

  createUser(customer: Customer) {
    // const url = 'http://localhost:8082/create/';
    const url = adminUrl + 'customer' + '/' + 'create';
    return this.http.post(url, customer, {headers: reqHeader});
  }

  updateUser(customer: Customer) {
    const url = customersUrl + 'update';
    return this.http.put<Customer>(url, customer);
  }

// tslint:disable-next-line:variable-name
  deleteUser(user_id: bigint) {
    const url = customersUrl + 'delete' + '/' + user_id;
    return this.http.delete(url);
  }

  getAllTypes() {
    const url = adminUrl + 'types';
    return this.http.get<any[]>(url, {headers: reqHeader});
  }

  registerPackage(packages: Packages) {
    const url = adminUrl + 'package' + '/' + 'create';
    return this.http.post(url, packages, {headers: reqHeader});
  }

  getAllPackages() {
    const url = adminUrl + 'packages';
    return this.http.get<Packages[]>(url, {headers: reqHeader});
  }
}
