import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CachingService {
  private cache: { [key: string]: any } = {};

  constructor() {}

  set(key: string, data: any): void {
    this.cache[key] = data;
  }

  get(key: string): any {
    return this.cache[key];
  }

  clear(): void {
    this.cache = {};
  }
}
