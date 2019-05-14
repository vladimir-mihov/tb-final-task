import {ImageData} from './data/image-data';
import {Observable} from 'rxjs';
import {CreationData} from './data/creation-data';

export interface CRUDStorage {
    // Public api
    readAll(serverURL?: string): Observable<ImageData[]>;

    // Private api
    create(data: CreationData): Observable<ImageData>;

    update(oldImageId: number, newImage: CreationData): Observable<ImageData>;

    delete(imageID: number): Observable<ImageData>;
}
