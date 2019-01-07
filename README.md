# currencyFair-MarketTradeProcessor

## About
The system consumes JSON messages, process them, and finally displays an output at the frontend. 

## System Architecture

![alt text](https://github.com/brandon5233/currencyFair-MarketTradeProcessor/blob/master/System%20Diagram.jpg "System Architecture Diagram")

1. Message Consumer  
  The consumer is available at **0.0.0.0/api_test**. It accepts a JSONArray as input. Each JSONObject is of the format:     
  
  ```  
  {"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP",  
  "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471,  
  "timePlaced" : "24-JAN-18 10:27:44", "originatingCountry" : "FR"}
  ```   
  
  <hr>
  Incoming JSONArrays are converted into an array of java objects using Gson.  
  Each transaction is loaded onto a buffer.  
  
  2. Message Processor  
    The processor thread picks up each transaction from the buffer and checks the originating country.
    The number of transactions originating from that particular country is expressed as a percentage of total number of transactions received by the system so far. This percentage is also converted into a color equivalent ranging from red (0%) to green(100%).The list of countries and their corresponding percents and color frequencies are converted to json and send to the frontend via websocket connection to be rendered.

3. Message Frontend  
     The frontend comprises of a React.js app that colors countries on a global map in real time. Countries that have a high percent of transactions originating from them are more green, while countries having lesser transactions originating from them are more red. The percentage of transactions originating from each country is also displayed in the bottom-left hand corner. Since communication happens over a websocket, data is rendered on the map in real time.  The system also suports multiple clients connecting to the server at any time. 
