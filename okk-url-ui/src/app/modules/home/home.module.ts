import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {HOME_ROUTES} from './home.module.routes';
import {CoreModule} from '../../core/core.module';
import {HomeComponent} from './pages/home/home.component';

@NgModule({
  declarations: [HomeComponent],
  imports: [
    CoreModule,
    RouterModule.forChild(HOME_ROUTES),
    CommonModule
  ]
})
export class HomeModule { }
