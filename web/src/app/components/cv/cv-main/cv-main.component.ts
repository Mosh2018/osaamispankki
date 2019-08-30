import {Component, OnInit} from '@angular/core';
import {CvService} from '../../../services/cv.service';

@Component({
  selector: 'app-cv-main',
  templateUrl: './cv-main.component.html',
  styleUrls: ['./cv-main.component.less']
})
export class CvMainComponent implements OnInit {
  picture: any = null;
  step = 0;
  url: any;
  noPhotoError = null;
  constructor(private cv: CvService) { }

  ngOnInit() {
    this.getImage();
    // get uploded picture
  }

  initPicture(event: any) {
    this.picture = event;
  }

  handleFileInput() {
    const FormatData: FormData = new FormData();
    FormatData.append('file', this.picture.target.files[0], 'name');

    this.cv.uploadFile(FormatData, 'upload_photo').subscribe(
      photo => {
        this.noPhotoError = null;
        this.url = photo;
      });
  }

  setStep(i: number) {
    this.step = i;
  }

  getImage() {
    this.cv.getPersonalImage().subscribe( photo => {
      this.noPhotoError = null;
      this.url = photo;
    }, error1 => {
      this.noPhotoError = error1;
      console.log('cv-main ', error1);
    });
  }

  deletePhoto() {
    this.cv.deletePersonalPhoto().subscribe( (response: any) => {
      this.noPhotoError = response.success;
    });
  }
}
