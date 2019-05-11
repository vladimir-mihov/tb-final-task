import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {CreationData} from '../data/creation-data';
import {RemoteCRUDService} from '../remote-crud.service';

@Component({
    selector: 'app-image-add',
    templateUrl: './image-add.component.html',
    styleUrls: ['./image-add.component.css']
})
export class ImageAddComponent implements OnInit {

    _imageSize = 256;

    _rows = 1;
    _cols = 1;

    rows = [0];
    cols = [0];

    title = '';

    canvas: HTMLCanvasElement;
    context: CanvasRenderingContext2D;

    inputContainer: HTMLElement;

    constructor(
        private imageService: RemoteCRUDService,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.canvas = document.getElementById('canvas') as HTMLCanvasElement;
        this.context = this.canvas.getContext('2d') as CanvasRenderingContext2D;

        this.inputContainer = document.getElementById('input-container');
    }

    updateRows(): void {
        this.rows = new Array(this._rows).fill(0).map((x, i) => i);
    }

    updateCols(): void {
        this.cols = new Array(this._cols).fill(0).map((x, i) => i);
    }

    clearCanvas(row: number, col: number): void {
        this.context.clearRect(this._imageSize * col, this._imageSize * row,
            this._imageSize, this._imageSize);
    }

    getInputFields(row: number, col: number): [HTMLInputElement, HTMLInputElement] {
        const input = this.inputContainer.children[row].children[col];

        return [
            input.children[1] as HTMLInputElement,
            input.children[3] as HTMLInputElement
        ];
    }

    drawText(text: string, row: number, col: number) {
        this.context.fillStyle = '#fff';
        this.context.font = 'bold 30px helvetica';
        this.context.textAlign = 'center';

        const textX = col * this._imageSize + this._imageSize / 2;
        const textY = row * this._imageSize + this._imageSize / 2;

        this.context.fillText(text.toUpperCase(), textX, textY, this._imageSize);
        this.context.strokeText(text.toUpperCase(), textX, textY, this._imageSize);
    }

    redraw(row: number, col: number) {
        this.clearCanvas(row, col);

        const inputs = this.getInputFields(row, col);
        const text = inputs[0].value;
        const file = inputs[1].files[0];

        if (file) {
            const fileReader = new FileReader();
            const img = new Image();

            fileReader.onload = () => {
                img.src = fileReader.result as string;
            };

            img.onload = () => {
                this.context.drawImage(img,
                    col * this._imageSize, row * this._imageSize,
                    this._imageSize, this._imageSize);

                this.drawText(text, row, col);
            };

            fileReader.readAsDataURL(file);
        } else {
            this.drawText(text, row, col);
        }
    }

    submit() {
        this.canvas.toBlob(blob => {
            this.imageService.create(new CreationData(this.title, blob as File));
            this.router.navigateByUrl('/');
        });
    }
}
