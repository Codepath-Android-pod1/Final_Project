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
3. ...

### 3. Navigation

**Tab Navigation** (Tab to Screen)
- First tab
- ...

**Flow Navigation** (Screen to Screen)
- First screen
- ...

## Wireframes
### Design (Figma)
https://www.figma.com/file/Op5T6MotwUd4ZTqTv5OCoz/Kotlin-Project?node-id=0%3A1
<img src='https://github.com/Codepath-Android-pod1/Final_Project/blob/master/WireFrame3.png' />

## Video Walkthrough
Breifly shows the current progress of the project.

<img src='https://github.com/Codepath-Android-pod1/Final_Project/blob/master/CP-1.gif' />


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

#### Existing API Endpoints
##### Ticketmaster Discovery API
- Base URL - [https://app.ticketmaster.com/discovery/v2/](https://developer.ticketmaster.com/products-and-docs/apis/discovery-api/v2/)

 HTTP Verb | Endpoint | Description
 ----------|----------|------------
  `GET`    | /events.json?apikey={apikey}&{params} | gets all events (relevant parameters include `keyword`, `geoPoint`, `postalCode`, `radius`, `unit`, etc)
