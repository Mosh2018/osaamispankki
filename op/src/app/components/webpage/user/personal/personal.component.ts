import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {PersonalService} from '../services/personal.service';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-personal',
  templateUrl: './personal.component.html',
  styleUrls: ['./personal.component.css']
})
export class PersonalComponent implements OnInit {

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
              private  personalService: PersonalService) {
  }

  ngOnInit() {
    this.getPersonalInformation();
  }

  addProfile() {
    this.personalService.savePersonalInformation(this.personalInformationForm.value)
      .pipe(first()).subscribe(d => {
      this.initDataResponse(d);
      this.personalInformationForm.markAsUntouched();
    }, error1 => {
      this.errors = error1;
    });
  }

  private getPersonalInformation() {
    this.personalService.getPersonalInformation().subscribe(data => {
        this.initDataResponse(data);
      }, error1 => {
        this.errors = error1;
      }
    );
  }

  private initDataResponse(data) {
    this.personalInformationForm.setValue(data);
  }

}
