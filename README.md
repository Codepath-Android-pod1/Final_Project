# App Name (To be Decided)

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

## Schema 
### Models

| Property(Key) | Type          | Description  |
| ------------- |-------------  | ------------ |
| Users         | Users         |              |
| Event         | Boolean       |              |
| Opportunity   | Boolean       |              |
| Description   | String        |              |
| Distance      | Int/String    |              |
| Location      | String?       |              |
| Time          | String        |              |
| Title         | String        |              |

### Networking
#### List of network requests by screen

#### Existing API Endpoints
##### Meetup
##### Ticketmaster
