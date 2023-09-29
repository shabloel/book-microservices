import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ChallengeAttempt } from 'src/app/dtos/challenge-attempt';

@Component({
  selector: 'app-leader-board',
  templateUrl: './leader-board.component.html',
  styleUrls: ['./leader-board.component.css'],
})
export class LeaderBoardComponent {
  pageSize = 5;
  pageSizeOptions: number[] = [5, 10, 25, 50];
  dataSource: MatTableDataSource<ChallengeAttempt>;
  columnsToDisplay = ['Challenge', 'Your guess', 'Result'];
}
