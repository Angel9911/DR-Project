import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {DataService} from '../../service/data.service';
import {Packages} from '../../models/Packages';
const statusType = 'Проблем с пратката';

@Component({
  selector: 'app-comment-dialog',
  templateUrl: './comment-dialog.component.html',
  styleUrls: ['./comment-dialog.component.css']
})
export class CommentDialogComponent implements OnInit {
  @Input() title: string;
  @Input() message: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() comment: string;
  packages: Packages;
  constructor(private activeModal: NgbActiveModal, private dataService: DataService) {
    console.log(this.dataService.packagesId);

  }

  ngOnInit() {
    console.log(this.dataService.packagesId);
    this.title = 'Проблем с пратка';
    this.message = 'Моля опишете с няколко думи какъв проблем е възникнал с пратката';
    this.btnOkText = 'Изпрати';
    this.btnCancelText = 'Откажи';
  }
  public decline() {
    this.activeModal.close(false);
  }

  public accept() {
    console.log(this.dataService.packagesId);
    this.activeModal.close({event: true, data: this.comment});
    console.log(this.comment);
  }

  public dismiss() {
    this.activeModal.dismiss();
  }
}
