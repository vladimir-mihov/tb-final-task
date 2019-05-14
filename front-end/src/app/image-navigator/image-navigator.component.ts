import {Component, Input, OnInit} from '@angular/core';

import {ImageData} from '../data/image-data';
import {CRUDService} from '../crud.service';

@Component({
    selector: 'app-image-navigator',
    templateUrl: './image-navigator.component.html',
    styleUrls: ['./image-navigator.component.css']
})
export class ImageNavigatorComponent implements OnInit {

    images: ImageData[] = [];
    filteredImages: ImageData[] = [];

    page = 1;
    imagesPerPage = 5;

    filter = '';

    constructor(
        private imageService: CRUDService
    ) {
    }

    ngOnInit() {
        this.getImages();
    }

    getImages() {
        this.imageService.readAll().subscribe(
            images => {
                this.images = images;
                this.filterImages();
            }
        );
    }

    filterImages() {
        if (this.filter) {
            this.filteredImages = this.images.filter(
                iData => {
                    const lowerCaseFilter = this.filter.toLowerCase();
                    return iData.title.toLowerCase().includes(lowerCaseFilter);
                }
            );
        } else {
            this.filteredImages = this.images;
        }
    }

    delete(imageID: number) {
        if (!confirm('Are you sure that you want to delete this image?')) {
            return;
        }

        this.imageService.delete(imageID).subscribe(
            () => this.getImages()
        );
    }

}
