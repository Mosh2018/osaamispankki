import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {ObjectId} from '../../modules/common';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {

  @Input()
  dataSource: MatTableDataSource<any>;
  @Input()
  displayedColumns: ObjectId[];
  columnId: string[];
  columnName: string[];
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  ngOnInit() {
    this.columnId = this.displayedColumns.map( x => x.id);
    this.columnName = this.displayedColumns.map( x => x.name);
    this.dataSource.paginator = this.paginator;
  }
}
