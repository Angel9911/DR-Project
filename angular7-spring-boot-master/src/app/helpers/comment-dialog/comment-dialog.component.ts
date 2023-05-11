import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {DataService} from '../../service/data.service';
import {Packages} from '../../models/Packages';
import {Form} from '@angular/forms';
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
  selectedFile: File | null = null;
  packages: Packages;
  private formData: FormData;
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

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  public accept() {
    console.log(this.dataService.packagesId);
    if (this.selectedFile) {
      const formData = new FormData();
      formData.append('file', this.selectedFile);
      this.activeModal.close({event: true, file: formData, data: this.comment});
      console.log(this.comment);
    }
  }

  public dismiss() {
    this.activeModal.dismiss();
  }
}
