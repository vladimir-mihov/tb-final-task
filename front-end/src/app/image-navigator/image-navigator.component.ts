import { Component, OnInit } from '@angular/core';
import { InMemoryCRUDService } from '../in-memory-crud.service';
import { ImageData } from '../data/image-data';

@Component({
  selector: 'app-image-navigator',
  templateUrl: './image-navigator.component.html',
  styleUrls: ['./image-navigator.component.css']
})
export class ImageNavigatorComponent implements OnInit {

    images: ImageData[];

    page = 1;
    imagesPerPage = 5;
    filter = '';

    listedPages: number[];
    pageCount: number;

    constructor(private imageService: InMemoryCRUDService) {}

    ngOnInit() {
        this.getImages();
    }

    getImages() {
        this.imageService.readAll().subscribe(
            images => {
                this.images = images;
                this.updatePaging();
            }
        );
    }

    updatePaging() {
        this.pageCount = Math.ceil(this.images.length / this.imagesPerPage);

        const firstDisplayedPage = this.page > 1 ? this.page - 1 : 1;
        const lastDisplayedPage = firstDisplayedPage + 2 < this.pageCount ?
            firstDisplayedPage + 2 : this.pageCount;

        this.listedPages = new Array(lastDisplayedPage - firstDisplayedPage + 1).fill(0).map((x,i) => firstDisplayedPage + i);
    }

    delete(imageID: number) {
        if(!confirm('Are you sure that you want to delete this image?')) { return; }

        this.imageService.delete(imageID);
        this.getImages();
    }

}
