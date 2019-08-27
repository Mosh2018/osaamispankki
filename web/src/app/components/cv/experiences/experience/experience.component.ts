import {Component, Inject} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {ExperienceData} from '../../../../../models/education-data';
import {dateFormatChanger, formatDate} from '../../../../helpers/utils.methods';

@Component({
  selector: 'app-experience',
  templateUrl: './experience.component.html',
  styleUrls: ['./experience.component.less']
})
export class ExperienceComponent {

  experienceForm = this.formBuilder.group({
    userId: [0],
    hide: [''],
    id: [0],
    company: ['', Validators.required],
    position: ['', Validators.required],
    description: ['', Validators.required],
    startYear: [],
    endYear: []
  });
  constructor(public dialogRef: MatDialogRef<ExperienceComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ExperienceData,
              private formBuilder: FormBuilder) {

    for (const [key, value] of Object.entries(data)) {
      if (key === 'startYear'  || key === 'endYear') {
        this.experienceForm.controls[key].setValue(formatDate(value));
      } else {
        this.experienceForm.controls[key].setValue(value);
      }
    }
  }

  onNoClick() {
    this.dialogRef.close(null);
  }

  save() {
    this.experienceForm.controls.startYear.setValue(dateFormatChanger(this.experienceForm.controls.startYear.value));
    this.experienceForm.controls.endYear.setValue(dateFormatChanger(this.experienceForm.controls.endYear.value));
    this.dialogRef.close(this.experienceForm);
  }
}
