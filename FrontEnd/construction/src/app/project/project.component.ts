import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit{

  projects: any[] = []; 
  
    constructor(private apiService: ApiService) {}
  
   
    ngOnInit(): void {
      this.getProjects();
    } 
  
    private getProjects() {
      this.apiService.getProjects().subscribe(data => {
        this.projects = data;
      });
    }

}
