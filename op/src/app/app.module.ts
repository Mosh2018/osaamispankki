import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {JwtModule} from '@auth0/angular-jwt';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import { MainComponent } from './components/main/main.component';
import { HeaderComponent } from './components/webpage/header/header.component';
import { UserLoginComponent } from './components/login/user-login/user-login.component';
import { CompanyLoginComponent } from './components/login/company-login/company-login.component';
import {environment} from '../environments/environment';
import {X_URL} from './allServices/utils/global';
import {JwtInterceptor} from './allServices/interceptors/jwt-interceptor';
import {ErrorInterceptor} from './allServices/interceptors/error-interceptor';
import {LoginService} from './allServices/services/login.service';
import {AuthenticationService} from './allServices/services/authentication.service';
import {JwtService} from './allServices/services/jwt.service';
import { UserMainPageComponent } from './components/webpage/user/user-main-page/user-main-page.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    HeaderComponent,
    UserLoginComponent,
    CompanyLoginComponent,
    UserMainPageComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return localStorage.getItem('currentUser');
        },
        whitelistedDomains: [environment.url],
        blacklistedRoutes: [
          environment.url + X_URL.LOGIN,
          environment.url + X_URL.REGISTER,
          environment.url + X_URL.LOGOUT]
      }
    })
  ],
  entryComponents: [UserLoginComponent],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    LoginService,
    AuthenticationService,
    JwtService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
