Pincode Distance 


In this project, I integrate Google Maps API to calculate the distance between two locations using pincode information.
The primary functionality is provided by the getRoute REST API, which fetches the route details between an origin and destination using the Google Maps Directions API.
The getRoute method fetches the route details between the origin and destination addresses by calling the Google Maps Directions API.
The API returns a JSON response containing various route options, and the application extracts the first available route.
We wrote unit tests to validate the API’s functionality. For example, the testGetRoute_CacheMiss test ensures that the application handles,
scenarios where no routes are returned by the Google Maps API correctly by throwing a RouteNotFoundException.
Error handling is implemented to gracefully manage cases where no route data is returned or the API encounters an issue.

First Call 
From Pincode - 141106 
To Pincode: 110060 

Output Tested by Postman


"distance": {

  "text": "353 km",
  
  "value": 353376
  
  },
  
"duration": {

  "text": "6 hours 7 mins",
  
  "value": 22001
  
  },
  
"end_address": "New Delhi, Delhi 110060, India",

  "end_location": {
  
      "lat": 28.6354283,
      
      "lng": 77.18567759999999
      
       },
       
"start_address": "Halwara, Punjab 141106, India",

   "start_location": {
   
      "lat": 30.7488957,
      
      "lng": 75.63834
      
}
