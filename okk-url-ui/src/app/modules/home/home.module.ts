import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {HOME_ROUTES} from './home.module.routes';
import {CoreModule} from '../../core/core.module';
import {HomeComponent} from './pages/home/home.component';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [HomeComponent],
  imports: [
    CoreModule,
    FormsModule,
    RouterModule.forChild(HOME_ROUTES),
    CommonModule
  ]
})
export class HomeModule { }
