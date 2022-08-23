import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../service/auth/auth.service';

@Component({
  selector: 'app-header-courier',
  templateUrl: './header-courier.component.html',
  styleUrls: ['./header-courier.component.css']
})
export class HeaderCourierComponent implements OnInit {
  imageSrc = 'assets/images/logo.png';
  imageAlt = 'iPhone';

  constructor(private auth: AuthService) { }

  ngOnInit() {
  }
  onLogOut() {
    this.auth.logout();
  }
}
