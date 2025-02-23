import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { TerreService } from '../TerreService.service';
import { papier } from './papier';
@Component({
  selector: 'app-list-papier',
  templateUrl: './list-papier.component.html',
  styleUrls: ['./list-papier.component.css']
})
export class ListPapierComponent implements OnInit {

PapierForm!: FormGroup;
addedCard: any = null;
cards: any[] = [];
id!: number;


constructor(private terreservice:TerreService,private router:Router,private http: HttpClient,private ac: ActivatedRoute) {
  
 

 }

 ngOnInit(): void {
  this.PapierForm = new FormGroup({
 
    statut: new FormControl('', [
      Validators.required, 
      Validators.minLength(3),
      Validators.pattern('^[A-Za-zÀ-ÿ]+$')
    ]),
    type_Autorisation: new FormControl('', [
      Validators.required, 
      Validators.minLength(3),
      Validators.pattern('^[A-Za-zÀ-ÿ]+$') // Validate letters and accented characters
    ]),
    description: new FormControl('', [
      Validators.required, 
      Validators.minLength(3),
      Validators.pattern('^[A-Za-zÀ-ÿ]+$')
    ])
  });

this.id=this.ac.snapshot.params['id'];
    this.getPapierListByTerrainId(this.id);







}
getPapierListByTerrainId(terrainId: number): void {
  this.terreservice.getPapierListByTerrainId(terrainId).subscribe(
    (data) => {
      this.cards = data; // Update the cards array with the fetched data
      console.log('Fetched papers: ', data);
    },
    (error) => {
      console.error('Error fetching papers: ', error);
    }
  );
}


onFormSubmit(): void {
  if (this.PapierForm.valid) {
    // Create the Papier object with the form data and the terrainId
    const papierData = {
      ...this.PapierForm.value,
      // You can pass only the terrainId in the body if needed, or send the full Terrain object
      // Here, we are passing the terrainId
      terrain: { id: this.id }
    };

    // Call the createPapier method, passing the terrainId and the Papier data
    this.terreservice.createPapier(papierData, this.id).subscribe(data => {
      console.log('Created Papier:', data);
      this.getPapierListByTerrainId(this.id);

    }, error => {
      console.error('Error creating Papier:', error);
    });

    // Reset the form after submission
    this.PapierForm.reset();
  }
}

// Handle card deletion
deleteCard(): void {
  this.addedCard = null;
}
}






