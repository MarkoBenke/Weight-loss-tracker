package com.marko.weightlosstracker.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseHelper constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    fun getUserCollection(): CollectionReference = fireStore.collection(USERS)

    fun getUserDocument(): DocumentReference = fireStore.collection(USERS).document(getUserId())

    fun getEntriesCollection(): CollectionReference =
        fireStore.collection(USERS).document(getUserId())
            .collection(ENTRIES)

    fun getUserId(): String = auth.currentUser?.uid ?: ""

    companion object {
        private const val USERS = "users"
        private const val ENTRIES = "entries"
    }
}