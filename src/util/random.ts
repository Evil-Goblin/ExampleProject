import * as crypto from 'crypto';
const randomDate = (start: Date, end: Date): Date =>
  new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));

const randomBoolean = (): boolean => Math.random() < 0.5;

const randomInt = (n: number): number => Math.floor(Math.random() * (n + 1));

const randomDouble = (n: number): number => Math.random() * n;

const randomString = (): string => crypto.randomUUID();

export { randomDate, randomBoolean, randomInt, randomDouble, randomString };
