import { Component, OnInit } from '@angular/core';
import {Education, initEducation} from '../../../../allServices/modules/education';
import {EducationAndExperienceService} from '../services/education-and-experience.service';
import {JwtService} from '../../../../allServices/services/jwt.service';
import {MatDialog} from '@angular/material';
import {ConfirmDialogComponent} from '../../../../allServices/shared/confirm-dialog/confirm-dialog.component';
import {EducationComponent} from '../education/education.component';
import {API_CONSTANTS} from '../../../../allServices/utils/global';

@Component({
  selector: 'app-education',
  templateUrl: './educations.component.html',
  styleUrls: ['./educations.component.css']
})
export class EducationsComponent implements OnInit {
  displayedColumns: string[] = ['nameOfInstitution', 'degree', 'location', 'startYear', 'endYear', 'edit', 'delete'];
  user: any;
  educations: Education[] = [];
  educationData = initEducation();


  constructor(public dialog: MatDialog,
              private service: EducationAndExperienceService,
              private jwt: JwtService) { }

  ngOnInit() {
    this.user = this.jwt.getUserFullInformation();
    this.getEducations();
  }

  openDialog(id: number) {
    if (id === null) {
      this.educationData = initEducation();
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
      if (data !== null && data !== undefined) {
        const xEducations = [...this.educations];
        this.service.save(API_CONSTANTS.EDUCATION, data.value).subscribe(
          (responseBE: Education) => {
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
        console.log('something goes wrong');
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
        yes: 'Delete',
        no: 'Cancel'
      }
    });

    confirmDialogRef.afterClosed().subscribe( confirm => {
      if (confirm !== null && confirm === 'YES') {
        // are you sure
        this.service.delete(API_CONSTANTS.EDUCATION, id).subscribe(x => {
          if (x ) {
            this.educations = this.educations.filter(y => y.id !== id);
          }});
      }});
  }

  private getEducations() {
    this.service.get(API_CONSTANTS.EDUCATION).subscribe((data: Education[]) => {
      this.educations = data === null ? [] : data;
    });
  }
}
