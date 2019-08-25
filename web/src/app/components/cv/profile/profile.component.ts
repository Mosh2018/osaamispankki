import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {CvService} from '../../../services/cv.service';
import {first} from 'rxjs/operators';
import {DatePipe} from '@angular/common';
import {dateFormatChanger, formatDate} from '../../../helpers/utils.methods';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.less'],
  providers: []

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

    this.personalInformationForm.controls.birthday.setValue(dateFormatChanger(
      this.personalInformationForm.controls.birthday.value));
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
    const date = formatDate(data.birthday);

    this.personalInformationForm.setValue(data);
    this.personalInformationForm.controls.birthday.setValue(date);
  }
}
