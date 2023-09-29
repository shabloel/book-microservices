import { DataSource } from '@angular/cdk/collections';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable, Subject, concatMap, tap } from 'rxjs';
import { Challenge } from 'src/app/dtos/challenge';
import { ChallengeAttempt } from 'src/app/dtos/challenge-attempt';
import { AttemptDto } from 'src/app/dtos/attempt-dto';
import { ChallengeServiceService } from 'src/app/core/http/challenge-service.service';
import { ObservablesService } from 'src/app/services/observables.service';

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
  challengeAttempt: ChallengeAttempt;

  constructor(
    private challengeService: ChallengeServiceService,
    private formBuilder: FormBuilder,
    private observableService: ObservablesService
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

  private sentChallenge(challengeAttempt: AttemptDto) {
    this.challengeService
      .sendChallenge(challengeAttempt)
      .pipe(
        tap((result) => {
          this.challengeAttempt = result;
          if (result.correct) {
            this.updateMessage('Congratulations, your guess is correct!');
          } else {
            this.updateMessage('Oops! Your guess is wrong, but keep playing!');
          }
        })
      )
      .subscribe(() => {
        this.observableService.sentChallengeAttemptToUserStats(
          challengeAttempt
        );
        this.getChallenge();
        this.clearInputFieldGuess();
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
        this.haveChallenge = false;
      }
    );
  }

  private createChallengeAttempt(): AttemptDto {
    const formValue = this.form.value;
    const challengeAttempt = new AttemptDto();
    challengeAttempt.factorA = this.challenge.factorA;
    challengeAttempt.factorB = this.challenge.factorB;
    challengeAttempt.guess = formValue.guess;
    challengeAttempt.userAlias = formValue.alias;
    return challengeAttempt;
  }

  private formBuild() {
    this.form = this.formBuilder.group({
      alias: ['', [Validators.required, Validators.maxLength(12)]],
      guess: [
        '',
        [
          Validators.required,
          Validators.min(0),
          Validators.pattern('^[0-9]*$'),
        ],
      ],
    });
  }

  private clearInputFieldGuess(): void {
    this.form.get('guess')?.setValue('');
    this.form.get('guess')?.markAsPristine();
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
