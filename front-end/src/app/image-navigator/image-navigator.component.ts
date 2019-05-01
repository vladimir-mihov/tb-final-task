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

    constructor(private imageService: InMemoryCRUDService) {}
    
    ngOnInit() {
        this.getImages();
    }
    
    getImages() {
        this.imageService.readAll().subscribe( images => this.images = images );
    }

}
