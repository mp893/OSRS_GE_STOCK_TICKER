import {item} from "./item"

export class itemPayload {
  private items : item[];

  getItems(){
    return this.items;
  }
}
