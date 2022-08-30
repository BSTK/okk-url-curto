import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {FooterComponent} from './footer/footer.component';
import {HeaderComponent} from './header/header.component';
import { AlertComponent } from './alert/alert.component';

@NgModule({
  declarations: [
    FooterComponent,
    HeaderComponent,
    AlertComponent
  ],
  exports: [
    FooterComponent,
    HeaderComponent,
    AlertComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule
  ]
})
export class CoreModule { }
