import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {p} from '../../../allServices/utils/global';

@Injectable({
  providedIn: 'root'
})
export class EndpointMasterService {

  private serverUrl =  environment.url;
  private MASTER = '/api/master-admin-master';
  constructor(private http: HttpClient) {}

  codeGenerator() {
    return p(this.http.get(this.serverUrl + this.MASTER + '/generate'));
  }

  getUsed() {
    return p(this.http.get(this.serverUrl + this.MASTER + '/usedActivatedCodes'));
  }

  getNotUsed() {
    return p(this.http.get(this.serverUrl + this.MASTER + '/notUsedActivatedCodes'));
  }

}
