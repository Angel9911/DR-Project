import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-list-packages',
  templateUrl: './list-packages.component.html',
  styleUrls: ['./list-packages.component.css']
})
export class ListPackagesComponent implements OnInit {
  filter = new FormControl('');

  constructor() { }

  ngOnInit() {
  }

}
