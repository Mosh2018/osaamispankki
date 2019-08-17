import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpRequest} from '@angular/common/http';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.less']
})
export class PageNotFoundComponent implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  handleFileInput(file: any) {
    console.log('payload', file);
    console.log('payload[0]', file);

    const formdata: FormData = new FormData();
    formdata.append('file', file.target.files[0]);
    this.http.post('http://localhost:8080/api/photo/upload_photo', formdata).subscribe(
      x => {console.log('file', x); },
        error1 => {console.log('error_pic', error1); }
    );
  }
}
