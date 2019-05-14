import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {NgbDropdownModule, NgbPaginationModule} from '@ng-bootstrap/ng-bootstrap';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ImageViewerComponent} from './image-viewer/image-viewer.component';
import {ImageNavigatorComponent} from './image-navigator/image-navigator.component';
import {ImageAddComponent} from './image-add/image-add.component';
import {ImageEditorComponent} from './image-editor/image-editor.component';
import {RemoteImageExplorerComponent} from './remote-image-explorer/remote-image-explorer.component';
import {ImageSaverTemplateComponent} from './image-saver-template/image-saver-template.component';

@NgModule({
    declarations: [
        AppComponent,
        ImageViewerComponent,
        ImageNavigatorComponent,
        ImageAddComponent,
        ImageEditorComponent,
        RemoteImageExplorerComponent,
        ImageSaverTemplateComponent
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
