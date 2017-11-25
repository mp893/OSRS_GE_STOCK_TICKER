import { Component, ViewChild, ElementRef} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

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
  private itemNames : array = [];

  @ViewChild('dataList')
  private dataList : ElementRef;

  onKey(event: any){

    var dataList = this.dataList.nativeElement;
    var inpLength : integer = event.target.value.length;

      var match = false;
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
        var url = this.apiRoot + this.alphaRoute + event.target.value + this.pageRoute;
        console.log(url);
        //console.log(this.searchElement);
        this.http.get(url).subscribe(data => {
          for(var i in data.items){
            var name = data.items[i].name;
            if(!this.itemNames.includes(name)){
              var element = document.createElement('OPTION');
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
