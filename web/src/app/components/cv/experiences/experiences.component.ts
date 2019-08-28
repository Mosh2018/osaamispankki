import {Component, OnInit} from '@angular/core';
import {ExperienceData, initExperienceData} from '../../../../models/education-data';
import {AuthenticationService} from '../../../services/authentication.service';
import {CvService} from '../../../services/cv.service';
import {MatDialog} from '@angular/material';
import {ExperienceComponent} from './experience/experience.component';
import {ConfirmDialogComponent} from '../../../stateless/confirm-dialog.component';

@Component({
  selector: 'app-experiences',
  templateUrl: './experiences.component.html',
  styleUrls: ['./experiences.component.less']
})
export class ExperiencesComponent implements OnInit {

  user;
  experienceData = initExperienceData();
  experiences: ExperienceData[] = [];
  displayedColumns: string[] = ['company', 'position', 'startYear', 'endYear', 'edit', 'delete'];

  constructor(private auth: AuthenticationService,
              private cv: CvService,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    this.user = this.auth.getUserFullInformation();
    this.getExperiences();
  }

  openDialog(id: number) {
    if (id === null) {
      this.experienceData = initExperienceData();
    } else {
      this.experienceData = this.experiences.find(x => x.id === id);
    }

    const dialogRef = this.dialog.open(ExperienceComponent, {
      width: '800px',
      maxWidth: '800px',
      minWidth: '300px',
      data: this.experienceData
    });

    dialogRef.afterClosed().subscribe(data => {
      console.log('The dialog was closed');
      if (data !== null && data !== undefined) {
        const xExperiences = [...this.experiences];
        this.cv.saveExperience(data.value).subscribe(
          (responseBE: ExperienceData) => {
            const index = xExperiences.findIndex(x => x.id === responseBE.id);
            if (index > -1) {
              xExperiences[index] = responseBE;
              this.experiences = xExperiences;
            } else {
              xExperiences.push(responseBE);
              this.experiences = xExperiences;
            }
          }
        );
      } else {
        console.log('do nothing');
      }
    });

  }

  openConfirmDialog(id: number) {
    const experience = this.experiences.find(x => x.id === id);
    const confirmDialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '500px',
      data: {
        title: '',
        message: 'your are going to delete your experience in '  + experience.company + '!',
        yes: 'DELETE',
        no: 'CANCEL'
      }
    });

    confirmDialogRef.afterClosed().subscribe( confirm => {
      if (confirm !== null && confirm === 'YES') {
        // are you sure
        this.cv.deleteExperience(id).subscribe(x => {
          if (x) {
            this.experiences = this.experiences.filter(y => y.id !== id);
          }});
      }});
  }

  private getExperiences() {
    this.cv.getExperiences().subscribe((data: ExperienceData[]) => {
      this.experiences = data === null ? [] : data;
      console.log(data, 'refresh all data');
    });
  }
}

