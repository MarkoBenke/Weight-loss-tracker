package com.marko.weightlosstracker.data.remote.datasource

import com.marko.weightlosstracker.data.remote.FirebaseHelper
import com.marko.weightlosstracker.data.remote.model.RemoteWeightEntry
import com.marko.weightlosstracker.util.WeightEntryTable
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WeightEntryService @Inject constructor(
    private val firebaseHelper: FirebaseHelper
) {

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
        } catch (e: java.lang.Exception) {
            false
        }
    }
}