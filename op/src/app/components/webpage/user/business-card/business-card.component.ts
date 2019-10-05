import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserAndCompanyService} from '../services/user-and-company.service';
import {first} from 'rxjs/operators';
import {dateFormatChanger, formatDate} from '../../../../allServices/utils/global';
import {PersonalService} from '../services/personal.service';

@Component({
  selector: 'app-business-card',
  templateUrl: './business-card.component.html',
  styleUrls: ['./business-card.component.css']
})
export class BusinessCardComponent implements OnInit {

  companiesForms: FormGroup[] = [];
  companies: string[] = ['netum', 'gofore', 'reactor'];
  employmentTypes: string[] = ['FULL_TIME', 'PART_TIME'];
  setting: any;
  companyStep = null;
  personalData: any;
  noPhotoError = null;
  photoUrl: any;
  picture: any = null;
  constructor(private formBuilder: FormBuilder,
              private  personalService: PersonalService,
              private cv: UserAndCompanyService) { }

  ngOnInit() {
    this.getSettingsFromBackend();
    this.getImage();
    this.getCompaniesForm();
    this.getPersonalInformation();
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
    this.companiesForms[index].controls.startingTime.setValue(dateFormatChanger(
      this.companiesForms[index].controls.startingTime.value));

    this.companiesForms[index].controls.endingTime.setValue(dateFormatChanger(
      this.companiesForms[index].controls.endingTime.value));

    this.cv.saveCompany(this.companiesForms[index].value)
      .pipe(first())
      .subscribe((data: any) => {
        const start = formatDate(data.startingTime);
        data.startingTime = start;
        const end = formatDate(data.endingTime);
        data.endingTime = end;
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
    console.log(id, 'ID');
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
      company: [''],
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
          const start = formatDate(data[i].startingTime);
          data[i].startingTime = start;
          const end = formatDate(data[i].endingTime);
          data[i].endingTime = end;
          this.companiesForms[i].setValue(data[i]);
        }
      } else {

      }
      // console.log(data, 'companies');
    });
  }


  private getPersonalInformation() {
    this.personalService.getPersonalInformation().subscribe(data => {
        this.personalData = data;
      }, error1 => {

      }
    );
  }
}
