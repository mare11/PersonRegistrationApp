import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { PersonService } from '../person.service';

export interface Person {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  age: number;
}

@Component({
  selector: 'app-person-dialog',
  templateUrl: './person-dialog.component.html',
  styleUrls: ['./person-dialog.component.css']
})

export class PersonDialogComponent implements OnInit {

  title = '';
  person: Person = <any>{};
  angForm: FormGroup;

  constructor(private personService: PersonService, private dialogRef: MatDialogRef<PersonDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any, private formBuilder: FormBuilder, private snackBar: MatSnackBar) {

    this.title = data.title;

    if (data.person) {
      // copy the form object for updating
      this.person = JSON.parse(JSON.stringify(data.person));
    } else {
      // adding new person
      this.person.id = null;
      this.person.username = '';
      this.person.firstName = '';
      this.person.lastName = '';
      this.person.age = null;
    }


  }

  ngOnInit() {
    this.angForm = this.formBuilder.group({
      username: [this.person.username, Validators.required],
      firstName: [this.person.firstName, [Validators.required, Validators.pattern('[a-zA-Z ]+')]],
      lastName: [this.person.lastName, [Validators.required, Validators.pattern('[a-zA-Z ]+')]],
      age: [this.person.age, [Validators.required, Validators.pattern('[0-9]+')]],
    });
  }

  savePerson() {
    // check form validation
    if (this.angForm.invalid) {
      return;
    }

    const values = this.angForm.value;
    values.id = this.person.id;
    this.person = values;

    // check if person has id(if it exists)
    if (this.person.id) {
      this.personService.updatePerson(this.person).subscribe(
        (data) => {
          // send data back to main component and close the dialog
          this.dialogRef.close(data);
        },
        error => {
          this.snackBar.open('Server error', '', {
            duration: 2000
          });
        }
      );

    } else {
      this.personService.addPerson(this.person).subscribe(
        (data) => {
          // send data back to main component and close the dialog
          this.dialogRef.close(data);
        },
        error => {
          this.snackBar.open('Server error', '', {
            duration: 2000
          });
        }
      );
    }

  }

}
