import { Component, ViewChild} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CreateNewAutocompleteGroup, SelectedAutocompleteItem, NgAutocompleteComponent} from "ng-auto-complete";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})


export class AppComponent {
  title: string = 'OSRSWebTicker';
  apiRoot : string = "https://services.runescape.com"
  alphaRoute : string = "/m=itemdb_oldschool/api/catalogue/items.json?category=1&alpha=";
  pageRoute : string = "&page=1"

  @ViewChild(NgAutocompleteComponent) public completer: NgAutocompleteComponent;

  private groupItems = [];
  public group = [
    CreateNewAutocompleteGroup(
        'Item Name',
        'completer',
        [
          {'title' : 'item',  id : 1}
        ],
        {titleKey: 'title', childrenKey: null}
    ),
  ];

  onKey(event: any){
    if(event.target.value.length > 2){
        var url = this.apiRoot + this.alphaRoute + event.target.value + this.pageRoute;
        console.log(url);
        console.log(this.http);
        this.http.get(url).subscribe(data => {
          var itemArray = [];
          for(var i in data.items){
            itemArray.push({title : data.items[i].name, id : i});
          }
          this.group = [CreateNewAutocompleteGroup(
                              'Item Name',
                              'completer',
                              itemArray,
                              {titleKey: 'title', childrenKey: null}
                          )];
          console.log(this.groupItems);
        })
    };
  }

  constructor(private http: HttpClient) {

  }
}
