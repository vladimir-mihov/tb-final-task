import { Component, ViewChild, ElementRef } from '@angular/core';
import { InMemoryCRUDService } from '../in-memory-crud.service';
import { Router } from '@angular/router';
import { CreationData } from '../data/creation-data';

@Component({
    selector: 'app-image-add',
    templateUrl: './image-add.component.html',
    styleUrls: ['./image-add.component.css']
})
export class ImageAddComponent {
    
    @ViewChild('fileInput')
    fileInput: ElementRef;

    imageTitle: string;

    constructor(
        private imageService: InMemoryCRUDService,
        private router: Router
    ) {}

    upload() {
        const data = new CreationData(this.imageTitle, this.fileInput.nativeElement.files[0]);
        this.imageService.create(data).subscribe(
            res => {
                this.router.navigate(["/"]);
            },
            err => console.error(err)
        );
    }

}
