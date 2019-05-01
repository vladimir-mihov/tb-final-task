import { ImageData } from './data/image-data';
import { Observable } from 'rxjs';
import { CreationData } from './data/creation-data';

export interface CRUDStorage {
    create(data: CreationData): void;
    readAll(): Observable<ImageData[]>;
    update(oldImage: ImageData, newImage: ImageData): void;
    delete(imageID: number): void;
}
