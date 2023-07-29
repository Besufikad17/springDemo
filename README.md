# springDemo

- Simple REST API that contains basic CRUD operation made by using spring boot.

## Dependencies 

- spring-boot-starter-web
- spring-boot-starter-jpa
- spring-boot-starter-validation
- spring-boot-starter-security
- jjwt-api
- jjwt-impl
- jjwt-jackson
- spring-dotenv
- mysql-connector-j

## Usage

### Local

**Clonning**

```bash
   git clone https://github.com/Besufikad17/springDemo.git &
   cd springDemo &
   ./gradlew bootRun
```

**Setting up env variables**

```
DATA_SOURCE_URL=
DATA_SOURCE_USERNAME=
DATA_SOURCE_PASSWORD=
DATA_SOURCE_DRIVER_CLASS_NAME=
```

### Routes

base url : http://localhost:8080/api


| Endpoint    | Request type | Body/Params                                       | Response            | Route          |
|-------------|--------------|---------------------------------------------------|---------------------|----------------|
| SignUp      | POST         | Body: { qfname, lname, email, password, role  }   | { user, token }     | /signup        |
| Login       | GET          | Body: { email, password }                         | { user, token }     | /login         |
| GetAllUsers | GET          | Authorization: "Bearer $token"                    | [ users ]           | /users         |
| GetUserById | GET          | Params: { id }, Authorization: "Bearer $token"    | { user }            | /user/:id      |
| EditUser    | PUT          | Params: { id }, Authorization: "Bearer $token"    | { user }            | /edit/:id      |
| RemoveUser  | DELETE       | Params: { id }, Authorization: "Bearer $token"    |                     | /remove/:id    | 
