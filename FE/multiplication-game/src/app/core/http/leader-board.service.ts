import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LeaderBoardService {
  SERVER_URL: string = `http://localhost:8081`;
  GET_LEADERBOARD: string = `/leaders`;

  constructor(private httpClient: HttpClient) {}

  getLeaderBoard(): Observable<any> {
    return this.httpClient.get(this.SERVER_URL + this.GET_LEADERBOARD);
  }
}
