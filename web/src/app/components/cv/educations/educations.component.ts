import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../../services/authentication.service';
import {MatDialog} from '@angular/material';
import {EducationData, initEducationData} from '../../../../models/education-data';
import {EducationComponent} from './education/education.component';
import {CvService} from '../../../services/cv.service';
import {ConfirmDialogComponent} from '../../../stateless/confirm-dialog.component';

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

  constructor(private auth: AuthenticationService,
              private cv: CvService,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    this.user = this.auth.getUserFullInformation();
    this.getEducations();
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

      }
    });

  }
  openConfirmDialog(id: number) {
    const education = this.educations.find(x => x.id === id);
    const confirmDialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '500px',
      data: {
        title: '',
        message: 'your are going to delete your education in '  + education.nameOfInstitution + '!',
        yes: 'DELETE',
        no: 'CANCEL'
      }
    });

    confirmDialogRef.afterClosed().subscribe( confirm => {
      if (confirm !== null && confirm === 'YES') {
        // are you sure
        this.cv.deleteEducation(id).subscribe(x => {
          if (x ) {
            this.educations = this.educations.filter(y => y.id !== id);
          }});
      }});
  }

  private getEducations() {
    this.cv.getEducations().subscribe((data: EducationData[]) => {
      this.educations = data === null ? [] : data;
      console.log(data, 'refresh all data');
    });
  }

}
