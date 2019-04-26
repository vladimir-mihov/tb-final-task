import { ImageData } from './image-data';
import { Observable } from 'rxjs';

export interface CRUDStorage {
    create(image: ImageData): void;
    readAll(): Observable<ImageData[]>;
    update(oldImage: ImageData, newImage: ImageData): void;
    delete(image: ImageData): void;
}
