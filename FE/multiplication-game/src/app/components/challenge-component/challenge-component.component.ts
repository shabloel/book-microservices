import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
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

  constructor(
    private challengeService: ChallengeServiceService,
    private formBuilder: FormBuilder
  ) {
    this.haveChallenge = false;
  }

  ngOnInit(): void {
    this.formBuild();
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

  async submitChallenge() {
    this.loading = true;
    const formValue = this.form.value;
    const challengeAttempt = new ChallengeAttemptDto();
    challengeAttempt.factorA = this.challenge.factorA;
    challengeAttempt.factorB = this.challenge.factorB;
    challengeAttempt.guess = formValue.answer;
    challengeAttempt.userAlias = formValue.name;

    this.challengeService
      .sendChallenge(challengeAttempt)
      .subscribe((result) => {});
  }

  private formBuild() {
    this.form = this.formBuilder.group({
      alias: ['', [Validators.required, Validators.maxLength(12)]],
      guess: ['', [Validators.required, Validators.min(0)]],
    });
  }

  get alias() {
    return this.form.get('alias');
  }

  get guess() {
    return this.form.get('guess');
  }
}
