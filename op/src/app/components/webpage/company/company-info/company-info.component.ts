import {Component, OnInit} from '@angular/core';
import {RegisterService} from '../services/register.service';
import {CompanyInfo, initCompanyInfo} from '../../../../allServices/modules/company-info';
import {m_Empty, m_null} from '../../../../allServices/utils/useful-functions';

@Component({
  selector: 'app-company-info',
  templateUrl: './company-info.component.html',
  styleUrls: ['./company-info.component.css']
})
export class CompanyInfoComponent implements OnInit {

  yTunnus: string;
  errors = null;
  companyInfo = initCompanyInfo();
  yTunnusInputEnable = true;
  constructor(private service: RegisterService) { }

  ngOnInit() {
    this.getCompany();
  }

  addCompany() {
    this.service.register(this.yTunnus).subscribe( (x: CompanyInfo) => {
      this.companyInfo = x;
      this.yTunnusInputEnable = m_null(x.code);
    });
  }

  private getCompany() {
    this.service.getRegisteredCompany()
      .subscribe( (x: CompanyInfo) => {
        this.companyInfo = x;
        this.yTunnusInputEnable = m_null(x) ? true : m_Empty(x.code);
      }, error => {
        this.errors = error;
      });
  }


}
