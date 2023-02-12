import { HttpErrorResponse } from '@angular/common/http';
import { Component, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { startWith, map, switchMap } from 'rxjs/operators';
import { BackendService, Country, PovertyRatioYearData } from './service/backend.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Índice de Pobreza - Banco Mundial';

  constructor(private backend: BackendService) { }
  private async _filter(value: string) {
    const filterValue = value.toLowerCase();
    return (await this.backend.getCountryList()).filter(option => (option.id + "\n" + option.name).toLowerCase().indexOf(filterValue) !== -1);
  }

  countryData = new FormControl();
  filteredOptions: Observable<Country[]>;
  currentFilterOptions: Country[];
  dataSource = new MatTableDataSource<PovertyRatioYearData>();
  displayedColumns: string[] = ['year', 'name'];
  errorMessage: string;
  tableTitle: string;
  @ViewChild('paginator') paginator: MatPaginator;

  ngOnInit() {
    this.filteredOptions = this.countryData.valueChanges.pipe(
      startWith(''),
      switchMap(async (value) => await this._filter(value))
    );
    this.filteredOptions.subscribe(x => this.currentFilterOptions = x);
    this.countryData.setValidators((x) => {
      if (!this.displayNameFn(x.value)) {
        const filterByName = this.currentFilterOptions?.filter(y => y.name == x.value);
        if (filterByName?.length) {
          x.setValue(filterByName[0].id);
          return null;
        }
        return { "countryData": "País inválido" }
      }
      return null;
    })
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  displayNameFn(value?: string) {
    return value ? this.currentFilterOptions.filter(x => x.id === value)[0]?.name : undefined;
  }

  loadDataForCountry() {
    if (this.countryData.valid) {
      this.backend.getPovertyData(this.countryData.value).subscribe(x => {
        this.dataSource.data = x;
        this.errorMessage = null;
        this.tableTitle = `Exibindo dados para: ${this.displayNameFn(this.countryData.value)}`
      }, e => {
        this.dataSource.data = [];
        if (e instanceof HttpErrorResponse) {
          if (e.status == 404) {
            this.errorMessage = "Não há dados para este país/região"
          } else {
            this.errorMessage = `Ocorreu um erro ao obter os dados (HTTP ${e.status}):\n${e.message}`
          }
        } else {
          this.errorMessage = `Ocorreu um erro ao obter os dados\n${e.message}`
        }
      });
    }
  }

}
