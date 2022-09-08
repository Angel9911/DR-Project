import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'employee-management';
  imageSrc = 'assets/images/background_image.jpg';
  imageAlt = 'background_image';
  // tslint:disable-next-line:variable-name
  image_2Src = 'assets/images/Courier-Services_2.png';
  // tslint:disable-next-line:variable-name
  image_2Alt = 'Courier-Services_2';
}
