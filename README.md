# Project Name (To be Decided)

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Schema](#Schema)

## Overview
### Description: 
This App allow users to find event based on their location and interest. Events include professional and social events. The user can search for a specific event through filters, such as the distance from me, the type of opportunity, the host of the event and etc.

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
- Example story

**Optional Nice-to-have Stories**
- Chat Features
- Notifying the user through Twilio
- Google Maps API

## Wireframes
### Design (Figma)
https://www.figma.com/file/Op5T6MotwUd4ZTqTv5OCoz/Kotlin-Project?node-id=0%3A1

### Networking
#### List of network requests by screen

## Data Model

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


## CRUD

Create -> Users will be able to create new Events given specific set of informations, like short description of the event, the distances/Location  
Read -> Read Event information such as: Event Description, the distances away, Time of event Title of event etc.  
Update -> Edit Events and profiles  
Delete -> Users will be able to delete the event they posted.

## API usage

- Google Maps
- Eventbrite
- Ticketmaster
- Twilio (Phone Notification)
- Back4App

## Databases

- Back4Apps
- Firebases ??
- MongoDB ??
