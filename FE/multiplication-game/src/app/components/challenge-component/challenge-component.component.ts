import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
  haveChallenge: boolean;
  challenge: Challenge = new Challenge();
  loading = false;
  success = false;
  message: string;

  constructor(
    private challengeService: ChallengeServiceService,
    private formBuilder: FormBuilder
  ) {
    this.haveChallenge = false;
  }

  ngOnInit(): void {
    this.formBuild();
    this.getChallenge();
  }

  async submitChallenge() {
    this.loading = true;
    const challengeAttempt = this.createChallengeAttempt();
    this.sentChallenge(challengeAttempt);
    this.loading = false;
  }

  private sentChallenge(challengeAttempt: ChallengeAttemptDto) {
    this.challengeService
      .sendChallenge(challengeAttempt)
      .subscribe((result) => {
        if (result.correct) {
          this.updateMessage('Congratulations, your guess is correct!');
        } else {
          this.updateMessage(
            'Oops! Your guess ' +
              result.resultAttempt +
              ' is wrong, but keep playing!'
          );
        }
      });
  }

  private getChallenge() {
    this.challengeService.getChallenge().subscribe(
      (result) => {
        this.challenge = result;
        this.haveChallenge = true;
      },
      (error) => {
        console.error('Could not reach the server.');
      }
    );
  }

  private createChallengeAttempt(): ChallengeAttemptDto {
    const formValue = this.form.value;
    const challengeAttempt = new ChallengeAttemptDto();
    challengeAttempt.factorA = this.challenge.factorA;
    challengeAttempt.factorB = this.challenge.factorB;
    challengeAttempt.guess = formValue.guess;
    challengeAttempt.userAlias = formValue.alias;
    return challengeAttempt;
  }

  private formBuild() {
    this.form = this.formBuilder.group({
      alias: ['', [Validators.required, Validators.maxLength(12)]],
      guess: ['', [Validators.required, Validators.min(0)]],
    });
  }

  private updateMessage(message: string) {
    this.message = message;
  }

  get alias() {
    return this.form.get('alias');
  }

  get guess() {
    return this.form.get('guess');
  }
}
