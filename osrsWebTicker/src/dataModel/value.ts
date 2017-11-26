

enum trend {
  negative,
  neutral,
  positive,
}


export class value {
  private trend : trend;
  private price : number;
  private change : number;
}
