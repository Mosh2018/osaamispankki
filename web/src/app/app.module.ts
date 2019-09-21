import {BrowserModule} from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {JwtModule} from '@auth0/angular-jwt';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MyCustomMaterialModule} from './my-custom-material.module';
import {LoginComponent} from './auth/login/login.component';
import {LogoutComponent} from './auth/logout/logout.component';
import {UserService} from './services/user.service';
import {HomeComponent} from './components/home/home.component';
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';
import {JwtInterceptor} from './helpers/jwt-interceptor';
import {ErrorInterceptor} from './helpers/error-interceptor';
import {GlobalErrorHandler} from './helpers/global-error-handler';
import {MainPageComponent} from './auth/main-page/main-page.component';
import {SignComponent} from './auth/sign/sign.component';
import {RegistrationSuccessComponent} from './auth/registration-success/registration-success.component';
import {ConfirmDialogComponent} from './stateless/confirm-dialog.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LogoutComponent,
    HomeComponent,
    PageNotFoundComponent,
    MainPageComponent,
    SignComponent,
    RegistrationSuccessComponent,
    ConfirmDialogComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    MyCustomMaterialModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return localStorage.getItem('currentUser');
        },
        whitelistedDomains: ['localhost:4200/'],
        blacklistedRoutes: ['http://localhost:4200/login', 'http://localhost:4200/sign']
      }
    })
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    {provide: ErrorHandler, useClass: GlobalErrorHandler, multi: false},
    UserService
  ],
  exports: [

  ],
  entryComponents: [ConfirmDialogComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
