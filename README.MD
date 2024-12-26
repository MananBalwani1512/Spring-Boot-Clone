# Spring Boot Clone
The Project Spring-Boot-Clone is created with intention of helping backend developers with writing the service classes easily without writing any code for servlets.
___
## Requirements
1. [Apache Tomcat9](https://apacheLink) should be installed properly on the system.
2. [Java](https://jdk) should be installed on the system.
___
## Steps for Setup
1. Clone [Spring Boot Clone](https://github.com/MananBalwani1512/Spring-Boot-Clone) repository int the webapps of the Apache Tomcat9 Folder .
2. Then change name from Spring-Boot-Clone to the project name required.
* * *
### Configuring web.xml File
1. Then go to WEB-INF Folder in the project and change some entries
`<url-pattern>/bobby/*</url-pattern>` to  `<url-pattern>/YOUR_API_URL/*</url-pattern>`
This will create the URL Pattern as 
`http://localhost:8080/YOUR_PROJECT_NAME/YOUR_API_URL/SERVICE_URL`
2. Also change parameter value for SERVICE_PACKAGE_PREFIX
`<param-value>bobby</param-value>` to `<param-value>YOUR_PROJECT_PACKAGE</param-value>`
3. Also change parameyer value for BASE_URL_PREFIX
`<param-value>bobby</param-value>` to `<param-value>YOUR_API_URL</param-value>`

*Note : Don't add /* at the end just like in <url-pattern> tag.*
* * *
### Writing Code for service Methods
1. Create a folder structure that represent the package of the project.
2. The create some Java files to write API's for the project.
3. Add Annotations to the API's according the usage.
* * *
#### Annotations Available
- *Get :* This annotation represent that the request for this API or all API's of this class will be of GET Type.
- *Post :* This annotation represent that the request for this API or all API's of this class will be of Post Type.
- *InjectRequestScope :* This annotation is applied on the class or method of parameter if in field of the class or method of parameter we need an object of RequestScope to  get or set an attribute in request scope
- *InjectSessionScope :* This annotation is applied on the class or method of parameter if in field of the class or method of parameter we need an object of SessionScope to  get or set an attribute in session scope
- *InjectApplicationScope :* This annotation is applied on the class or parameter of the method if in field of the class or method of parameter we need an object of ApplicationScope to  get or set an attribute in Application scope
- *InjectApplicationDirectory :* This annotation is applied on the class or method of parameter if in field of the class or method of parameter we need an object of ApplicationDirectory to  get a file object of working directory of the project.
- *InjectApplicationDirectory :* This annotation is applied on the class or method of parameter if in field of the class or method of parameter we need an object of ApplicationDirectory to  get a file object of working directory of the project.
- *OnStartup :* This annotation is applied on the method to make that method run when server starts. This method takes a parameter priority from 1 to 7 which decides which method to run first.
- *Forward :* This annotation is applied on method which forwards the request to the uri mentioned as the value.
- *Autowired :* This annotation is applied on fields. It takes a parameter name and set the value of the field when a method of from that class is called by searching that name in : 
    - Request Scope
    - Session Scope
    - Application Scope
- *InjectRequestParameter :* This annotation takes the name of the parameter and set the value of field after finding it from request.
- *RequestParameter :* This annotation is applied on the parameter of the method and a name is given and searches for the parameter in request object and sets the value when method is executed.
- *SecuredAccess :* This annotation is applied on the method if before execution of that method an another method should be executed. Here mention checkPost and gaurd where checkPost is the class and Gaurd is the method to be called before executing this method.
- *Path :* This annotation is applied on the method as well as on the class. We have to pass a value to it by which the method will be called. for example
```Java
    @Path(value = "/sample")
    class User
    {
        @Path(value = "/test")
        public void test()
        {
            System.out.println("Executing Test");
        }
    }
```
* * *
In this case URI for calling this method would be
*/sample/test*
4. Then Start the Tomcat server by using these commands
```bash
    cd /path/to/bin/folder
    startup.bat
```
5. Go to Any software like Postman or browser to test those API's.
___
## Features Available
1. *Javascript Files : * As we start the server some Javascript files are created which includes AJAX Requests to all the API's written by the user to include these Javascript to the Frontend side the URI is `/getJavascriptFiles?fileName=THE_NAME_OF_JAVA_FILE`
for example one of the API is written in Student.java file so to access Javascript file for Student.java the URI would be 
`/getJavascriptFiles?fileName=Student`
2. *ServiceDocument :* This feature is used to create a service document as a PDF of the services written by the user.
The commands to create the service document are : 
```bash
    cd path/to/classes/folder
    java -classpath .\lib\*;. ServiceDocument PACKAGE_OF_SERVICE_CLASSES DESTINATION_OF_PDF_FILE_WITH_NAME

```
for example in our case bash command would be : 
```bash
    java -classpath .\lib\*;. ServiceDocument bobby ./ServiceDocument.pdf 
```
___

## Test Cases
I have provided some examples for using this Repository in package name bobby.
Also the URL to check for the Test API's is 
`http://localhost:8080/Spring-Boot-Clone/bobby/API_URI`
___
## Thanks for the support
___