import { Component, OnInit } from '@angular/core';
import { InMemoryCRUDService } from '../in-memory-crud.service';
import { ImageData } from '../image-data';

@Component({
    selector: 'app-images',
    templateUrl: './images.component.html',
    styleUrls: ['./images.component.css']
})
export class ImagesComponent implements OnInit {
    
    images: ImageData[];
    visibleSlice = [0,7];

    constructor(private imageService: InMemoryCRUDService) {}
    
    ngOnInit() {
        this.getImages();
    }
    
    getImages() {
        this.imageService.readAll().subscribe( images => this.images = images );
    }
    
}
