import { Component, OnInit, ViewChild } from '@angular/core';
import { PersonService } from './person.service';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatSnackBar } from '@angular/material';
import { PersonDialogComponent, Person } from './person-dialog/person-dialog.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = '';
  allPersons: Person[];
  persons: any;
  columnsToDisplay = ['username', 'firstName', 'lastName', 'age', 'details', 'delete'];

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(private personService: PersonService, public dialog: MatDialog, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.personService.getPersons().subscribe(
      (data: Person[]) => {
        this.allPersons = data;
        this.persons = new MatTableDataSource(data);
        this.persons.sort = this.sort;
        this.persons.paginator = this.paginator;
      },
      (erorr) => {
        console.log(erorr);
      });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    if (filterValue && filterValue.length > 0) {
      this.personService.searchPersons(filterValue).subscribe(
        (data: Person[]) => {
          this.persons = new MatTableDataSource(data);
        },
        (erorr) => {
          console.log(erorr);
        });
    } else {
      this.persons = new MatTableDataSource(this.allPersons);
    }
    // this.persons.filter = filterValue.trim().toLowerCase();
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

    // dialogRef.disableClose = true;

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
    if (confirm('Do you want to delete \'' + person.username + '\' ?')) {
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
