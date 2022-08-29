# DR-Project
It's the repository where the project files are stored. There are code from FrontEnd framework(Angular) and BackEnd(Spring Boot). Also there is a ER Diagram which describes the tables where the information are stored and shows the relationships between objects.
The server and the client communicate by using a rest api. The client send the request to the specified end point, the server processes the submitted information and return a http response with object or collection of objects to the client.
All server resources are forbidden to the unauthorized users. When the user logs in, the server generates and return "bearer" token to the client which is stored in the local storage. This token is active for 30 minutes.
