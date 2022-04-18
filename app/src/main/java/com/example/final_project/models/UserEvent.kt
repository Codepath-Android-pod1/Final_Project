package com.example.final_project.models

import com.parse.*

@ParseClassName("Event")
class UserEvent : ParseObject() {
    fun getTitle(): String? {
        return getString(KEY_TITLE)
    }

    fun setTitle(title: String) {
        put(KEY_TITLE, title)
    }

    fun getDescription(): String? {
        return getString(KEY_DESCRIPTION)
    }

    fun setDescription(description: String) {
        put(KEY_DESCRIPTION, description)
    }

    fun getDate(): ParseObject? {
        return getParseObject(KEY_DATE)
    }

    fun setDate(date: ParseObject) {
        put(KEY_DATE, date)
    }

    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }

    fun setUser(user: ParseUser) {
        put(KEY_USER, user)
    }

    fun getLocation(): ParseGeoPoint? {
        return getParseGeoPoint(KEY_LOCATION)
    }

    fun setLocation(parseLocation: ParseGeoPoint) {
        put(KEY_LOCATION, parseLocation)
    }

    companion object {
        const val KEY_TITLE = "title"
        const val KEY_DESCRIPTION = "description"
        const val KEY_DATE = "date"
        const val KEY_LOCATION = "location"
        const val KEY_USER = "user"
    }
}