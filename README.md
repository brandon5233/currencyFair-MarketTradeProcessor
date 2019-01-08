# currencyFair-MarketTradeProcessor

## About
The system consumes JSON messages, process them, and finally displays an output at the frontend. 

## System Architecture

![alt text](https://github.com/brandon5233/currencyFair-MarketTradeProcessor/blob/master/System%20Diagram.jpg "System Architecture Diagram")

  <hr>
  1. Message Consumer  
  The consumer is available at **0.0.0.0/api_test**. It accepts a JSONArray as input. Each JSONObject is of the format:     
  
  ```  
  {"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP",  
  "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471,  
  "timePlaced" : "24-JAN-18 10:27:44", "originatingCountry" : "FR"}
  ```     
   Incoming JSONArrays are converted into an array of java objects using Gson. Currently, JSON data must be supplied as a JSON array even if it contains a single JSON object (as seen above). This is done for simplicity only. Adapting to handle both objects and arrays is pretty straightforward and can be implemented easily.    
 
   When the consumer recieves a JSONArray, it checks if the entire array can fit into the buffer. This is done as a rate limiting measure for the consumer, and to ensure that when the server accepts data, it is capable of processing it. The size of the buffer is a design consideration, and depends on the expected number of incoming requests. Currently it is set to 150, as I sent batches of 100 transactions continuously for testing. Each transaction is loaded onto a buffer waiting for the processpr to pick them up.  

2. Message Processor  
The processor thread picks up each transaction from the buffer and checks the originating country.
The number of transactions originating from that particular country is expressed as a percentage of total number of transactions received by the system so far. This percentage is also converted into a color equivalent ranging from red (0%) to green(100%).The list of countries and their corresponding percents and color frequencies are converted to json and send to the frontend via websocket connection to be rendered.  

3. Message Frontend  
The frontend comprises of a React.js app that colors countries on a global map in real time. Countries that have a high percent of transactions originating from them are more green, while countries having lesser transactions originating from them are more red. The percentage of transactions originating from each country is also displayed in the bottom-left hand corner. Since communication happens over a websocket, data is rendered on the map in real time.  The system also suports multiple clients connecting to the server at any time. 

## Testing

1. Basic Junit Tests - basic test to check http connection to the server, check if undefined paths are handled, sending valid and invalid JSON data to the server and check if correct error responses are received. 

2. Stress tesing (backend) - The buffer size was decreased from 500 to 250. Multiple post requests (each of size ~100) were sent to the server until the buffer was full. The rejected request was retried continuously. The server did not crash. Once the buffer could accomodate the request, the failed request went through. The processing log was analysed to ensure that the transactions were executed in order. 

3. Stress testing (frontend) - A large batch of messages were sent to the frontend. This test lasted ~15 seconds. When that worked smoothly, the test was repeated on a loop to last a couple minutes. The front end seemed to be handling requests very well.  
