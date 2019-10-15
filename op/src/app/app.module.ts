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
import {MainComponent} from './components/main/main.component';
import {HeaderComponent} from './components/webpage/header/header.component';
import {UserLoginComponent} from './components/login/user-login/user-login.component';
import {CompanyLoginComponent} from './components/login/company-login/company-login.component';
import {environment} from '../environments/environment';
import {X_URL} from './allServices/utils/global';
import {JwtInterceptor} from './allServices/interceptors/jwt-interceptor';
import {ErrorInterceptor} from './allServices/interceptors/error-interceptor';
import {AuthenticationService} from './allServices/services/authentication.service';
import {JwtService} from './allServices/services/jwt.service';
import {EndpointService} from './allServices/services/endpoint.service';
import {UserRegisterComponent} from './components/login/user-register/user-register.component';
import {InputComponent} from './allServices/common/input/input.component';
import {SuccessComponent} from './components/login/success/success.component';
import {DialogService} from './allServices/services/dialog.service';
import {MainPageComponent} from './components/webpage/main-page/main-page.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    HeaderComponent,
    UserLoginComponent,
    CompanyLoginComponent,
    UserRegisterComponent,
    InputComponent,
    SuccessComponent,
    MainPageComponent,
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
  entryComponents: [UserLoginComponent, UserRegisterComponent],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    AuthenticationService,
    JwtService,
    EndpointService,
    DialogService
  ],
  exports: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
