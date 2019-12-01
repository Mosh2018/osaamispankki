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
  activationCode: string;
  error = null;
  constructor(private service: RegisterService) { }

  ngOnInit() {
    this.getCompany();
  }

  addCompany() {
    this.service.register(this.yTunnus, this.activationCode).subscribe( (x: CompanyInfo) => {
      this.companyInfo = x;
      this.yTunnusInputEnable = m_null(x.code);
    }, (error1: any) => {
      this.error = error1.company_activation;
      console.log(this.error);

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
