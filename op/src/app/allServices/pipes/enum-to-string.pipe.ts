import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'enumToString'
})
export class EnumToStringPipe implements PipeTransform {

  transform(value: any, ...args: any[]): string {

    switch (value) {
      case 'FULL_TIME':
        return 'full time';
      case 'PART_TIME':
        return 'part time';
      default:
        return 'not found';
    }
  }

}
