import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ImageNavigatorComponent } from './image-navigator/image-navigator.component';
import { ImageAddComponent } from './image-add/image-add.component';
import { ImageEditorComponent } from './image-editor/image-editor.component';

const routes: Routes = [
    { path: 'add', component: ImageAddComponent },
    { path: 'edit', component: ImageEditorComponent },
    { path: '**', component: ImageNavigatorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
