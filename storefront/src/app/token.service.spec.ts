import {
  beforeEachProviders,
  it,
  describe,
  expect,
  inject
} from '@angular/core/testing';
import { TokenService } from './token.service';

describe('Token Service', () => {
  beforeEachProviders(() => [TokenService]);

  it('should ...',
      inject([TokenService], (service: TokenService) => {
    expect(service).toBeTruthy();
  }));
});
