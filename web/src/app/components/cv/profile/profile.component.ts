import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {CvService} from '../../../services/cv.service';
import {first} from 'rxjs/operators';
import {DateAdapter, MAT_DATE_FORMATS} from '@angular/material';
import {APP_DATE_FORMAT, AppDateAdapter} from '../../../helpers/dataAdapter';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.less'],
  providers: [
    {provide: DateAdapter, useClass: AppDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMAT}
  ]

})
export class ProfileComponent implements OnInit {
  personalInformationForm = this.fb.group({
    firstName: ['', Validators.required],
    surname: ['', Validators.required],
    birthday: [''],
    phoneNo: ['', Validators.required],
    address: this.fb.group({
      street: ['', Validators.required],
      postcode: ['', Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required]
    }),
  });

  errors = null;

  constructor(private fb: FormBuilder,
              private  cvService: CvService) {
  }

  ngOnInit() {
    console.log(this.errors);
    this.getPersonalInformation();
  }

  addProfile() {
    const test = new DatePipe('en-US')
      .transform(this.personalInformationForm.controls.birthday.value, 'dd.MM.yyyy');
    this.personalInformationForm.controls.birthday.setValue(test);
    this.cvService.saveProfile(this.personalInformationForm.value)
      .pipe(first()).subscribe(d => {
      this.initDataResponse(d);
      this.personalInformationForm.markAsUntouched();
    }, error1 => {
      this.errors = error1;
    });
  }

  private getPersonalInformation() {
    this.cvService.getPersonalInformation().subscribe(data => {
        this.initDataResponse(data);
      }, error1 => {
        this.errors = error1;
      }
    );
  }

  private initDataResponse(data) {
    console.log(data, "information")
    const date = this.formatDate(data);

    this.personalInformationForm.setValue(data);
    this.personalInformationForm.controls.birthday.setValue(date);
  }

  private formatDate(data: any) {
    if (data.birthday !== null) {
      const date = new Date(data.birthday.replace(/(\d{2}).(\d{2}).(\d{4})/, '$2/$1/$3'));
      new DatePipe('en-US').transform(date, 'dd.MM.yyyy');
      return date;
    }
    return data;
  }
}
