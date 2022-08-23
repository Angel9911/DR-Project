import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
  logoSrc = 'assets/images/logo-removebg-preview.png';
  FbSrc = 'assets/images/FaceBook-icon.png';
  FbAlt = 'facebook';
  InstaSrc = 'assets/images/instagram.png';
  InstaAlt = 'instagram';
  PhoneSrc = 'assets/images/twitter1.png';
  PhoneAlt = 'twitter';
  LinkedinSrc = 'assets/images/linkedin.png';
  LinkedinAlt = 'linkedin';
  constructor() { }

  ngOnInit() {
  }

}
