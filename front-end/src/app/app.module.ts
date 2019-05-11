import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {NgbDropdownModule, NgbPaginationModule} from '@ng-bootstrap/ng-bootstrap';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ImagesComponent} from './images/images.component';
import {ImageNavigatorComponent} from './image-navigator/image-navigator.component';
import {ImageAddComponent} from './image-add/image-add.component';
import {ImageEditorComponent} from './image-editor/image-editor.component';

@NgModule({
    declarations: [
        AppComponent,
        ImagesComponent,
        ImageNavigatorComponent,
        ImageAddComponent,
        ImageEditorComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        NgbPaginationModule,
        NgbDropdownModule
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
