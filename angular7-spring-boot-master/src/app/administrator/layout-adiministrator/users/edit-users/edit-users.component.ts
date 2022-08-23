import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Customer} from '../../../../models/user';
import {Router} from '@angular/router';
import {HttpAdiministratorService} from '../../../../service/administrator/http-adiministrator.service';
import {DataService} from '../../../../service/data.service';

@Component({
  selector: 'app-edit-users',
  templateUrl: './edit-users.component.html',
  styleUrls: ['./edit-users.component.css']
})
export class EditUsersComponent implements OnInit {
  user: Customer;
  editForm: FormGroup;

  // tslint:disable-next-line:max-line-length
  constructor(private router: Router, private httpService: HttpAdiministratorService, private form: FormBuilder, private dataService: DataService) {
    this.createForm();
  }

  ngOnInit() {
  //  console.log('user_account: ' + this.dataService.customer.name);
    this.editForm.controls.name.setValue(this.dataService.customer.name);
    this.editForm.controls.last_name.setValue(this.dataService.customer.last_name);
    this.editForm.controls.phone.setValue(this.dataService.customer.phone);
    this.editForm.controls.city.setValue(this.dataService.customer.city);
    this.editForm.controls.address.setValue(this.dataService.customer.address);

  }
  get name() {
    return this.editForm.get('name');
  }

  get last_name() {
    return this.editForm.get('last_name');
  }
  get phone() {
    return this.editForm.get('phone');
  }

  get city() {
    return this.editForm.get('city');
  }
  get address() {
    return this.editForm.get('address');
  }
  updateCustomer() {
    this.user.name = this.name.value;
    this.user.last_name = this.last_name.value;
    this.user.phone = this.phone.value;
   // this.user.city = this.name.value;
    this.user.address = this.address.value;
    this.httpService.updateUser(this.user).subscribe(data => {
      alert('User updated successfully');
    });
  }
  createForm() {
    this.editForm = this.form.group({
      name: ['', Validators.required],
      last_name: ['', Validators.required],
      phone: ['', Validators.required],
      city: ['', Validators.required],
      address: ['', Validators.required],
    });
  }
}
