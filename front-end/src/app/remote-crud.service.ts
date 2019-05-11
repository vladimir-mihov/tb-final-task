import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

import {CRUDStorage} from './crudstorage';
import {CreationData} from './data/creation-data';
import {ImageData} from './data/image-data';
import {catchError} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class RemoteCRUDService implements CRUDStorage {

    private apiEndpoint = 'http://localhost:8080/meme';

    constructor(private http: HttpClient) {
    }

    readAll(): Observable<ImageData[]> {
        return this.http.get<ImageData[]>(this.apiEndpoint);
    }

    create(data: CreationData): Observable<ImageData> {
        const formData = new FormData();
        formData.append('title', data.title);
        formData.append('file', data.file);
        return this.http.post<ImageData>(this.apiEndpoint, formData);
    }

    delete(imageID: number): Observable<ImageData> {
        return this.http.delete<ImageData>(`${this.apiEndpoint}/${imageID}`);
    }

    update(oldImage: ImageData, newImage: CreationData): Observable<ImageData> {
        return null;
    }
}
