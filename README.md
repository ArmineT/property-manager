PropertyManagment application
===
Application allows user to register in a system for create, edit and view properties. All registered users can add
property and can edit and view their properties. For easy use you can use swagger by the
following [http://localhost:[port]/swagger-ui.html](http://localhost:8080/swagger-ui.html) (by default port in 8080).

## Requirements

For building and running the application you need:

- JDK 11
- Gradle 6.8.x, 6.9.x, or 7.x.
- Postresql running in port 5432, with role admin, password 9863254 and database with name property_manager_db (
  settings can be changed in application.properties file)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method
in the `com.property.manager.PropertyManagerApplication` class from your IDE.

## Commands for gradle/Spring

- `gradle clean`: clean the generated build dir
- `gradle bootRun`: download the dependencies & run the application

## Authentication

Application is built on top of Spring Security.

* **Registration**
* **Login**

### Registration

By default users will need to register via. an email in order to create an account.

|                                          URL                        | Method |                    Remarks                    |
|---------------------------------------------------------------------|--------|-----------------------------------------------|
|`http://localhost:8080/property/manager/api/authentication/register`                           | POST   |

#### Sample Valid JSON Request Bodys

```json
{
  "username": "domain@example.com",
  "name": "name",
  "surname": "surname",
  "password": "password"
}
```
NOTE: Validations are not working properly, so username can be not in an email format
### Login

|                                          URL                        | Method |                    Remarks                    |
|---------------------------------------------------------------------|--------|-----------------------------------------------|
|`http://localhost:8080/property/manager/api/authentication/login`                            | POST   |Bearer Token, Refresh Token is generated     |
|`http://localhost:8080/property/manager/api/authentication/refreshToken`                    | POST   |Refresh Token from login should be passed     |

#### Sample Valid JSON Request Bodys

login

```json
{
  "username": "domain@example.com",
  "password": "password"
}
```

refresh-token

```json
{
  "token": "1178cd43-21d2-45b4-8b5f-c79aa1d5b76e",
  "username": "domain@example.com"
}
```

## Property Managment

* **Add properties**
* **Edit properties**
* **Get properties**

### Add properties

Saves property in database. Before saving the building, the coordinates for the provided building will be find
from [Geoapity](https://www.geoapify.com/geocoding-api)
and be set. User can save multiple properties at once, in which case coordinates are retrieved from Geoapity in one batch call. Access token is required for identifying user and for later view
or edit the property.

|                                          URL                        | Method |                    Remarks                    |                    Response                    |
|---------------------------------------------------------------------|--------|-----------------------------------------------|-----------------------------------------------|
|`http://localhost:8080/property/manager/api/property`                | POST   |  AccessToken must be passed in header.         |  Given properties with id and lon,lat fields                                             |

#### Sample Valid JSON Request Bodys

```json
[
  {
    "name": "Name",
    "country": "Finland",
    "city": "Espoo",
    "street": " Piispansilta",
    "number": "1",
    "postalCode": "02230",
    "description": "description"
  },
  {
    "name": "Name",
    "country": "Finland",
    "city": "Espoo",
    "street": " Piispansilta",
    "number": "11",
    "postalCode": "02230",
    "description": "description"
  }
]
```

### Edit properties

A User can edit multiple properties at once. All properties must be created by the same authenticated user.New coordinates will be retrieved for Geoapify

|                                          URL                        | Method |                    Remarks                   |
|---------------------------------------------------------------------|--------|----------------------------------------------|
|`http://localhost:8080/property/manager/api/property`                | PUT   |  AccessToken must be passed in header.All JSON objects must have id. |    |

#### Sample Valid JSON Request Bodys


```json
[
  {
    "id": 1,
    "name": "Name",
    "country": "Finland",
    "city": "Espoo",
    "street": " Piispansilta",
    "number": "1",
    "postalCode": "02230",
    "description": "description"
  },
  {
    "id": 2,
    "name": "Name",
    "country": "Finland",
    "city": "Espoo",
    "street": " Piispansilta",
    "number": "11",
    "postalCode": "02230",
    "description": "description"
  }
]
```

### Get properties

User can get properties added by himself/herself.

|                                          URL                        | Method |                    Remarks                   |
|---------------------------------------------------------------------|--------|----------------------------------------------|
|`http://localhost:8080/property/manager/api/property?{page}&{size}&{sort}`                | GET   |  AccessToken must be passed in header. Page, size and sort are not required|   



#### Sample Valid JSON Request

Getting second page with 2 elements, sort order is ascending by id
http://localhost:8080/property/manager/api/property?page=1&size=2&sort=id%2CASC </br>
Getting first page with 20 elements, sort order is descending by street
http://localhost:8080/property/manager/api/property?page=0&size=20&sort=street%2CDESC

