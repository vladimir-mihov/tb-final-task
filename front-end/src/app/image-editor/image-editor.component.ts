import {Component, OnInit} from '@angular/core';

import {ImageEditorService} from '../image-editor.service';
import {CreationData} from '../data/creation-data';
import {Router} from '@angular/router';
import {RemoteCRUDService} from '../remote-crud.service';

@Component({
    selector: 'app-image-editor',
    templateUrl: './image-editor.component.html',
    styleUrls: ['./image-editor.component.css']
})
export class ImageEditorComponent implements OnInit {

    titleInput: HTMLInputElement;
    fileInput: HTMLInputElement;

    constructor(
        private editorService: ImageEditorService,
        private imageService: RemoteCRUDService,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.titleInput = document.getElementById('title') as HTMLInputElement;
        this.fileInput = document.getElementById('file') as HTMLInputElement;

        this.titleInput.value = this.editorService.imageData.title;
        this.titleInput.focus();
    }

    submit() {
        const file = this.fileInput.files[0];

        this.imageService.update(
            this.editorService.imageData,
            new CreationData(this.titleInput.value, file)
        );

        this.router.navigateByUrl('/');
    }

}
