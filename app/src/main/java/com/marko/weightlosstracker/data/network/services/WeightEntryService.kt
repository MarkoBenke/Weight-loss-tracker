package com.marko.weightlosstracker.data.network.services

import com.marko.weightlosstracker.data.network.FirebaseHelper
import com.marko.weightlosstracker.data.network.entities.WeightEntryDto
import com.marko.weightlosstracker.data.util.WeightEntryTable
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WeightEntryService @Inject constructor(
    private val firebaseHelper: FirebaseHelper
) {

    suspend fun getAllEntries(): List<WeightEntryDto>? {
        return try {
            var entryDtos: MutableList<WeightEntryDto>? = null
            firebaseHelper.getEntriesCollection().get().addOnCompleteListener {
                if (it.isSuccessful) {
                    entryDtos = it.result?.toObjects(WeightEntryDto::class.java)
                }
            }.await()
            entryDtos
        } catch (e: Exception) {
            null
        }
    }

    suspend fun insertWeightEntry(weightEntryDto: WeightEntryDto): Boolean {
        return try {
            var isSuccessful = false
            firebaseHelper.getEntriesCollection().document(weightEntryDto.uuid).set(weightEntryDto)
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