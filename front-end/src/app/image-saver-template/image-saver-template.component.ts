import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CreationData} from '../data/creation-data';

@Component({
    selector: 'app-image-saver-template',
    templateUrl: './image-saver-template.component.html',
    styleUrls: ['./image-saver-template.component.css']
})
export class ImageSaverTemplateComponent implements OnInit {

    @Input() buttonText = 'Save';
    @Output() submitData = new EventEmitter<CreationData>();

    imageSize = 256;
    _rows = 1;
    _cols = 1;

    rows = [0];
    cols = [0];

    @Input() title = '';

    canvas: HTMLCanvasElement;
    context: CanvasRenderingContext2D;

    inputContainer: HTMLElement;

    ngOnInit() {
        this.canvas = document.getElementById('canvas') as HTMLCanvasElement;
        this.context = this.canvas.getContext('2d') as CanvasRenderingContext2D;

        this.inputContainer = document.getElementById('input-container');
    }

    updateRows(): void {
        this.rows = new Array(this._rows).fill(0).map((x, i) => i);
        this.redrawAll();
    }

    updateCols(): void {
        this.cols = new Array(this._cols).fill(0).map((x, i) => i);
        this.redrawAll();
    }

    clearCanvas(row: number, col: number): void {
        this.context.clearRect(this.imageSize * col, this.imageSize * row,
            this.imageSize, this.imageSize);
    }

    getInputFields(row: number, col: number): [HTMLInputElement, HTMLInputElement] {
        const input = this.inputContainer.children[row].children[col];

        return [
            input.children[1] as HTMLInputElement,
            input.children[3] as HTMLInputElement
        ];
    }

    getAllInputFields(): [[HTMLInputElement, HTMLInputElement]] {
        // @ts-ignore
        const allInputFields: [[HTMLInputElement, HTMLInputElement]] = [];

        for (const i of this.rows) {
            for (const j of this.cols) {
                allInputFields.push(this.getInputFields(i, j));
            }
        }

        return allInputFields;
    }

    drawText(text: string, row: number, col: number) {
        this.context.fillStyle = '#fff';
        this.context.font = 'bold 30px helvetica';
        this.context.textAlign = 'center';

        const textX = col * this.imageSize + this.imageSize / 2;
        const textY = row * this.imageSize + this.imageSize / 2;

        this.context.fillText(text.toUpperCase(), textX, textY, this.imageSize);
        this.context.strokeText(text.toUpperCase(), textX, textY, this.imageSize);
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
                    col * this.imageSize, row * this.imageSize,
                    this.imageSize, this.imageSize);

                this.drawText(text, row, col);
            };

            fileReader.readAsDataURL(file);
        } else {
            this.drawText(text, row, col);
        }
    }

    redrawAll() {
        for (const i of this.rows) {
            for (const j of this.cols) {
                this.redraw(i, j);
            }
        }
    }

    submit() {
        const creationData = new CreationData(this.title, null);
        if (this.getAllInputFields()
            .some(inputs => !!inputs[0].value || !!inputs[1].files[0])
        ) {
            this.canvas.toBlob(
                blob => {
                    creationData.file = blob as File;
                    this.submitData.emit(creationData);
                }
            );
        } else {
            this.submitData.emit(creationData);
        }
    }
}
