import {Component, OnInit, PipeTransform} from '@angular/core';
import {Customer} from '../../../models/user';
import {Courier} from '../../../models/Courier';
import {Router} from '@angular/router';
import {HttpAdiministratorService} from '../../../service/administrator/http-adiministrator.service';
import {ConfirmDialogService} from '../../../service/confirm-dialog.service';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {DecimalPipe} from '@angular/common';
import {DataService} from '../../../service/data.service';
// tslint:disable-next-line:prefer-const
let couriers: Courier[];

function search(text: string, pipe: PipeTransform): Courier[] {
  return couriers.filter(courier => {
    console.log(courier);
    const term = text.toLowerCase();
    return courier.courier_first_name.toLowerCase().includes(term)
      || courier.courier_last_name.toLowerCase().includes(term)
      || courier.courier_city_name.toLowerCase().includes(term)
      || courier.courier_phone.toLowerCase().includes(term);
  });
}

@Component({
  selector: 'app-list-couriers',
  templateUrl: './list-couriers.component.html',
  styleUrls: ['./list-couriers.component.css']
})
export class ListCouriersComponent implements OnInit {
  filter = new FormControl('');
  // users: Customer[];
  result$: Observable<Courier[]>;

  // tslint:disable-next-line:max-line-length
  constructor(private router: Router, private apiService: HttpAdiministratorService, private confirmService: ConfirmDialogService, private pipe: DecimalPipe, private dataService: DataService) {
    this.apiService.getCouriers().subscribe(data => {
      couriers = [...data];
      this.result$ = this.filter.valueChanges.pipe(
        startWith(''),
        map(text => search(text, pipe))
      );
    });
  }

  ngOnInit() {
  }
  deleteCourier(courier: Courier): void {
    this.confirmService.confirm('Моля потвърдете', 'Искате ли да изтриете този куриер ?', 'Откажи', 'Изпрати')
      .then((confirmed) => {
        // tslint:disable-next-line:triple-equals
        if (confirmed != false) {
          this.apiService.deleteCourier(courier.courier_id).subscribe( data => {
            couriers = couriers.filter(u => u !== courier);
          });
        }
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }

  editCourier(courier: Courier): void {
    // window.localStorage.removeItem('editUserId');
    // window.localStorage.setItem('editUserId', customer.toString());
    this.dataService.courier = courier;
    this.router.navigate(['/administrator/edit-user']);
  }

  addCourier() {
    this.router.navigate(['/administrator/add-courier']);
  }
}
