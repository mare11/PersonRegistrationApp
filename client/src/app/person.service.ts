import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Person } from './person-dialog/person-dialog.component';

const baseUrl = '/persons/';

@Injectable({
  providedIn: 'root'
})
export class PersonService {


  constructor(private http: HttpClient) { }

  getPersons() {
    return this.http.get(baseUrl);
  }

  searchPersons(searchQuery) {
    return this.http.get(baseUrl + 'search/' + searchQuery);
  }

  addPerson(person: Person) {
    return this.http.post(baseUrl, person);
  }

  updatePerson(person: Person) {
    return this.http.put(baseUrl + person.username, person);
  }

  deletePerson(person: Person) {
    return this.http.delete(baseUrl + person.username);
  }
}
