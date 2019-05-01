import { Pipe, PipeTransform } from '@angular/core';
import { ImageData } from './data/image-data'

@Pipe({
    name: 'filterIncludes',
    pure: false
})
export class FilterIncludesPipe implements PipeTransform {
    
    transform(arr: ImageData[], str: string ): ImageData[] {
        return arr.filter(s => s.title.toLowerCase().includes(str.toLowerCase()));
    }
    
}
