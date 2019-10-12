import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {Education} from '../../../../allServices/modules/education';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.css']
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
              @Inject(MAT_DIALOG_DATA) public data: Education,
              private formBuilder: FormBuilder) {
    this.educationForm.setValue(data);
  }

  onNoClick() {
    this.dialogRef.close(null);
  }

  save() {
    this.dialogRef.close(this.educationForm);
  }

}
