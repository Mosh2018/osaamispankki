import {Component, OnInit} from '@angular/core';
import {CvService} from '../../../services/cv.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {dateFormatChanger, formatDate} from '../../../helpers/utils.methods';
import {first} from 'rxjs/operators';
import {AuthenticationService} from '../../../services/authentication.service';

@Component({
  selector: 'app-cv-main',
  templateUrl: './cv-main.component.html',
  styleUrls: ['./cv-main.component.less']
})
export class CvMainComponent implements OnInit {
  picture: any = null;
  step = 0;
  companyStep = null;
  url: any;
  noPhotoError = null;
  setting: any;

  companiesForms: FormGroup[] = [];
  companies: string[] = ['netum', 'gofore', 'reactor'];
  employmentTypes: string[] = ['FULL_TIME', 'PART_TIME'];

  constructor(private cvService: CvService,
              private auth: AuthenticationService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.cvService.getValidations().subscribe(
      data => this.setting = data
    );
    this.getImage();
    this.getCompaniesForm();

    // get Companies to selects
  }

  initPicture(event: any) {
    this.picture = event;
  }

  handleFileInput() {
    const FormatData: FormData = new FormData();
    FormatData.append('file', this.picture.target.files[0], 'name');

    this.cvService.uploadFile(FormatData, 'upload_photo').subscribe(
      photo => {
        this.noPhotoError = null;
        this.picture = null;
        this.url = photo;
      });
  }

  setStep(i: number) {
    this.step = i;
  }

  setInnerStep(i: number) {
    this.companyStep = i;
  }

  getImage() {
    this.cvService.getPersonalImage().subscribe(photo => {
      this.noPhotoError = null;
      this.url = photo;
    }, error1 => {
      this.noPhotoError = error1;
    });
  }

  deletePhoto() {
    this.cvService.deletePersonalPhoto().subscribe((response: any) => {
      this.noPhotoError = response.success;
    });
  }

  saveEmploymentInformation(index: number) {
    this.companiesForms[index].controls.startingTime.setValue(dateFormatChanger(
      this.companiesForms[index].controls.startingTime.value));

    this.companiesForms[index].controls.endingTime.setValue(dateFormatChanger(
      this.companiesForms[index].controls.endingTime.value));

    this.cvService.saveCompany(this.companiesForms[index].value)
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
      this.cvService.deleteCompany(id).subscribe( x => {
        if (x) {
            this.companiesForms = this.companiesForms.filter(y => y.controls.id.value !== id);
        }
      });
    } else {
      this.companiesForms = this.companiesForms.filter(y => y.controls.id.value !== id);
    }
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
    this.cvService.getCompanies().subscribe( (data: any[]) => {

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


}
