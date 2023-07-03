import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Challenge } from 'src/app/dtos/challenge';
import { ChallengeAttemptDto } from 'src/app/dtos/challenge-attempt-dto';
import { ChallengeServiceService } from 'src/app/services/challenge-service.service';

@Component({
  selector: 'app-challenge-component',
  templateUrl: './challenge-component.component.html',
  styleUrls: ['./challenge-component.component.css'],
})
export class ChallengeComponentComponent implements OnInit {

  form: FormGroup;
  
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

    this.formBuild();
  }

  public submitChallenge(event: any) {
    const challengeAttempt = new ChallengeAttemptDto();
    this.challengeService.sendChallenge(challengeAttempt);
  }

  private formBuild() {
    this.form = new FormGroup({
      user: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(12)])),
      answer: new FormControl('', Validators.compose([Validators.required, value ]))
    });
  }
}
