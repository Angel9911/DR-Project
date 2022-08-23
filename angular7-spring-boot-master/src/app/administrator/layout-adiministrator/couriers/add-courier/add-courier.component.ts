import { Component, OnInit } from '@angular/core';
import {City} from '../../../../models/City';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Courier} from '../../../../models/Courier';
import {Router} from '@angular/router';
import {HttpAdiministratorService} from '../../../../service/administrator/http-adiministrator.service';

@Component({
  selector: 'app-add-courier',
  templateUrl: './add-courier.component.html',
  styleUrls: ['./add-courier.component.css']
})
export class AddCourierComponent implements OnInit {
  user: Courier;
  courierCreate = new Courier();
  city = new City();
  addForm: FormGroup;
  constructor(private router: Router, private apiService: HttpAdiministratorService, private form: FormBuilder) {
    this.createForm();
  }

  ngOnInit() {
  }
  createCourier(courier) {
    this.apiService.createCourier(this.courierCreate).subscribe(response => {
      alert('Куриерът е създаден успешно');
      this.router.navigate(['/administrator/home']);
    });
  }
  createForm() {
    this.addForm = this.form.group({
      courier_first_name: ['', Validators.required],
      courier_last_name: ['', Validators.required],
      city_name: ['', Validators.required],
      courier_phone: ['', Validators.required],
    });
  }
}
