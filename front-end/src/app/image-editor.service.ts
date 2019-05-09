import { Injectable } from '@angular/core';
import { ImageData } from './data/image-data';

@Injectable({
    providedIn: 'root'
})
export class ImageEditorService {
    public imageData: ImageData;
}
