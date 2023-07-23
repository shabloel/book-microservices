import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ChallengeAttemptDto } from '../dtos/challenge-attempt-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ChallengeServiceService {
  private static SERVER_URL = `http://localhost:8090`;
  private static POST_CHALLENGE_URL = `/attempts`;
  private static GET_CHALLENGE_URL = `/challenges/random`;

  constructor(private httpClient: HttpClient) {}

  sendChallenge(challengeAttempt: ChallengeAttemptDto): Observable<any> {
    return this.httpClient.post(
      ChallengeServiceService.SERVER_URL +
        ChallengeServiceService.POST_CHALLENGE_URL,
      challengeAttempt
    );
  }

  getChallenge(): Observable<any> {
    return this.httpClient.get(
      ChallengeServiceService.SERVER_URL +
        ChallengeServiceService.GET_CHALLENGE_URL
    );
  }
}
