import { Component, OnInit } from '@angular/core';
import {EndpointMasterService} from './endpoint-master.service';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {ConfirmDialogComponent} from '../../../allServices/shared/confirm-dialog/confirm-dialog.component';
import {Confirm} from '../../../allServices/modules/common';


interface ActivationCode {
  id: number;
  used: boolean;
  activationCode: string;
  created_At: string;
  update_At: string;
}

@Component({
  selector: 'app-master',
  templateUrl: './master.component.html',
  styleUrls: ['./master.component.css']
})
export class MasterComponent implements OnInit {

  codesData: MatTableDataSource<ActivationCode>;
  displayedColumns = [
    {id: 'activationCode', name: 'Activation code'},
    {id: 'created_At', name: 'Created'},
    {id: 'used', name: 'Used'},
    {id: 'company_id', name: 'Company name'}];

  setting = {
    all_codes: false,
  };

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
  getAllCodes() {
    this.switchToComponent('all_codes');
    this.endpoint.getAllCodes().subscribe( data => {
      this.codesData = new MatTableDataSource<ActivationCode>(data);
    });
  }

  private generateActivateCode() {
    this.endpoint.codeGenerator().subscribe( code => {
      console.log('code', code);
    });
  }


  switchToComponent(str: string) {
    for (const settingKey in this.setting) {
      if (settingKey === str) {
        this.setting[settingKey] = true;
      } else {
        this.setting[settingKey] = false;
      }
    }
  }


}
