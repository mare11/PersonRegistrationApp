import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const baseUrl = '/persons';

@Injectable({
  providedIn: 'root'
})
export class PersonService {


  constructor(private http: HttpClient) { }

  getPersons() {
    return this.http.get(baseUrl);
  }

  addPerson(person) {
    return this.http.post(baseUrl, person);
  }

  updatePerson(person) {
    return this.http.put(baseUrl, person);
  }

  deletePerson(person) {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      body: person
    };
    return this.http.delete(baseUrl, httpOptions);
  }
}
