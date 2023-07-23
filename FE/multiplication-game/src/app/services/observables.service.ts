import { Injectable } from '@angular/core';
import { ChallengeAttempt } from '../dtos/challenge-attempt';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ObservablesService {
  private dataSubject: Subject<ChallengeAttempt> = new Subject<any>();
  public data$ = this.dataSubject.asObservable();

  sentChallengeAttemptToUserStats(data: ChallengeAttempt) {
    this.dataSubject.next(data);
  }

  constructor() {}
}
