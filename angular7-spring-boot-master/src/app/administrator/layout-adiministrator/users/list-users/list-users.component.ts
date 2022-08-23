import {Component, OnInit, PipeTransform} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClientService} from '../../../../service/customer/http-client.service';
import {Customer} from '../../../../models/user';
import {HttpAdiministratorService} from '../../../../service/administrator/http-adiministrator.service';
import {DataService} from '../../../../service/data.service';
import {FormControl} from '@angular/forms';
import {DecimalPipe} from '@angular/common';
import {map, startWith} from 'rxjs/operators';
import {from, Observable, of} from 'rxjs';
import {ConfirmDialogService} from '../../../../service/confirm-dialog.service';
// @ts-ignore
let users: Customer[];
// tslint:disable-next-line:label-position no-unused-expression

function search(text: string, pipe: PipeTransform): Customer[] {
  return users.filter(user => {
    console.log(user);
    const term = text.toLowerCase();
    return user.name.toLowerCase().includes(term)
      || user.last_name.toLowerCase().includes(term)
      || user.phone.toLowerCase().includes(term);
  });
}

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.css']
})
export class ListUsersComponent implements OnInit {
  filter = new FormControl('');
 // users: Customer[];
  result$: Observable<Customer[]>;

  // tslint:disable-next-line:max-line-length
  constructor(private router: Router, private apiService: HttpAdiministratorService, private dataService: DataService, private pipe: DecimalPipe, private confirmService: ConfirmDialogService) {
    this.apiService.getUsers()
      .subscribe( data => {
        // @ts-ignore
        users = [...data];
        console.log(users);
        // this.users = data;
        this.result$ = this.filter.valueChanges.pipe(
          startWith(''),
          map(text => search(text, pipe))
        );
      });
  }

  ngOnInit() {
    /*if (!window.localStorage.getItem('token')) {
      this.router.navigate(['login']);
      return;
    }
     this.apiService.getUsers()
      .subscribe( data => {
         this.users = data;
      }); */
  }

  deleteUser(customer: Customer): void {
    console.log(customer.user_id);
    this.confirmService.confirm('Моля потвърдете', 'Искате ли да изтриете този потребител ?')
      .then((confirmed) => {
        // tslint:disable-next-line:triple-equals
        if (confirmed != false) {
          this.apiService.deleteUser(customer.user_id).subscribe( data => {
            users = users.filter(u => u !== customer);
            window.location.reload();
          });
        }
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    /* this.apiService.deleteUser(customer.user_account.username, customer.phone).subscribe( data => {
      users = users.filter(u => u !== customer);
    }); */
   /* this.apiService.deleteUser(2)
      .subscribe( data => {
        this.users = this.users.filter(u => u !== customer);
      }); */
  }

  editUser(customer: Customer): void {
   // window.localStorage.removeItem('editUserId');
   // window.localStorage.setItem('editUserId', customer.toString());
     this.dataService.customer = customer;
     this.router.navigate(['/administrator/edit-user']);
  }

  addUser(): void {
    this.router.navigate(['/administrator/add-user']);
  }
}
