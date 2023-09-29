import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { ChallengeServiceService } from 'src/app/core/http/challenge-service.service';
import { LeaderBoardService } from 'src/app/core/http/leader-board.service';
import { AttemptDtoChecked } from 'src/app/dtos/attempt-dto-checked';
import { LeaderBoardRow } from 'src/app/dtos/leader-board-row';
import { Uzer } from 'src/app/dtos/uzer';

@Component({
  selector: 'app-leader-board',
  templateUrl: './leader-board.component.html',
  styleUrls: ['./leader-board.component.css'],
})
export class LeaderBoardComponent implements OnInit {
  dataSource: MatTableDataSource<LeaderBoardRow>;
  columnsToDisplay = ['User', 'Score', 'Badges'];
  leaderBoardRows: LeaderBoardRow[];
  serverError: boolean;

  constructor(
    private leaderBoardService: LeaderBoardService,
    private challengeService: ChallengeServiceService
  ) {
    this.leaderBoardRows = [];
    this.serverError = false;
  }

  ngOnInit(): void {
    setInterval(() => {
      this.refreshLeaderBoard();
    }, 5000);
  }

  private refreshLeaderBoard() {
    this.getLeaderBoardData()
      .then((leaderBoardRows) => {
        let userIds = leaderBoardRows.map((row) => row.userId);
        this.getUserAliasDate(userIds)
          .then((uzers) => {
            let userMap = new Map();
            uzers.forEach((uzer) => {
              userMap.set(uzer.id, uzer.alias);
            });
            leaderBoardRows.forEach((row) => {
              row.userAlias = userMap.get(row.userId);
            });
            this.updateLeaderBoard(leaderBoardRows);
          })
          .catch((reason) => {
            console.log('Error mapping user ids', reason);
            this.updateLeaderBoard(leaderBoardRows);
          });
      })
      .catch((reason) => {
        this.serverError = true;
        console.log('Gamification server error', reason);
      });
  }

  private getLeaderBoardData(): Promise<LeaderBoardRow[]> {
    return new Promise((resolve, reject) => {
      this.leaderBoardService.getLeaderBoard().subscribe(
        (result) => {
          resolve(result);
        },
        (error) => {
          reject(error);
        }
      );
    });
  }

  private getUserAliasDate(userIds: number[]): Promise<Uzer[]> {
    return new Promise((resolve, reject) => {
      this.challengeService.getUsers(userIds).subscribe(
        (result) => {
          resolve(result);
        },
        (error) => {
          reject(error);
        }
      );
    });
  }

  private updateLeaderBoard(leaderBoard: LeaderBoardRow[]) {
    this.leaderBoardRows = leaderBoard;
    console.log('leaderboard ', this.leaderBoardRows);
    this.dataSource = new MatTableDataSource<LeaderBoardRow>(
      this.leaderBoardRows
    );
  }
}
