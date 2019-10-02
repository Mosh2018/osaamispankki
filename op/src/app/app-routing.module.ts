import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserModule} from './components/webpage/user/user.module';


const routes: Routes = [
  {path: 'user',
    loadChildren: () => import(`./components/webpage/user/user.module`).then( m => m.UserModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule, UserModule]
})
export class AppRoutingModule { }
