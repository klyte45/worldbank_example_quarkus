import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import { observable, Observable, PartialObserver } from 'rxjs';

export interface Country {
  id: string,
  name: string
}

export interface PovertyRatioYearData {
  year: number,
  value: number | null
}

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private http: HttpClient) { }

  private cachedCountryList: Country[]

  async getCountryList(): Promise<Country[]> {
    if (!this.cachedCountryList) {
      this.cachedCountryList = await this.http.get(environment.urlBase + `/country`).pipe(map((val) => val as Country[])).toPromise();
    }
    return this.cachedCountryList;
  }

  

}
