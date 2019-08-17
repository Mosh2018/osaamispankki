import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../../../services/authentication.service';
import {MatDialog} from '@angular/material';
import {EducationData} from '../../../../models/education-data';

@Component({
  selector: 'app-educations',
  templateUrl: './educations.component.html',
  styleUrls: ['./educations.component.less']
})
export class EducationsComponent implements OnInit {

  user;
  data: EducationData = {
    userId: 1,
  hide: false,
  id: 1,
  nameOfInstitution: 'any things',
  degree: 'Mater',
  location: 'Tampere',
  startYear: new Date(),
  endYear: new Date(),
  };

  constructor(private auth: AuthenticationService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.user = this.auth.getUserFullInformation();
    console.log(this.user);
  }

  openDialog() {
    const dialogRef = this.dialog.open(EducationsComponent, {
      width: '500px',
      data : this.data
    });
  }
}
