import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {EducationData} from '../../../../../models/education-data';

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.less']
})
export class EducationComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<EducationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EducationData ) { }

  ngOnInit() {
  }

  onNoClick() {

  }
}
