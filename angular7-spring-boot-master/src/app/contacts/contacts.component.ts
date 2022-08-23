import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpClientService} from '../service/customer/http-client.service';
import {DataService} from '../service/data.service';
import {AlertServiceService} from '../service/alert-service.service';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {
  EmailSrc = 'assets/images/email.png';
  EmailAlt = 'email';
  PhoneSrc = 'assets/images/phone_con.png';
  PhoneAlt = 'phone_con';
  LocationSrc = 'assets/images/location.png';
  LocationAlt = 'location';
  contactForm: FormGroup;
  submitted: boolean;
  loading: boolean;

  // tslint:disable-next-line:max-line-length
  constructor(private form: FormBuilder, private httpClientService: HttpClientService, private dataService: DataService, private alertService: AlertServiceService) {
    this.createForm();
  }

  ngOnInit() {
    const names = this.dataService.customer.name + ' ' + this.dataService.customer.last_name;
    this.contactForm.controls.name.setValue(names);
    this.contactForm.controls.email.setValue(this.dataService.customer.email);
  }
  sendMessage() {
    this.submitted = true;
    this.alertService.clear();
    if (this.contactForm.invalid) {
      return;
    }
    // this.loading=true;
  }
  createForm() {
    // @ts-ignore
    this.contactForm = this.form.group({
      name: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required]),
      message: new FormControl('', [Validators.required]),
    });
  }
}
