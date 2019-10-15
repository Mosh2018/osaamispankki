import {Component, OnInit} from '@angular/core';
import {Experience, initExperience} from '../../../../allServices/modules/experience';
import {MatDialog} from '@angular/material';
import {EducationAndExperienceService} from '../services/education-and-experience.service';
import {JwtService} from '../../../../allServices/services/jwt.service';
import {API_CONSTANTS} from '../../../../allServices/utils/global';
import {ExperienceComponent} from '../experience/experience.component';
import {ConfirmDialogComponent} from '../../../../allServices/shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-experiences',
  templateUrl: './experiences.component.html',
  styleUrls: ['./experiences.component.css']
})
export class ExperiencesComponent implements OnInit {
  displayedColumns: string[] = ['company', 'position', 'startYear', 'endYear', 'edit', 'delete'];
  experiences: Experience[] = [];
  experienceData = initExperience();
  user: any;

  constructor(public dialog: MatDialog,
              private service: EducationAndExperienceService,
              private jwt: JwtService) { }

  ngOnInit() {
    this.user = this.jwt.getUserFullInformation();
    this.getExperiences();
  }

  openDialog(id: number) {
    if (id === null) {
      this.experienceData = initExperience();
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
      if (data !== null && data !== undefined) {
        const xExperiences = [...this.experiences];
        this.service.save(API_CONSTANTS.EXPERIENCE, data.value).subscribe(
          (responseBE: Experience) => {
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
        throw Error('something goes wrong');
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
        yes: 'Delete',
        no: 'Cancel'
      }
    });

    confirmDialogRef.afterClosed().subscribe( confirm => {
      if (confirm !== null && confirm === 'YES') {
        // are you sure
        this.service.delete(API_CONSTANTS.EXPERIENCE, id).subscribe(x => {
          if (x) {
            this.experiences = this.experiences.filter(y => y.id !== id);
          }});
      }});
  }

  private getExperiences() {
    this.service.get(API_CONSTANTS.EXPERIENCE).subscribe((data: Experience[]) => {
      this.experiences = data === null ? [] : data;
    });
  }
}
