import { Component, OnInit } from '@angular/core';
import { Challenge } from 'src/app/dtos/challenge';
import { ChallengeAttemptDto } from 'src/app/dtos/challenge-attempt-dto';
import { ChallengeServiceService } from 'src/app/services/challenge-service.service';

@Component({
  selector: 'app-challenge-component',
  templateUrl: './challenge-component.component.html',
  styleUrls: ['./challenge-component.component.css'],
})
export class ChallengeComponentComponent implements OnInit {
  challenge: Challenge = new Challenge();

  constructor(private challengeService: ChallengeServiceService) {}

  ngOnInit(): void {
    this.challengeService.getChallenge().subscribe(
      (result) => {
        this.challenge = result;
      },
      (error) => {
        console.error('Could not reach the server.');
      }
    );
  }

  public submitChallenge(event: any) {
    const challengeAttempt = new ChallengeAttemptDto();
    this.challengeService.sendChallenge(challengeAttempt);
  }
}
