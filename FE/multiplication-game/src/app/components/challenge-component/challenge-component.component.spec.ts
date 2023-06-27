import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChallengeComponentComponent } from './challenge-component.component';

describe('ChallengeComponentComponent', () => {
  let component: ChallengeComponentComponent;
  let fixture: ComponentFixture<ChallengeComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChallengeComponentComponent]
    });
    fixture = TestBed.createComponent(ChallengeComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
