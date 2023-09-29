import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AttemptDto } from 'src/app/dtos/attempt-dto';

@Injectable({
  providedIn: 'root',
})
export class ChallengeServiceService {
  SERVER_URL = `http://localhost:8090`;
  POST_CHALLENGE_URL = `/attempts`;
  GET_CHALLENGE_URL = `/challenges/random`;
  GET_ATTEMPTS_BY_ALIAS = `/attempts?alias=`;
  GET_USERS_BY_IDS = `/users`;

  constructor(private httpClient: HttpClient) {}

  sendChallenge(attemptDto: AttemptDto): Observable<any> {
    return this.httpClient.post(
      this.SERVER_URL + this.POST_CHALLENGE_URL,
      attemptDto
    );
  }

  getChallenge(): Observable<any> {
    return this.httpClient.get(this.SERVER_URL + this.GET_CHALLENGE_URL);
  }

  getAttempts(alias: string): Observable<any> {
    return this.httpClient.get(
      this.SERVER_URL + this.GET_ATTEMPTS_BY_ALIAS + alias
    );
  }

  getUsers(userIds: number[]): Observable<any> {
    return this.httpClient.get(
      this.SERVER_URL + this.GET_USERS_BY_IDS + `/` + userIds.join(',')
    );
  }
}
