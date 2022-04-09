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
- **Category:**
- **Mobile:**
- **Story:**
- **Market:**
- **Habit:**
- **Scope:**

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
<img src='https://github.com/Codepath-Android-pod1/Final_Project/blob/master/20220408_211545.png' />

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
    - (Read/GET) 
    - (Read/GET) Query all events near by.
    - (Read/GET) Picutres and profile of the user. 
    - (Read/GET) bring pop up that shows the event's image and description.
    - (Create/SAVE) Filters the events to look for.
 - Chat Feed Screen
    - (Read/GET) Receive the chat logs. Able to type. Able to send. 
    - (Read/GET) Person's photo and bring the pop up of showing profile of friend
    - (Read/GET) *Move to the Friend's list or may be pop up*
    - (Create/SAVE) Add friend and increase the number of the friend.
 - Create Event Feed Screen  
    - (Read/GET) Preview the event that person is trying to post.
    - (Create/SAVE) Allow user to type title and events and post.
    - (Create/SAVE) Save as a draft.
    - (Create/SAVE) Save and upload the event.
    - (Delete) Delete and reset the everything the person wrote.

#### Existing API Endpoints
##### Meetup
https://www.meetup.com/api/schema/#Event

https://www.meetup.com/api/schema/#Venue

Using [Apollo Kotlin](https://www.apollographql.com/docs/kotlin) client to generate Kotlin models from GraphQL queries

##### Ticketmaster Discovery API
- Base URL - [https://app.ticketmaster.com/discovery/v2/](https://developer.ticketmaster.com/products-and-docs/apis/discovery-api/v2/)

 HTTP Verb | Endpoint | Description
 ----------|----------|------------
  `GET`    | /events.json?apikey={apikey}&{params} | gets all cities (parameters include `keyword`, `geoPoint`, `postalCode`, `radius`, `unit`, etc)
