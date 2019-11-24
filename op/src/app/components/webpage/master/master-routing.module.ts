import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MasterComponent} from './master.component';
import {MasterGuard} from '../../../allServices/guards/master.guard';



const routes: Routes = [{ path: '', component: MasterComponent, children: [], canActivate: [MasterGuard]}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MasterRoutingModule { }
