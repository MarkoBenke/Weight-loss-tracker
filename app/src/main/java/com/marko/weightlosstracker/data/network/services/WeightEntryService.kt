package com.marko.weightlosstracker.data.network.services

import com.marko.weightlosstracker.data.network.FirebaseHelper
import com.marko.weightlosstracker.data.network.entities.RemoteWeightEntry
import com.marko.weightlosstracker.data.util.WeightEntryTable
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WeightEntryService @Inject constructor(
    private val firebaseHelper: FirebaseHelper
) {

    suspend fun getAllEntries(): List<RemoteWeightEntry>? {
        return try {
            var entries: MutableList<RemoteWeightEntry>? = null
            firebaseHelper.getEntriesCollection().get().addOnCompleteListener {
                if (it.isSuccessful) {
                    entries = it.result?.toObjects(RemoteWeightEntry::class.java)
                }
            }.await()
            entries
        } catch (e: Exception) {
            null
        }
    }

    suspend fun insertWeightEntry(weightEntry: RemoteWeightEntry): Boolean {
        return try {
            var isSuccessful = false
            firebaseHelper.getEntriesCollection().document(weightEntry.uuid).set(weightEntry)
                .addOnCompleteListener {
                    isSuccessful = it.isSuccessful
                }.await()
            isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateWeightEntry(weightEntryMap: HashMap<String, Any>): Boolean {
        return try {
            var isSuccessful = false
            val itemId = weightEntryMap[WeightEntryTable.UUID] as String
            firebaseHelper.getEntriesCollection().document(itemId).update(weightEntryMap)
                .addOnCompleteListener {
                    isSuccessful = it.isSuccessful
                }.await()
            isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteWeightEntry(id: String): Boolean {
        return try {
            var isSuccessful = false
            firebaseHelper.getEntriesCollection().document(id).delete().addOnCompleteListener {
                isSuccessful = it.isSuccessful
            }.await()
            isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}