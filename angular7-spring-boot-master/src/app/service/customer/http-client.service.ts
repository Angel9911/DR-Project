import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject} from 'rxjs';
import {Customer} from '../../models/user';
import {Admin} from '../../models/admin';
import { saveAs } from 'file-saver';
import {City} from '../../models/City';
import {SendRequest} from '../../models/SendRequest';
import {Packages} from '../../models/Packages';

const accUrl = 'http://localhost:8082/account/';
const custUrl = 'http://localhost:8082/customer/';
const reqHeader = new HttpHeaders({
  'Content-Type': 'application/json',
  Authentication: 'Bearer ' + JSON.parse(localStorage.getItem('token'))
});

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {
  private adminsubject: BehaviorSubject<Admin>;
  public admin: Admin;
  public customer: Customer;

  constructor(private httpClient: HttpClient) {
  }

  public get adminValue(): Admin {
    return this.adminsubject.value;
  }

  // tslint:disable-next-line:variable-name
  registerCustomer(customer: SendRequest) {
    const url = 'http://localhost:8082/register';

    // @ts-ignore
    return this.httpClient.post<Customer>(url, customer, {responseType: 'text'});
  }

  loginCustomer(username: string) {
    const url = custUrl + username;
    // @ts-ignore
    return this.httpClient.get<Customer>(url, {headers: reqHeader});
  }

  getCities() {
    return this.httpClient.get<any[]>('https://raw.githubusercontent.com/dbonev91/Bulgaria-Towns-JSON/master/towns.json');
  }

  findUsername(username: string) {
    // const url = accUrl + username;
    return this.httpClient.get<boolean>(`${accUrl}?username=${username}`);
  }

  findEmail(email: string) {
    const url = custUrl + 'email/' + email;
    return this.httpClient.get<boolean>(`${accUrl}?email=${email}`);
  }

  updateCustomer(customer: Customer) {
    const url = custUrl + 'update';
    // @ts-ignore
    return this.httpClient.put<Customer>(url, customer);
  }

  // tslint:disable-next-line:variable-name
  getPackages(username: string) {
    const url = custUrl + 'packages';
    return this.httpClient.get<Packages>(`${url}?username=${username}`);
  }
  forgotPassword(email: string) {
    const url = 'http://localhost:8082/email/forgot/password';
    return this.httpClient.get<Customer>(`${url}?toEmailAddress=${email}`);
  }
  generateInvoice() {
    const url =  'http://localhost:8082/invoice/generator';
    this.httpClient.get(url, { responseType: 'blob' }).subscribe(response => {
      saveAs(response, 'invoice.pdf');
    });
  }
}
