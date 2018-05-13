import { Component, ViewChild, ElementRef} from '@angular/core';
import {NgForm} from '@angular/forms';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {itemPayload} from "../dataModel/itemPayload";
import {item} from "../dataModel/item";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})


export class AppComponent {

  private title: string = 'OSRSWebTicker';
  private apiRoot : string = "https://services.runescape.com"
  private alphaRoute : string = "/m=itemdb_oldschool/api/catalogue/items.json?category=1&alpha=";
  private pageRoute : string = "&page=1";
  private timeout : any = null;
  private itemData : item[] = [];
  private pattern : RegExp = RegExp('[a-zA-z0-9\(\)\+\ ]');

  @ViewChild('dataList')
  private dataList : ElementRef;

  onKey(event: any){
    clearTimeout(this.timeout);

    if(event.target.value.length === 0)
      this.itemData = [];
    else{

      this.timeout = setTimeout(()=> {
          console.log("Input Value:", event.target.value);

          let url : string = this.apiRoot + this.alphaRoute + this.encodeString(event.target.value) + this.pageRoute;
          console.log(url);
          let found : boolean = false;
          let targetUpper: string = event.target.value.toUpperCase();

          for(var i in this.itemData){
            let itemUpper : string = this.itemData[i].name.toUpperCase();
            if(itemUpper === targetUpper){
              console.log("Selected Item", this.itemData[i]);
              found = true;
              break;
            }
            else if(itemUpper.includes(targetUpper)){
              found = true;
              break;
            }
          }
          if(!found){
            this.http.get(url).subscribe((data : itemPayload) => {
              this.itemData = data.items;
            });
          }
      },600);
    }
  }

  validateInputChar(event: any){
    let inputChar = String.fromCharCode(event.charCode);

    if (!this.pattern.test(inputChar)) {
      event.preventDefault();
    }
  }

  displayItemDetails(itemDetails : item){
    console.log(itemDetails);
  };

  encodeString(input : string){
    return encodeURIComponent(input.toLowerCase());
  }


  constructor(private http: HttpClient) {
  }
}
