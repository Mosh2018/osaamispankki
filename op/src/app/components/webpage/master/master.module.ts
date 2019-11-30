import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MasterRoutingModule} from './master-routing.module';
import {MasterComponent} from './master.component';
import {SharedModule} from '../../../shared.module';


@NgModule({
  declarations: [MasterComponent],
  imports: [
    CommonModule,
    MasterRoutingModule,
    SharedModule
  ],
  entryComponents: [MasterComponent],
})
export class MasterModule { }
