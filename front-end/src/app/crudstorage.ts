import {ImageData} from './data/image-data';
import {Observable} from 'rxjs';
import {CreationData} from './data/creation-data';

export interface CRUDStorage {
    create(data: CreationData): Observable<ImageData>;

    readAll(): Observable<ImageData[]>;

    update(oldImage: ImageData, newImage: CreationData): Observable<ImageData>;

    delete(imageID: number): Observable<ImageData>;
}
