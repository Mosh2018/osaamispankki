export interface CompanyInfo {
  businessId: string;
  businessLine: string;
  code: string;
  companyForm: string;
  companyName: string;
  createdByPerson: string;
  registrationDate: string;
  createdBy: number;
  created_At: Date;
  id: number;
  update_At: Date;
  companyCode: string;
}

export function initCompanyInfo() {
  return {
    businessId: '',
    businessLine: '',
    code: '',
    companyForm: '',
    companyName: '',
    createdByPerson: '',
    registrationDate: '',
    createdBy: 0,
    created_At: null,
    id: 0,
    update_At: null,
    companyCode: '',
  };
}

export interface Company {
  id: string;
  name: string;
}
