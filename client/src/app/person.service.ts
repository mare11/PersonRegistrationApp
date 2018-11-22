import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor(private http: HttpClient) { }

  getPersons() {
    return this.http.get('/getPersons');
  }

  addPerson(person) {
    return this.http.post('/addPerson', person);
  }

  updatePerson(person) {
    return this.http.put('/updatePerson', person);
  }

  deletePerson(person) {
    return this.http.delete('/deletePerson/' + person.id);
  }
}
