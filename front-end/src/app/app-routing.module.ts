import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ImageNavigatorComponent } from './image-navigator/image-navigator.component';
import { ImageAddComponent } from './image-add/image-add.component';

const routes: Routes = [
    { path: 'add', component: ImageAddComponent },
    { path: '**', component: ImageNavigatorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
