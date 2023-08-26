# Overview
Here I am representing a new project on creating a small clone of spring boot.

# Features : 
1. No need to create seperate servlets
2. No need to specify paths for each method in web.xml
3. No need to create a Js file for writing basic functions of ajax call
4. No need to write any servlet
5. Can access all parameters and attributes easily by using annotations

#Steps Of Usage : 
1. The User Has to write a java class file and write annotations on everu class and method of '@Paht(Path="mention the path by which method or class will be called")'
2. He should give a path in web.xml for com.thinking.machine.webRock.tmWebRock for path (@Path(Path="path given to the class"))
as the parameter-value in web.xml
3.If Parameter of a method comes from REQUEST SCOPE then use annotation @RequestParameter(name="Name Of The Parameter") before that parameter
4.If method uses application/Session/Request Scope to add their attributes then create a field of (Session/Request/Application)Scope and add an Annotation @AutoWire() to it
to get attributes we need to mention name in AutoWire annotation and create a field of the type of attribute(@AutoWire(name="Name of thed attribute"))
5.For mentioning methodType of the methods by which it is called we can use @Post for POST TYPE and @Get for GET TYPE Request
(DEFAULT CASE USED WILL BE GET)
6.To forward request to any other method or url mention annotation @ForwardTo(forward="url where to be forwarded")
7. In Html file include scripts by the below line of code
<script src = '/tmWebRock/getJsContent?name=mention name of method which is needed to be called'></script>
8. Call the method as you want and pass the method the data (if required) and a function which tells after successfull response which action should be document


# EXAMPLE : 
For instance I have Provided index.html for HTML code
And in WEB-INF/classes/Student/Service folder I have provided Student.java file which will help you to know each and every feature of the framework
