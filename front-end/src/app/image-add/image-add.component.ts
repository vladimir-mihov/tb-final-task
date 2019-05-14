import {Component} from '@angular/core';

import {CreationData} from '../data/creation-data';
import {CRUDService} from '../crud.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-image-add',
    templateUrl: './image-add.component.html',
    styleUrls: ['./image-add.component.css']
})
export class ImageAddComponent {

    constructor(
        private imageService: CRUDService,
        private router: Router
    ) {
    }

    submit(data: CreationData) {
        this.imageService.create(data).subscribe(
            () => this.router.navigateByUrl('/')
        );
    }

}
