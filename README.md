# BBC Online Test #

# Description #

The REST API was built using Spring Boot. The API has endpoints for registering users, getting a list of all registered users and sending pushbullet notifications to any of the registered users by username only. The project has been unit tested.

# Prerequisites #

1. JDK 1.8+
2. IDE such as Eclipse
3. Pushbullet installed on a device
4. Pushbullet access token

# Setting up pushbullet # 

1. Visit https://www.pushbullet.com/ and Sign up
2. Go to Devices
    a. Install Pushbullet to a device of your choice (be it a phone or a web browser)
3. Go to Settings, Account
    a. Select Create Access Token
    b. Store the generated access token somewhere safe

# How to run the project #

1. Checkout project and import it as a maven project in your IDE
2. Make sure port 8080 is free and run the project as a Java Application. The application will be deployed on http://localhost:8080

Now using any API testing tool such as Fiddler or Postman you can make calls to the API.

# API #

**POST** http://{hostname}:{port}/api/v1/user
Request for registering a user.

#### Request Body
Request Content Type application/json

    {
        "username": "bbcUser1",
        "accessToken": "anAccessToken"
    }

**anAccessToken** is the token generated in Pushbullet.

#### Response Body
Response Content Type application/json

    {
        "username": "bbcUser1",
        "accessToken": "anAccessToken",
        "creationTime": "2018-02-04T12:02:27",
        "numOfNotificationsPushed": 0
    }

#### Response Status

Status 201 on success
Status 400 if the request is missing a username or a token
Status 409 if the user is already registered

------

**GET** http://{hostname}:{port}/api/v1/user
Request for getting a list of all registered users

#### Response Body
Response Content Type application/json

    [
        {
            "username": "bbcUser1",
            "accessToken": "anAccessToken1",
            "creationTime": "2018-02-04T12:47:51",
            "numOfNotificationsPushed": 1
        },
        {
            "username": "bbcUser3",
            "accessToken": "anAccessToken",
            "creationTime": "2018-02-04T12:47:56",
            "numOfNotificationsPushed": 0
        },
        {
            "username": "bbcUser2",
            "accessToken": "anAccessToken",
            "creationTime": "2018-02-04T12:47:54",
            "numOfNotificationsPushed": 3
        }
    ]

###Response Status

Status 200 on success

------

**POST** http://{hostname}:{port}/api/v1/push/{username}
Request for pushing a Pushbullet notification to a registered user.
 
#### Request Body
Request Content Type application/json

    {
        "title": "string",
        "body": "string",
        "type": "string"
    }

#### Response Body
Response Content Type application/json

    {
        "active": boolean,
        "iden": "string",
        "created": float,
        "modified": float,
        "dismissed": boolean,
        "direction": "string",
        "sender_iden": "string",
        "sender_email": "string",
        "sender_email_normalized": "string",
        "sender_name": "string",
        "receiver_iden": "string",
        "receiver_email": "string",
        "receiver_email_normalized": "string",
        "type": "string",
        "title": "string",
        "body": "string"
    }

#### Response Status

Status 201 on success
Status 400 if the user is not registered

------

**PUT** http://{hostname}:{port}/api/v1/user
Request for updating the access token of an already registered user.

#### Request Body
Request Content Type application/json

    {
        "username": "bbcUser1",
        "accessToken": "anAccessTokenNew"
    }

#### Response Body
Response Content Type application/json

    {
        "username": "bbcUser1",
        "accessToken": "anAccessTokenNew",
        "creationTime": "2018-02-04T12:02:27",
        "numOfNotificationsPushed": 0
    }

#### Response Status

Status 200 on success
Status 400 if the request is missing a username or a token
Status 400 if the user is not registered

------

**DELETE** http://{hostname}:{port}/api/v1/user/{username}
Request for deleting a registered user.

#### Response Status

Status 200 on success
Status 400 if the user is not registered

------

**GET** http://{hostname}:{port}/api/v1/user/{username}
Request for getting a registered user.

#### Response Body
Response Content Type application/json

    {
        "username": "username",
        "accessToken": "anAccessToken",
        "creationTime": "2018-02-04T12:02:27",
        "numOfNotificationsPushed": 0
    }

#### Response Status

Status 200 on success
Status 400 if the user is not registered