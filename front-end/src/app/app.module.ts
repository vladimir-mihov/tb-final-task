import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ImagesComponent } from './images/images.component';
import { ImageNavigatorComponent } from './image-navigator/image-navigator.component';
import { FilterIncludesPipe } from './filter-includes.pipe';
import { ImageAddComponent } from './image-add/image-add.component';

@NgModule({
  declarations: [
    AppComponent,
    ImagesComponent,
    ImageNavigatorComponent,
    FilterIncludesPipe,
    ImageAddComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
