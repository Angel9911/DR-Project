import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../service/auth/auth.service';

@Component({
  selector: 'app-header-layout-user',
  templateUrl: './header-layout-user.component.html',
  styleUrls: ['./header-layout-user.component.css']
})
export class HeaderLayoutUserComponent implements OnInit {
  searchIcon = 'assets/images/search-icon.png';
  imageSrc = 'assets/images/logo.png';
  imageAlt = 'iPhone';
  profileSrc = 'assets/images/user-profile.png';
  profileAlt = 'profile';

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  onLogOut() {
    this.authService.logout();
  }

}
