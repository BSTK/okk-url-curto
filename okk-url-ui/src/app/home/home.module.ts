import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {CoreModule} from '../core/core.module';
import {HOME_ROUTES} from './home.module.routes';
import {HomeComponent} from './home/home.component';

@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    CoreModule,
    RouterModule.forChild(HOME_ROUTES),
    CommonModule
  ]
})
export class HomeModule { }
