import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {UserAndCompanyService} from '../services/user-and-company.service';
import {first} from 'rxjs/operators';
import {PersonalService} from '../services/personal.service';
import {initPersonalInfo, PersonalInfo} from '../../../../allServices/modules/personal-info';
import {RegisterService} from '../../company/services/register.service';
import {Company} from '../../../../allServices/modules/company-info';

@Component({
  selector: 'app-business-card',
  templateUrl: './business-card.component.html',
  styleUrls: ['./business-card.component.css']
})
export class BusinessCardComponent implements OnInit {

  companiesForms: FormGroup[] = [];
  companies: Company[] = [];
  employmentTypes: string[] = ['FULL_TIME', 'PART_TIME'];
  setting: any;
  companyStep = null;
  personalData = initPersonalInfo();
  noPhotoError = null;
  photoUrl: any;
  picture: any = null;
  constructor(private formBuilder: FormBuilder,
              private personalService: PersonalService,
              private registered: RegisterService,
              private cv: UserAndCompanyService) { }

  ngOnInit() {
    this.getSettingsFromBackend();
    this.getImage();
    this.getCompaniesForm();
    this.getPersonalInformation();
    this.getAllRegisteredCompanies();
  }

  initPicture(event: any) {
    this.picture = event;
  }

  setInnerStep(i: number) {
    this.companyStep = i;
  }

  uploadPhoto() {
    const formData: FormData = new FormData();
    formData.append('file', this.picture.target.files[0], 'name');
    this.cv.uploadFile(formData, 'upload_photo').subscribe(
      photo => {
        this.noPhotoError = null;
        this.picture = null;
        this.photoUrl = photo;
      });
  }

  getImage() {
    this.cv.getPersonalImage().subscribe(photo => {
      this.noPhotoError = null;
      this.photoUrl = photo;
    }, error1 => {
      this.noPhotoError = error1;
    });
  }

  deleteImage() {
    this.cv.deletePersonalPhoto().subscribe((response: any) => {
      this.noPhotoError = response.success;
    });
  }

  saveEmploymentInformation(index: number) {
    this.cv.saveCompany(this.companiesForms[index].value)
      .pipe(first())
      .subscribe((data: any) => {
        this.companiesForms[index].setValue(data);
      }, error1 => {
        console.log('Error in saving company', index, error1);
      });
  }

  addCompany() {
    if ( this.companiesForms !== null && this.companiesForms.length < this.setting.max_COMPANIES) {
      this.companiesForms.push(this.initCompanyForm());
      this.companyStep = this.companiesForms.length - 1;
    }
  }

  deleteCompany(i: number) {
    const id = this.companiesForms[i].controls.id.value;
    if ( id !== null) {
      this.cv.deleteCompany(id).subscribe( x => {
        if (x) {
          this.companiesForms = this.companiesForms.filter(y => y.controls.id.value !== id);
        }
      });
    } else {
      this.companiesForms = this.companiesForms.filter(y => y.controls.id.value !== id);
    }
  }

  private getSettingsFromBackend() {
    this.cv.getValidations().subscribe(
      data => this.setting = data
    );
  }


  private initCompanyForm() {
    return this.formBuilder.group({
      id: [],
      company_name: [''],
      position: [],
      employmentType: [],
      startingTime: [],
      endingTime: [],
      salary: [],
      companyCode: [],
      role: [],
      admittedCompany: [],
      admittedCompanyRole: []
    });

  }

  private getCompaniesForm() {
    this.cv.getCompanies().subscribe( (data: any[]) => {
      if (data !== null) {
        for (let i = 0; i < data.length; i++) {
          this.companiesForms.push(this.initCompanyForm());
          this.companiesForms[i].setValue(data[i]);
        }
      } else {

      }
      // console.log(data, 'companies');
    });
  }


  private getPersonalInformation() {
    this.personalService.getPersonalInformation().subscribe((data: PersonalInfo) => {
        this.personalData = data;
      }, error1 => {

      }
    );
  }

  private getAllRegisteredCompanies() {
    this.registered.getRegisteredCompanies().subscribe( data => {
      this.companies = data;
    });

  }
}
