"# microservice-gateway" 
"# microservice-security" 
--
    In proper implementation the required Resource & Authorization Serves are also created as separated microservices, and their data transfer controlled through Ribbon & Zuul-Gate-Way. However, as a demonstration of working principle in our example The API security system is founded in user-service, theoretical and implementation manners will be explained as follows;
    General component of OAuth Security System,
    https://projects.spring.io/spring-security-oauth/docs/oauth2.html
    Resource Owner:
    The entity or person (user) that owns the protected Resource.
    Resource Server:
    The server which hosts the protected resources, this server should be able to accept the access tokens issued by the Authorization Server and respond with the protected resource if the the access token is valid.
    Client Applications:
    The application or the (software) requesting access to the protected resources on the Resource Server, this client (on some OAuth flows) can request access token on behalf of the Resource Owner.
    Authorization Server:
    The server which is responsible for managing authorizations and issuing access tokens to the clients after validating the Resource Owner identity.
    Note: In our architecture we can have single Authorization Server which is responsible to issue access token which can be consumed by multiple Resource Servers.
    Check the Diagram under user-service
    Curl Commands
    You should install ./JQ (or https://chocolatey.org/install#install-with-cmdexe) before running these Curl commands.
    To get a new token 
    curl trusted-app:secret@localhost:8000/oauth/token -d "grant_type=password&username=user&password=password" | jq 
    To get a refresh token
    curl trusted-app:secret@localhost:8000/oauth/token -d "grant_type=access_token&access_tokem=[ACCESS_TOKEN]" | jq 
    To access a protected resource
    curl -H "Authorization: Bearer [ACCESS_TOKEN]" localhost:8000/api/hello
    Register new User
    curl -H "Authorization: Bearer $(curl register-app:secret@localhost:8000/oauth/token -d "grant_type=client_credentials&client_id=register-app" | jq --raw-output ."access_token")" localhost:8000/api/register -H "Content-Type: application/json" -d '{"username":"new-user","password":"password","firstName":"First","lastName":"Last","email":"email@email.com"}' | jq
    Curl sample commands api/me curl -H "Authorization: Bearer $(curl trusted-app:secret@localhost:8000/oauth/token -d "grant_type=password&username=user&password=password" | jq --raw-output ."access_token")" localhost:8080/api/me | jq
    External references
    This project was inspired by all these references.
    •  Using JWT with Spring Security OAuth
    	https://www.baeldung.com/spring-security-oauth-jwt
    •  JWT authentication with Spring Web
    	https://sdqali.in/blog/2016/07/13/jwt-authentication-with-spring-web---part-5/
    •  JWT Authentication Tutorial: An example using Spring Boot
    	http://www.svlada.com/jwt-token-authentication-with-spring-boot/#jwt-authentication
    •  Spring Oauth2 with JWT Sample
    	http://sgdev-blog.blogspot.com/2016/04/spring-oauth2-with-jwt-sample.html
    •  OAuth2 in depth: A step-by-step introduction for enterprises
    	http://www.swisspush.org/security/2016/10/17/oauth2-in-depth-introduction-for-enterprises
    •  spring-auth-example
    	https://github.com/gdong42/spring-auth-example
    • Spring-cloud OAuth2.0配置
        在spring cloud项目环境中配置
        http://www.saily.top/2016/03/31/Spring-cloud-OAuth2-0%E9%85%8D%E7%BD%AE/
    • Spring Boot REST API (4) - Security with OAuth2
        https://gigsterous.github.io/engineering/2017/03/01/spring-boot-4.html
