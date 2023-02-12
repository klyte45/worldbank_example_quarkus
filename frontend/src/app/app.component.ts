import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { startWith, map, switchMap } from 'rxjs/operators';
import { BackendService, Country } from './service/backend.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'world-bank-fe';

  countryData = new FormControl();
  constructor(private backend: BackendService) { }
  private async _filter(value: string) {
    const filterValue = value.toLowerCase();
    return (await this.backend.getCountryList()).filter(option => (option.id + "\n" + option.name).toLowerCase().indexOf(filterValue) !== -1);
  }
  filteredOptions: Observable<Country[]>;
  currentFilterOptions: Country[];

  ngOnInit() {
    this.filteredOptions = this.countryData.valueChanges.pipe(
      startWith(''),
      switchMap(async (value) => await this._filter(value))
    );
    this.filteredOptions.subscribe(x => this.currentFilterOptions = x);
  }

  displayNameFn(value?: string) {
    return value ? this.currentFilterOptions.filter(x => x.id === value)[0]?.name : undefined;
  }
  loadDataForCountry() {
    throw new Error('Method not implemented.');
  }
}
