import {Component, OnInit} from '@angular/core';
import {CvService} from '../../../services/cv.service';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-cv-main',
  templateUrl: './cv-main.component.html',
  styleUrls: ['./cv-main.component.less']
})
export class CvMainComponent implements OnInit {
  picture: any = null;
  step = 0;
  url: any;
  noPhotoError = null;

  companyForm = this.formBuilder.group({
    company: [],
    position: [],
    employmentType: [],
    startingTime: [],
    endingTime: [],
    salary:[],
    companyCode: []
  });
  companies: string[] = ['netum', 'gofore'];
  employmentTypes: string[] = ['full time', 'part time'];

  constructor(private cv: CvService,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.getImage();
    // get companies
    // get company form
  }

  initPicture(event: any) {
    this.picture = event;
  }

  handleFileInput() {
    const FormatData: FormData = new FormData();
    FormatData.append('file', this.picture.target.files[0], 'name');

    this.cv.uploadFile(FormatData, 'upload_photo').subscribe(
      photo => {
        this.noPhotoError = null;
        this.picture = null;
        this.url = photo;
      });
  }

  setStep(i: number) {
    this.step = i;
  }

  getImage() {
    this.cv.getPersonalImage().subscribe( photo => {
      this.noPhotoError = null;
      this.url = photo;
    }, error1 => {
      this.noPhotoError = error1;
      console.log('cv-main ', error1);
    });
  }

  deletePhoto() {
    this.cv.deletePersonalPhoto().subscribe( (response: any) => {
      this.noPhotoError = response.success;
    });
  }

  saveEmploymentInformation() {
    console.log(this.companyForm.value);
  }
}
