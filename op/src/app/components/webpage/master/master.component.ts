import { Component, OnInit } from '@angular/core';
import {EndpointMasterService} from './endpoint-master.service';
import {MatDialog} from '@angular/material';
import {ConfirmDialogComponent} from '../../../allServices/shared/confirm-dialog/confirm-dialog.component';
import {Confirm} from '../../../allServices/modules/common';

@Component({
  selector: 'app-master',
  templateUrl: './master.component.html',
  styleUrls: ['./master.component.css']
})
export class MasterComponent implements OnInit {

  constructor(private endpoint: EndpointMasterService,
              private confirmDialog: MatDialog) { }

  ngOnInit() {
  }

  opeDialogForCodeGenerate() {
    const codeConfirmDialog = this.confirmDialog.open(ConfirmDialogComponent, {
      width: '500px',
      data: {
        title: '',
        message: 'Do you want to generate a new activation code',
        yes: 'Generate',
        no: 'Cancel'
      }
    });

    codeConfirmDialog.afterClosed().subscribe( response => {
      if (response === Confirm.YES) {
        this.generateActivateCode();
      } else { return; }
    });

  }





  private generateActivateCode() {
    this.endpoint.codeGenerator().subscribe( code => {
      console.log('code', code);
    });
  }
}
