import { Component, Input } from '@angular/core';
import { ImageData } from '../data/image-data';

@Component({
    selector: 'app-images',
    templateUrl: './images.component.html',
    styleUrls: ['./images.component.css']
})
export class ImagesComponent {
    
    @Input() images: ImageData[];
    @Input() page: number;
    @Input() imagesPerPage: number;
    @Input() filter: string;
    
}
