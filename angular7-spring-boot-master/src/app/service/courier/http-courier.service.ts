import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Courier} from '../../models/Courier';
import {Packages} from '../../models/Packages';

const baseUrl = 'http://localhost:8082/courier/';
const reqHeader = new HttpHeaders({
  'Content-Type': 'application/json',
  Authentication: 'Bearer ' + JSON.parse(localStorage.getItem('token'))
});

@Injectable({
  providedIn: 'root'
})
export class HttpCourierService {

  constructor(private httpClient: HttpClient) {
  }

  loginCourier(username: string) {
    const url = 'http://localhost:8082/courier';
    return this.httpClient.get<Courier>(`${url}?username=${username}`, {headers: reqHeader});
  }

  getPackages(username: string) {
    const url = baseUrl + 'packages';
    return this.httpClient.get<Packages>(`${url}?username=${username}`, {headers: reqHeader});
  }

  getProblemPackages(username: string) {
    const url = baseUrl + 'packages' + '/' + 'problem';
    return this.httpClient.get<Packages>(`${url}?username=${username}`, {headers: reqHeader});
  }

  getDeliveredPackages(username: string) {
    const url = baseUrl + 'packages' + '/' + 'delivered';
    return this.httpClient.get<Packages>(`${url}?username=${username}`, {headers: reqHeader});
  }

  updateProblemPackage(statusType: string, packageId: bigint, message: string, imgFile: FormData) {
    const url = baseUrl + 'package' + '/' + 'problem' + '/' + 'update';
    console.log(imgFile);
    // tslint:disable-next-line:max-line-length
    return this.httpClient.post<string>(`${url}?packageId=${packageId}&status=${statusType}&message=${message}`, {file: imgFile}, {headers: reqHeader});
  }

  updatePackageSuccessfull(statusType: string, packageId: bigint) {
    const url = baseUrl + 'package' + '/' + 'update';
    return this.httpClient.put(`${url}?packageId=${packageId}&status=${statusType}`, {headers: reqHeader});
  }
}
