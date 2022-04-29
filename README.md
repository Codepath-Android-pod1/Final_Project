# Eventful

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Schema](#Schema)

## Overview
### Description: 
Allows users to find events based on their location and interests. Events include professional and social events. The user can search for a specific event through filters, such as the user's current location, the type of event (social/professional), the organizer of the event, etc.

### App Evaluation
- **Category:** Social Networking / Lifestyle
- **Mobile:** This app would be primarily developed for mobile by taking advantage of location services to find nearby events.
- **Story:** User views a list of nearby social or professional events or based on their search options. Users can also create, read, update, delete events that will be publicly available for anyone on the app to join.
- **Market:** All age groups are targeted. Students, in particular, would benfit the most from this app as this demographic generally on the lookout for events or opportunities to participate in.
- **Habit:** The habit of users depends on how eager they are willing to participate in events. We expect most users to use this app frequently since it leverages on the appeal of both professional opportunities and social events.
- **Scope:** The app could be expanded to contain more features. For example, using OAuth API to purchases tickets and view upcoming events while in the app.

## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**
- Create: Users will be able to create new Events given specific set of informations, like short description of the event, the distances/Location  
- Read: Read Event information such as: Event Description, the distances away, Time of event Title of event etc.  
- Update: Edit Events and profiles  
- Delete: Users will be able to delete the event they posted.

**Optional Nice-to-have Stories**
- Chat Features
- Notifying the user through Twilio
- Google Maps API

### 2. Screen Archetypes
1. Login
2. Register
3. Home (Tab Layout)
4. Edit/Compose (Fragment)
5. Chat/Friend (Fragment)
6. ...

### 3. Navigation

**Tab Navigation** (Tab to Screen)
- Search
- Setting
- Report
- Feedback
- ...

**Flow Navigation** (Screen to Screen)
- Login/Register
- Home with Professional/Social Event
- Profile
- Compose Event 
- Setting
- Report
- Feedback
- ...

## Wireframes
### Design (Figma)
https://www.figma.com/file/Op5T6MotwUd4ZTqTv5OCoz/Kotlin-Project?node-id=0%3A1
<img src='https://github.com/Codepath-Android-pod1/Final_Project/blob/master/WireFrame3.png' />

## Video Walkthrough
Briefly shows the current progress of the project.

<img src='https://github.com/Codepath-Android-pod1/Final_Project/blob/master/CP-3.gif' />


## Schema 
### Models
#### Event

| Property(Key) | Type          | Description  |
| ------------- |-------------  | ------------ |
| eventId       | String        | Unique id for the event |
| organizer     | String        | Event organizer |
| eventName     | String        | Name of the event|
| description   | String        | Description of the event|
| distance      | Int/String    | Distance between event and user|
| location      | String        | Latitude and longitude values of the user|
| time          | String        | Start and end times of the event|
| objectId      | userId        | Name of the user|
| email         | String        | eamil of the user|
| name          | String        | name of the user|
| phonenum      | String        | phone number of the user|

### Networking
#### List of network requests by screen
 - Home Feed Screen
    - (Read/GET) Query events based on filters
 - Organizer List Feed Screen
    - (Read/GET) Query list of Organizers
 - Chat Feed Screen
    - (Read/GET) Query logged in user Chat objects
    - (Create/POST) Send message
 - Create Event Feed Screen  
    - (Create/POST) Create a new event object
 - Create Profile
    - (Create/POST) Create a new user(objectId) with detail included
 - Display Profile
    - (Read/GET) Query details of the user(objectId) and display and profile
 - Remove Profile
    - (Delete) Delete the user(objectId) with detail permanently

#### Existing API Endpoints
##### Ticketmaster Discovery API
- Base URL - [https://app.ticketmaster.com/discovery/v2/](https://developer.ticketmaster.com/products-and-docs/apis/discovery-api/v2/)

 HTTP Verb | Endpoint | Description
 ----------|----------|------------
  `GET`    | /events.json?apikey={apikey}&{params} | gets all events (relevant parameters include `keyword`, `geoPoint`, `postalCode`, `radius`, `unit`, etc)
 
#### Developer's comments
##### Hou Chi
I created most of the fragment and linking them from one to another along with the stylization for each fragment. This project was incredibly fun, it's also the most organized project I've done in a while. At the beginning of the project, I didn't want this project to fail, so I decided to gather everyone together and assigned different tasks to each one. Aaron Picked up Back-end, and I mostly updated the front-end; Andrew was helping out in both, filling up the gap between me and Aaron. I definitely learn a lot more in this 3 weeks than I ever did before. The most annoying part of this whole process was Android studio malfunctioning every so often for no apparent reasons. The GPS location is randomly turned off and the application will crash every so often. Besides that, this is an incredibly fun project. 
##### Aaron Ang
##### Andrew Cho
I worked on the profile uploading the details user typed and getting user data and display whenever the userId is logged in. I had a hard time communicating with some of the individuals and interpreting their code. However, through them, I learned the new stuff such as tracking down the new code step by step with patience and finally was able to modify and improve into better version of the code!(was really happy to interpret). It was really fun to learn how to code the cloud code using java. I had to encounter multiple errors and debugging using the physical note pad(back4app did not have debugging for their cloud code. All they had was DEPLOY!(Run)). Plus, I had error that phone only saves the initial background data(so if I type name as Andrew and later change to Benjamin, it did not change), so I learned that I had to refresh in the background whenever the fragment is on the screen. Moreover, a lot of informations are written in java, so I had to manually convert some code to kotlin since things did not work out if I trusted Android Studio. I am looking forward to improve this application. 
