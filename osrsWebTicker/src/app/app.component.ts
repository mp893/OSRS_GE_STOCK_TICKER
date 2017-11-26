import { Component, ViewChild, ElementRef} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {itemPayload} from "../dataModel/itemPayload";

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
  private itemNames : string[] = [];

  @ViewChild('dataList')
  private dataList : ElementRef;

  onKey(event: any){

    let dataList = this.dataList.nativeElement;
    let inpLength : number = event.target.value.length;

      let match : boolean = false;
      for(var i in this.itemNames){
        if(this.itemNames[i].toUpperCase().includes(event.target.value.toUpperCase())){
          match = true;
          break;
        }
      }
      if(!match){

        while(dataList.firstChild){
          dataList.removeChild(dataList.firstChild);
        };
        this.itemNames = [];
        let url : string = this.apiRoot + this.alphaRoute + event.target.value + this.pageRoute;
        console.log(url);
        //console.log(this.searchElement);
        this.http.get(url).subscribe((data : itemPayload) => {
          for(var i in data.getItems()){
            var name = data.getItems()[i].getName();
            if(!this.itemNames.includes(name)){
              let element = document.createElement('OPTION') as HTMLOptionElement;
              this.itemNames.push(name);
              element.value = name;
              var value = document.createTextNode(name);
              element.appendChild(value);
              dataList.appendChild(element);
            }
          }
        });
      }
    }

  constructor(private http: HttpClient) {

  }
}
