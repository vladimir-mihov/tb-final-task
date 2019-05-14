import {Component, OnInit} from '@angular/core';
import {ImageData} from '../data/image-data';
import {CRUDService} from '../crud.service';
import {HttpClient} from '@angular/common/http';
import {Domain} from '../data/domain';

@Component({
    selector: 'app-remote-image-explorer',
    templateUrl: './remote-image-explorer.component.html',
    styleUrls: ['./remote-image-explorer.component.css']
})
export class RemoteImageExplorerComponent implements OnInit {

    images: ImageData[] = [];
    filteredImages: ImageData[] = [];

    page = 1;
    imagesPerPage = 5;
    filter = '';

    domainAPIURL = 'https://meme-it-platform-service-api.herokuapp.com/domain';
    domains: Domain[] = [];
    _currentDomain = new Domain('Choose a domain', null);

    constructor(
        private imageService: CRUDService,
        private http: HttpClient
    ) {
    }

    ngOnInit() {
        this.http.get<Domain[]>(this.domainAPIURL).subscribe(
            domains => this.domains = domains
        );
    }

    getImages() {
        this.imageService.readAll(this.currentDomain.address).subscribe(
            images => {
                this.images = images;
                this.filterImages();
            }
        );
    }

    filterImages() {
        if (this.filter) {
            this.filteredImages = this.images.filter(
                iData => {
                    const lowerCaseFilter = this.filter.toLowerCase();
                    return iData.title.toLowerCase().includes(lowerCaseFilter);
                }
            );
        } else {
            this.filteredImages = this.images;
        }
    }

    get currentDomain(): Domain {
        return this._currentDomain;
    }

    set currentDomain(value: Domain) {
        if (this._currentDomain === value) {
            return;
        }

        this._currentDomain = value;
        this.getImages();
    }

}
