package com.marko.weightlosstracker.data.network.services.weightentry

import com.marko.weightlosstracker.data.network.FirebaseHelper
import com.marko.weightlosstracker.data.network.entities.WeightEntryDto
import com.marko.weightlosstracker.data.util.WeightEntryTable
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseWeightEntryService @Inject constructor(
    private val firebaseHelper: FirebaseHelper
) : WeightEntryService {

    override suspend fun getAllEntries(): List<WeightEntryDto>? {
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

    override suspend fun insertWeightEntry(weightEntry: WeightEntryDto): Boolean {
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

    override suspend fun updateWeightEntry(weightEntry: WeightEntryDto): Boolean {
        return try {
            var isSuccessful = false
            firebaseHelper.getEntriesCollection().document(weightEntry.uuid)
                .update(getWeightEntryMap(weightEntry))
                .addOnCompleteListener {
                    isSuccessful = it.isSuccessful
                }.await()
            isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteWeightEntry(id: String): Boolean {
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

    private fun getWeightEntryMap(weightEntry: WeightEntryDto): HashMap<String, Any> {
        return hashMapOf(
            WeightEntryTable.UUID to weightEntry.uuid,
            WeightEntryTable.CURRENT_WEIGHT to weightEntry.currentWeight,
            WeightEntryTable.WAIST_SIZE to weightEntry.waistSize,
            WeightEntryTable.DESCRIPTION to weightEntry.description
        )
    }
}