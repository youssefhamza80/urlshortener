Dear Catrin,
Kindly, find attached source code of the URL shortening task.

To build and run the solution:
1. Extract the archive.
2. On the root directory of the extracted archive, run the following commands on the cmd/shell terminal:
	a. mvn clean install
	b. mvn spring-boot:run
	
The application should start on port 8080.

To check the functionality:

1. Shortening a URL: Using "Postman" for example, issue a POST request to http://localhost:8080/shortenurl with request body containing the URL to be shortened (raw text).
Return body should contain the shortened URL. Please, copy this shortened URL and paste in the web browser address bar to check it redirects successfully to the original web site.
2. You may run the same shortening request multiple time to check that statistics are updated successfully while maintaining the same short URL.


For user specific portal testing:
1. I have created only 2 users besides the admin user. Authentication is done with HTTP basic authentication. 
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


Implementation/Unit testing is not 100% completed. I'm planning to discuss all details in the interview. Please, let me know if you need any further clarifications.



