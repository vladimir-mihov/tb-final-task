import {Component, Input, Output, EventEmitter} from '@angular/core';
import {ImageData} from '../data/image-data';
import {Router} from '@angular/router';
import {ImageEditorService} from '../image-editor.service';

@Component({
    selector: 'app-images',
    templateUrl: './images.component.html',
    styleUrls: ['./images.component.css']
})
export class ImagesComponent {
    @Input() images: ImageData[];
    @Input() page: number;
    @Input() imagesPerPage: number;
    @Output() deleteEmitter = new EventEmitter<number>();

    constructor(
        private router: Router,
        private editorService: ImageEditorService
    ) {
    }

    edit(image: ImageData) {
        this.editorService.imageData = image;
        this.router.navigateByUrl('/edit');
    }

    delete(imageID: number) {
        this.deleteEmitter.emit(imageID);
    }
}
