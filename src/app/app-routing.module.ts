import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AjouterTerreComponent } from './ajouter-terre/ajouter-terre.component';
import { ListterreComponent } from './listterre/listterre.component';
import { DetailsterreComponent } from './detailsterre/detailsterre.component';
import { ListPapierComponent } from './list-papier/list-papier.component';
const routes: Routes = [
{path:'listterre',component:ListterreComponent},
{path:'ajouter-terre',component:AjouterTerreComponent},
{path:'detailsterre/:id',component:DetailsterreComponent},
{path:'list-papier/:id',component:ListPapierComponent},
{path:'',redirectTo:'listterre',pathMatch:'full'} 


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
