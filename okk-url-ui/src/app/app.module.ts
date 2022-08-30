import {AppComponent} from './app.component';
import {CoreModule} from './core/core.module';
import ptBr from '@angular/common/locales/pt';
import {LOCALE_ID, NgModule} from '@angular/core';
import {registerLocaleData} from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {APP_ROUTING_PROVIDER, ROUTING} from './app.routing';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

registerLocaleData(ptBr);

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    ROUTING,
    CoreModule,
    BrowserModule,
    BrowserAnimationsModule
  ],
  providers: [
    APP_ROUTING_PROVIDER,
    {provide: LOCALE_ID, useValue: 'pt'},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
