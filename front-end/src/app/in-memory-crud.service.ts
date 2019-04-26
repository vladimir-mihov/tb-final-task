import { Injectable } from '@angular/core';
import { ImageData } from './image-data';
import { Observable, of } from 'rxjs';
import { CRUDStorage } from './crudstorage';

@Injectable({
  providedIn: 'root'
})
export class InMemoryCRUDService implements CRUDStorage {

    images: ImageData[] = [
        new ImageData(0, 'Best meme', 'assets/loss.png'),
        new ImageData(1, 'Best meme', 'assets/loss.png')
    ];

    create(image: ImageData): void {
        image.id = this.images.length;
        this.images.push(image);
    }
    
    readAll(): Observable<ImageData[]> {
        return of(this.images);
    }

    update(oldImage: ImageData, newImage: ImageData): void {
        if(newImage.title) { oldImage.title = newImage.title; }
        if(newImage.url) { oldImage.url = newImage.url; }
    }

    delete(image: ImageData): void {
        this.images.splice(image.id,1);
    }
}
