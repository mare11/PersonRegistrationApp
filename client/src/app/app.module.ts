import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { PersonDialogComponent } from './person-dialog/person-dialog.component';
import { MaterialModule } from './material/material.module';
import { PersonService } from './person.service';
import { FormsModule } from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';



@NgModule({
  declarations: [
    AppComponent,
    PersonDialogComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MaterialModule,
    AppRoutingModule
  ],
  entryComponents: [PersonDialogComponent],
  providers: [PersonService],
  bootstrap: [AppComponent]
})
export class AppModule { }
