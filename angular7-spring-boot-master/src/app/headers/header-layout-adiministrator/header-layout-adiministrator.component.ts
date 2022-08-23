import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../service/auth/auth.service';

@Component({
  selector: 'app-header-layout-adiministrator',
  templateUrl: './header-layout-adiministrator.component.html',
  styleUrls: ['./header-layout-adiministrator.component.css']
})
export class HeaderLayoutAdiministratorComponent implements OnInit {
  imageSrc = 'assets/images/logo.png';
  imageAlt = 'iPhone';
  constructor(private authService: AuthService) { }

  ngOnInit() {
  }
  onLogOut() {
    this.authService.logout();
  }
}
