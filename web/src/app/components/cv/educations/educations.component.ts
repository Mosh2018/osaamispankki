import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../../services/authentication.service';
import {MatDialog} from '@angular/material';
import {EducationData, initEducationData} from '../../../../models/education-data';
import {EducationComponent} from './education/education.component';
import {CvService} from '../../../services/cv.service';

@Component({
  selector: 'app-educations',
  templateUrl: './educations.component.html',
  styleUrls: ['./educations.component.less']
})
export class EducationsComponent implements OnInit {

  user;
  educationData = initEducationData();
  educations: EducationData[] = [];
  displayedColumns: string[] = ['nameOfInstitution', 'degree', 'location', 'startYear', 'endYear', 'edit', 'delete'];
  deleted = false;

  constructor(private auth: AuthenticationService,
              private cv: CvService,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    this.user = this.auth.getUserFullInformation();
    this.getEducations();
    console.log(this.user);
  }

  openDialog(id: number) {
    if (id === null) {
      this.educationData = initEducationData();
    } else {
      this.educationData = this.educations.find(x => x.id === id);
    }

    const dialogRef = this.dialog.open(EducationComponent, {
      width: '800px',
      maxWidth: '800px',
      minWidth: '300px',
      data: this.educationData
    });

    dialogRef.afterClosed().subscribe(data => {
      console.log('The dialog was closed');
      if (data !== null && data !== undefined) {
        const xEducations = [...this.educations];
        this.cv.saveEducation(data.value).subscribe(
          (responseBE: EducationData) => {
            const index = xEducations.findIndex(x => x.id === responseBE.id);
            if (index > -1) {
              xEducations[index] = responseBE;
              this.educations = xEducations;
            } else {
              xEducations.push(responseBE);
              this.educations = xEducations;
            }
          }
        );
      } else {
        console.log('do nothing');
      }
    });

  }

  private getEducations() {
    this.cv.getEducations().subscribe((data: EducationData[]) => {
      this.educations = data;
      console.log(data, 'refresh all data');
    }, error1 => {

    });
  }

  delete(id: number) {
    this.cv.delete(id).subscribe( x => {
      if (x ) {
        this.educations = this.educations.filter(x => x.id !== id);
      }

    }, err => {

    });
  }
}
