import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {EducationData} from '../../../../../models/education-data';
import {FormBuilder, Validators} from '@angular/forms';
import {dateFormatChanger, formatDate} from '../../../../helpers/utils.methods';



@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.less']
})
export class EducationComponent {

  educationForm = this.formBuilder.group({
    userId: [0],
    hide: [''],
    id: [0],
    nameOfInstitution: ['', Validators.required],
    degree: ['', Validators.required],
    location: ['', Validators.required],
    startYear: [],
    endYear: []
  });

  constructor(public dialogRef: MatDialogRef<EducationComponent>,
              @Inject(MAT_DIALOG_DATA) public data: EducationData,
              private formBuilder: FormBuilder) {

    for (const [key, value] of Object.entries(data)) {
      if (key === 'startYear'  || key === 'endYear') {
        this.educationForm.controls[key].setValue(formatDate(value));
      } else {
        this.educationForm.controls[key].setValue(value);
      }
    }

  }

  onNoClick() {
    this.dialogRef.close(null);
  }

  save() {
    this.educationForm.controls.startYear.setValue(dateFormatChanger(this.educationForm.controls.startYear.value));
    this.educationForm.controls.endYear.setValue(dateFormatChanger(this.educationForm.controls.endYear.value));
    this.dialogRef.close(this.educationForm);
  }
}
