import { Component, OnInit } from '@angular/core';
import {Customer} from '../../../../models/user';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {HttpAdiministratorService} from '../../../../service/administrator/http-adiministrator.service';
import {City} from '../../../../models/City';

@Component({
  selector: 'app-add-users',
  templateUrl: './add-users.component.html',
  styleUrls: ['./add-users.component.css']
})
export class AddUsersComponent implements OnInit {
  user: Customer;
  customerCreate = new Customer();
  // city = new City();
  addForm: FormGroup;

  constructor(private router: Router, private apiService: HttpAdiministratorService, private form: FormBuilder) {
    this.createForm();
  }

  ngOnInit() {
  }

  createCustomer(user) {
    this.apiService.createUser(this.customerCreate).subscribe(response => {
        alert('Потребителят е създаден успешно');
        this.router.navigate(['/administrator/home']);
      });
  }

  createForm() {
    this.addForm = this.form.group({
      name: ['', Validators.required],
      last_name: ['', Validators.required],
      city: ['', Validators.required],
      address: ['', Validators.required],
      phone: ['', Validators.required],
    });
  }

  onSubmit() {

  }
}
