import { Injectable } from '@angular/core';
import{HttpClient, HttpClientModule} from  '@angular/common/http';
import { Observable } from 'rxjs';
import { Terre } from '../app/listterre/Terre';
import { papier } from './list-papier/papier';


@Injectable({
  providedIn: 'root'
})
export class TerreService {
private baseURL="http://localhost:8085/api4/terrain";
  constructor(private httpClient:HttpClient) { }

getTerreList():Observable<Terre[]>{
return this.httpClient.get<Terre[]>(this.baseURL+'/allterrain');
}
getTerreById(id: number): Observable<Terre> {
  return this.httpClient.get<Terre>(`${this.baseURL}/seuleterrain/${id}`);
}


deleteTerre(id: number): Observable<void> {
 
  return this.httpClient.delete<void>(this.baseURL + '/deleteterrain' + '/' + id);
}
createTerre(terre: Terre): Observable<Object> {
  return this.httpClient.post<Object>(this.baseURL + '/addterrain', terre);

}



createPapier(papier:papier, terrainId: number): Observable<Object> {
  // Send the terrainId in the URL and the Papier_autorisation in the request body
  return this.httpClient.post<Object>(`${this.baseURL}/addPapier/${terrainId}`, papier);
}

getPapierListByTerrainId(terrainId: number): Observable<papier[]> {
  return this.httpClient.get<papier[]>(`${this.baseURL}/papiers/${terrainId}`);
}




updateTerre(terre: Terre): Observable<Object> {
  return this.httpClient.put<Object>(this.baseURL + '/updateterrain', terre);
}

}
