import { Injectable } from '@angular/core';
import { ImageData } from './data/image-data';
import { Observable, of } from 'rxjs';
import { CRUDStorage } from './crudstorage';
import { CreationData } from './data/creation-data';

@Injectable({
  providedIn: 'root'
})
export class InMemoryCRUDService implements CRUDStorage {

    images: ImageData[] = [
        new ImageData(0, 'Meme 0', 'assets/loss.png'),
        new ImageData(1, 'Meme 1', 'assets/loss.png'),
        new ImageData(2, 'Meme 2', 'assets/loss.png'),
        new ImageData(3, 'Meme 3', 'assets/loss.png'),
        new ImageData(4, 'Meme 4', 'assets/loss.png'),
        new ImageData(5, 'Meme 5', 'assets/loss.png')
    ];

    create(data: CreationData): Observable<number> {
        const len = this.images.length;
        this.images.push(new ImageData(len, data.title, '/assets/loss.png'));
        return of(1);
    }

    readAll(): Observable<ImageData[]> {
        return of(this.images);
    }

    update(oldImage: ImageData, newImage: CreationData): void {
        this.images[ this.images.indexOf(oldImage) ].title = newImage.title;
    }

    delete(imageID: number): void {
        const indexToDelete = this.images.findIndex(iData => iData.id === imageID);
        this.images.splice(indexToDelete, 1);
    }
}
