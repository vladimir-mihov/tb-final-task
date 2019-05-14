import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {CRUDStorage} from './crudstorage';
import {CreationData} from './data/creation-data';
import {ImageData} from './data/image-data';

@Injectable({
    providedIn: 'root'
})
export class CRUDService implements CRUDStorage {

    private serverURL = 'http://localhost:8080';
    private apiEndpoint = 'http://localhost:8080/meme';

    constructor(private http: HttpClient) {
    }

    private toFormData(data: CreationData): FormData {
        const formData = new FormData();
        formData.append('title', data.title);
        formData.append('file', data.file);
        return formData;
    }

    readAll(serverURL = this.serverURL): Observable<ImageData[]> {
        return this.http.get<ImageData[]>(serverURL + '/meme');
    }

    create(data: CreationData): Observable<ImageData> {
        return this.http.post<ImageData>(this.apiEndpoint, this.toFormData(data));
    }

    delete(imageID: number): Observable<ImageData> {
        return this.http.delete<ImageData>(`${this.apiEndpoint}/${imageID}`);
    }

    update(oldImageId: number, newImageData: CreationData): Observable<ImageData> {
        return this.http.put<ImageData>(`${this.apiEndpoint}/${oldImageId}`, this.toFormData(newImageData));
    }
}
