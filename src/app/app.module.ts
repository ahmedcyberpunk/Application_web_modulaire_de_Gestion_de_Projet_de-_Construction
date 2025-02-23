import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import{HttpClientModule} from  '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListterreComponent } from './listterre/listterre.component';
import { AjouterTerreComponent } from './ajouter-terre/ajouter-terre.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DetailsterreComponent } from './detailsterre/detailsterre.component';
import { ListPapierComponent } from './list-papier/list-papier.component';

@NgModule({
  declarations: [
    AppComponent,
    ListterreComponent,
    AjouterTerreComponent,
    DetailsterreComponent,
    ListPapierComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
