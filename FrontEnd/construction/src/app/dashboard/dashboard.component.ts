import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  projects: any = [];

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
