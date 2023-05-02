// randomDate, randomBoolean, randomInt, randomDouble
import { randomDate } from './random';

describe('Random', () => {
  describe('RandomDate', () => {
    it('start 와 end 사이의 랜덤 날짜를 리턴합니다.', () => {
      const start = new Date(1970, 0, 1);
      const end = new Date(1970, 0, 2);
      expect(randomDate(start, end).getTime()).toBeGreaterThanOrEqual(
        start.getTime(),
      );
      expect(randomDate(start, end).getTime()).toBeLessThan(end.getTime());
    });
  });
});
