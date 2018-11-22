import { Component, OnInit, ViewChild } from '@angular/core';
import { PersonService } from './person.service';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatSnackBar } from '@angular/material';
import { PersonDialogComponent } from './person-dialog/person-dialog.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = '';
  persons: any;
  columnsToDisplay = ['firstName', 'lastName', 'age', 'details', 'delete'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private personService: PersonService, public dialog: MatDialog, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.personService.getPersons().subscribe((data: any) => {
      this.persons = new MatTableDataSource(data);
      this.persons.sort = this.sort;
      this.persons.paginator = this.paginator;
    });

  }

  applyFilter(filterValue: string) {
    this.persons.filter = filterValue.trim().toLowerCase();
  }

  addPerson() {
    const dialogRef = this.dialog.open(PersonDialogComponent, {
      width: '350px',
      data: {
        title: 'Add Person',
        person: ''
      }
    });

    dialogRef.disableClose = true;

    dialogRef.afterClosed().subscribe(person => {
      // updating table source binding
      if (person) {
        const data = this.persons.data;
        data.push(person);
        this.persons.data = data;
        this.snackBar.open('Person added!', '', {
          duration: 2000
        });
      }
    });
  }

  updatePerson(person) {
    const dialogRef = this.dialog.open(PersonDialogComponent, {
      width: '350px',
      data: {
      title: 'Update Person',
      person: person
      }
    });

    dialogRef.disableClose = true;

    dialogRef.afterClosed().subscribe(user => {
      // updating table source binding
      if (user) {
        for (const u of this.persons.data) {
          if (u.id === user.id) {
            const data = this.persons.data;
            const index = data.indexOf(u, 0);
            // remove the old and insert new one
            data.splice(index, 1, user);
            this.persons.data = data;
            break;
          }
        }
        this.snackBar.open('Person data updated!', '', {
          duration: 2000
        });
      }
    });
  }

  deletePerson(person) {
    if (confirm('Do you want to delete \'' + person.firstName + ' ' + person.lastName + '\' ?')) {
      this.personService.deletePerson(person).subscribe(
        () => {
          const index = this.persons.data.indexOf(person, 0);
          if (index > -1) {
            const data = this.persons.data;
            data.splice(index, 1);
            this.persons.data = data;
            this.snackBar.open('Person deleted!', '', {
              duration: 2000
            });
          }
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
