import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { MatIconModule, MatToolbarModule, MatMenuModule, MatDialogModule,
  MatFormFieldModule, MatInputModule, MatButtonModule, MatCheckboxModule,
  MatPaginatorModule, MatSortModule, MatTableModule, MatTooltipModule,
  MatSnackBarModule } from '@angular/material';

@NgModule({
  declarations: [],
  imports: [MatButtonModule, MatCheckboxModule, MatIconModule, MatToolbarModule, MatMenuModule,
    MatDialogModule, MatFormFieldModule, MatInputModule, MatPaginatorModule, MatSortModule,
    MatTableModule, MatTooltipModule, ReactiveFormsModule, MatSnackBarModule],
  exports: [MatButtonModule, MatCheckboxModule, MatIconModule, MatToolbarModule, MatMenuModule,
    MatDialogModule, MatFormFieldModule, MatInputModule, MatPaginatorModule, MatSortModule,
    MatTableModule, MatTooltipModule, ReactiveFormsModule, MatSnackBarModule],
})
export class MaterialModule { }
