1. To build and run the solution:
     a. Extract the attached archive.
     b. Change directory to the root directory of the extracted archive, and run the following commands on the cmd/shell terminal:
            - mvn clean install
            - mvn spring-boot:run
	
The application should start on port 8080.

2. To check the functionality:

1. To shorten a URL: Using "Postman" for example, issue a POST request to http://localhost:8080/shortenurl with request body containing the URL to be shortened (raw text).
Return body should contain the shortened URL. 2. To redirect: Please, copy the shortened URL and paste in the web browser address bar to check it redirects successfully to the original web site.
Same shortening request can run multiple time to make sure that statistics are updated successfully while maintaining the same short URL.


For user specific links testing:

Note: I have created only 2 users besides the admin user. Authentication is done with HTTP basic authentication. 

Credentials are: 
user1/user1 (USER role)
user2/user2 (USER role)
admin/admin (ADMIN role)

To shorten a URL by a specific user, please issue a POST request using one of the following links:

http://localhost:8080/user/user1/shortenurl (for user1)
http://localhost:8080/user/user2/shortenurl (for user2)
http://localhost:8080/user/admin/shortenurl (for admin user)

To check statistics:
1. For a specific user statistics, please use one of the following links:

http://localhost:8080/user/user1/statistics (for user1)
http://localhost:8080/user/user2/statistics (for user2)
http://localhost:8080/user/admin/statistics (for admin user)

2. To check statistics for all users (by admin only):
http://localhost:8080/user/admin/allstatistics



All Rest controller APIs are listed here: http://localhost:8080/swagger-ui/index.html
