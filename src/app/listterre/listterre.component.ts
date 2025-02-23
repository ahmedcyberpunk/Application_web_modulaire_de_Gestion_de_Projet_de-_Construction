import { Component, OnInit } from '@angular/core';
import { TerreService } from '../TerreService.service';
import { Terre } from './Terre';
import * as bootstrap from 'bootstrap';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';


@Component({
  selector: 'app-listterre',
  templateUrl: './listterre.component.html',
  styleUrls: ['./listterre.component.css']
})
export class ListterreComponent implements OnInit {

  terres: Terre[]=[];
  searchText: string = '';
  Terrain = {  idTerrain: 0, nom: '', localisation: '', superficie: 0, statutJuridique: '' , imagePath: '', typeSol: ''};
  ordreTri: string = 'ascendant'; 
 

  constructor(private terreservice: TerreService) {}

  ngOnInit(): void {
    this.getTerreList();
  } 

  private getTerreList() {
    this.terreservice.getTerreList().subscribe(data => {
      this.terres = data;
    });
  }
  filterTerres() {
    return this.terres.filter((r) =>
      r.localisation.toLowerCase().includes(this.searchText.toLowerCase())
    );
  }

  deleteTerre(id: number) {
    this.terreservice.deleteTerre(id).subscribe(() => {
      this.terres = this.terres.filter(terre => terre.idTerrain !== id);
    });
  }

  // Open modal and populate data
  openModal(terrain: Terre) {
   
    this.Terrain = { ...terrain };  
    const modalElement = document.getElementById('updateModal');

    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
    } else {
      console.error('La modale avec l\'ID "updateModal" n\'a pas été trouvée.');
    }
  }
  updateData() {
  // Update data when form is submittedupdateData() {
  console.log('Données mises à jour:', this.Terrain);
  
  // Call the TerreService's update method to send the updated data to the backend
  this.terreservice.updateTerre(this.Terrain).subscribe(
    (response) => {
      console.log('Update successful:', response);
      
      // After the update, hide the modal
      const modalElement = document.getElementById('updateModal');
      if (modalElement) {
        const modal = new bootstrap.Modal(modalElement);
        modal.hide();  // Hide the modal after updating
      } else {
        console.error('Modal with ID "updateModal" not found');
      }
      
      // Optionally refresh the list of terrains after updating
      this.getTerreList();
      this.closeModal(); // This assumes you have a method to fetch the updated list of terrains
    },
    (error) => {
      console.error('Update failed:', error);
      alert('An error occurred while updating the terrain');
    }
  );
}

trierParSuperficie(): void {
  if (this.ordreTri === 'ascendant') {
    this.terres = [...this.terres].sort((a, b) => a.superficie - b.superficie);
  } else {
    this.terres = [...this.terres].sort((a, b) => b.superficie - a.superficie);
  }
}
closeModal() {
  const modalElement = document.getElementById('updateModal');
  if (modalElement) {
    const modalInstance = bootstrap.Modal.getInstance(modalElement); // Get the existing instance
    if (modalInstance) {
      modalInstance.hide(); // Hide the modal
    } else {
      console.warn('Modal instance not found. Creating a new one to close.');
      new bootstrap.Modal(modalElement).hide(); // Fallback: create and hide modal
    }
  } else {
    console.error('Modal element not found.');
  }
}





async generatePDF(): Promise<void> {
  const pdf = new jsPDF();
  let y = 20; // Starting vertical position

  pdf.setFontSize(18);
  pdf.text('Liste des Terrains', 80, 10);

  for (const terrain of this.terres) {
    pdf.setFontSize(12);

    // Adding text with updated y values for each piece of information
    pdf.text(`Nom: ${terrain.nom}`, 10, y);
    y += 10; // Move to next line

    pdf.text(`Localisation: ${terrain.localisation}`, 10, y);
    y += 10;

    pdf.text(`Superficie: ${terrain.superficie} m²`, 10, y);
    y += 10;

    pdf.text(`Statut Juridique: ${terrain.statutJuridique}`, 10, y);
    y += 10;

    pdf.text(`Type de Sol: ${terrain.typeSol}`, 10, y);
    y += 10;

    // Check if image exists and add it
    if (terrain.imagePath) {
      await this.addImageToPdf(terrain.imagePath, pdf, y);
      y += 60; // Add more space after the image
    } else {
      y += 20; // Move down if no image is available
    }

    // Add spacing between terrains
    y += 10;
  }

  // Save the PDF
  pdf.save('terrains.pdf');
}

// Helper function to add an image to the PDF and wait for it to load
addImageToPdf(imagePath: string, pdf: jsPDF, y: number): Promise<void> {
  return new Promise((resolve, reject) => {
    const img = new Image();
    img.src = imagePath;

    img.onload = () => {
      const imageWidth = 100;  // Increase the width of the image
      const imageHeight = 60;  // Increase the height of the image
      const imageX = 10; // Set the X position for the image

      // Add the image to the PDF
      pdf.addImage(img, 'JPEG', imageX, y, imageWidth, imageHeight);
      resolve(); // Resolve when image is added
    };

    img.onerror = (error) => {
      console.error('Image loading error', error);
      reject(error); // Reject if image fails to load
    };
  });
}
}