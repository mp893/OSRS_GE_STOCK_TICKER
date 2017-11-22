# OSRS_GE_STOCK_TICKER

 - Currently the only functioning version is a Swing java/POJO java version 
    - Requires the user to know the item ID in order to search
    - Has a simple item library building capacity, but this can be quite time consuming even though it is multithreaded
    - Almost entirely a manual system, but the ticking capability is there
    - I do not plan on updating/fixing/enhancing this version 
    
 - In progress Vertx java backend
    - Fetching to become on demand
    - Perhaps internal rest calls?
    
    ## Changelog:
      - 1.0
        - Mavenized
        - Converted first set of handlers and request client files from Netty to Vertx
          - *this uses Netty but does a lot of the setup*
 - I also plan on making some sort of web based front end in the future
