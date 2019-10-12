import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Experience} from '../../../../allServices/modules/experience';

@Component({
  selector: 'app-experience',
  templateUrl: './experience.component.html',
  styleUrls: ['./experience.component.css']
})
export class ExperienceComponent{

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
              @Inject(MAT_DIALOG_DATA) public data: Experience,
              private formBuilder: FormBuilder) {
    this.experienceForm.setValue(data);
  }

  onNoClick() {
    this.dialogRef.close(null);
  }

  save() {
    this.dialogRef.close(this.experienceForm);
  }

}
