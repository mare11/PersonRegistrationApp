import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';
import { FormGroup,  FormBuilder,  Validators } from '@angular/forms';
import { PersonService } from '../person.service';

interface Person {
  id: number;
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
      this.person.firstName = '';
      this.person.lastName = '';
      this.person.age = null;
    }


   }

   ngOnInit() {
    this.angForm = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.pattern('[a-zA-Z ]+')]],
      lastName: ['', [Validators.required, Validators.pattern('[a-zA-Z ]+')]],
      age: ['', [Validators.required, Validators.pattern('[0-9]+')]],
  });

    this.angForm.get('firstName').setValue(this.person.firstName);
    this.angForm.get('lastName').setValue(this.person.lastName);
    this.angForm.get('age').setValue(this.person.age);
   }

  savePerson() {
    // check form validation
    if (this.angForm.invalid) {
      return;
    }

    // check if person has id (if it exists)
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

  // alternative for NgModel because formControlName attribute
  onKeyUp() {
    this.person.firstName = this.angForm.get('firstName').value;
    this.person.lastName = this.angForm.get('lastName').value;
    this.person.age = this.angForm.get('age').value;
  }

}
