import {Component, OnInit} from '@angular/core';

import {ImageEditorService} from '../image-editor.service';
import {CreationData} from '../data/creation-data';
import {Router} from '@angular/router';
import {CRUDService} from '../crud.service';

@Component({
    selector: 'app-image-editor',
    templateUrl: './image-editor.component.html',
    styleUrls: ['./image-editor.component.css']
})
export class ImageEditorComponent implements OnInit {

    constructor(
        private editorService: ImageEditorService,
        private imageService: CRUDService,
        private router: Router
    ) {
    }

    title = '';

    ngOnInit() {
        if (!this.editorService.imageData) {
            this.router.navigateByUrl('/');
        }

        this.title = this.editorService.imageData.title;
        document.getElementById('title').focus();
    }

    submit(data: CreationData) {
        this.imageService.update(this.editorService.imageData.id, data)
            .subscribe(
                () => this.router.navigateByUrl('/')
            );
    }

}
