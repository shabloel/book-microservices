import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ChallengeAttempt } from 'src/app/dtos/challenge-attempt';
import { ChallengeServiceService } from 'src/app/core/http/challenge-service.service';
import { ObservablesService } from 'src/app/services/observables.service';

@Component({
  selector: 'app-user-stats',
  templateUrl: './user-stats.component.html',
  styleUrls: ['./user-stats.component.css'],
})
export class UserStatsComponent implements OnInit {
  pageSize = 5;
  pageSizeOptions: number[] = [5, 10, 25, 50];
  dataSource: MatTableDataSource<ChallengeAttempt>;
  columnsToDisplay = ['Challenge', 'Your guess', 'Result'];
  challengeAttempt: ChallengeAttempt;
  attempts: ChallengeAttempt[];

  @ViewChild('paginator') paginator!: MatPaginator;

  constructor(
    private challengeService: ChallengeServiceService,
    private observablesService: ObservablesService
  ) {}

  ngOnInit(): void {
    this.getAttempts();
  }

  private getAttempts() {
    this.observablesService.data$.subscribe((challengeAttempt) => {
      if (challengeAttempt.userAlias) {
        this.challengeService.getAttempts(challengeAttempt.userAlias).subscribe(
          (result) => {
            this.attempts = result;
            this.dataSource = new MatTableDataSource<ChallengeAttempt>(
              this.attempts
            );
            this.dataSource.paginator = this.paginator;
          },
          (error) => {
            console.error('Could not fetch attempts from the server.');
          }
        );
      } else {
        console.log('user is undefined');
      }
    });
  }
}
