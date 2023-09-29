import { Injectable } from '@angular/core';
import { AttemptDto } from '../dtos/attempt-dto';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ObservablesService {
  private dataSubject: Subject<AttemptDto> = new Subject<any>();
  public data$ = this.dataSubject.asObservable();

  sentChallengeAttemptToUserStats(data: AttemptDto) {
    this.dataSubject.next(data);
  }

  constructor() {}
}
