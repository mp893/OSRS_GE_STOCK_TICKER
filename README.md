# OSRS_GE_STOCK_TICKER

 - Currently the only functioning version is a Swing java/POJ version 
    - Requires the user to know the item ID in order to search
    - Has a simple item library building capacity, but this can be quite time consuming even though it is multithreaded
    - Almost entirely a manual system, but the ticking capability is there
    - I do not plan on updating/fixing/enhancing this version 
       
 - In progress:
 
     - ~~Vertx java backend~~
    - ~~Fetching to become on demand~~
    - ~~Perhaps internal rest calls?~~
     - Angular 5 web client
    
    ## Changelog:
      - 1.0.3
        - Created skeleton for Angular 5 version
        - Set up and wired up basic rest service of web client version
        - Support for Desktop version will be terminated
          - *acess to a new more efficent api allows for a simpler client*
          - *no need to write a server for simple data manipulation*
          
      - 1.0
        - Mavenized
        - Converted first set of handlers and request client files from Netty to Vertx
          - *this uses Netty but does a lot of the setup*
