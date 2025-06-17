package com.example.data.repository

import com.example.common.Resource
import com.example.common.firebase.await
import com.example.domain.repository.StorageService
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : StorageService {

    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setHost("firestore.googleapis.com") // Default host for Firestore
            .setSslEnabled(true)
            .setPersistenceEnabled(true) // Enable offline data persistence
            .build()
        db.firestoreSettings=settings

    }

    override suspend fun fetchDoc(collectionId: String, docId: String): Resource<DocumentSnapshot> {
        return try{
            val docRef = db.collection(collectionId).document(docId)
            val result = docRef.get().await()
            if(result.exists()) {
                Resource.Success(result)
            }else{
               Resource.Failure(Exception("No Document Found"))
            }
        }catch (e:Exception){
            e.printStackTrace()
           Resource.Failure(e)
        }
    }

    override suspend fun fetchCollection(collectionId: String): Resource<List<DocumentSnapshot>> {
        return try{
            val result = db.collection(collectionId).get().await()
            if(result.isEmpty){
               Resource.Failure(Exception("No documents Found"))
            }else{
                Resource.Success(result.documents)
            }
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun fetchListOfDoc(
        collectionId: String,
        docList: List<String>
    ): Resource<List<DocumentSnapshot>> {
         return try {
             val docRef = db.collection(collectionId).whereIn(FieldPath.documentId(),docList)
             val result = docRef.get().await()
             if(result.isEmpty){
                 Resource.Failure(Exception("No Document Found"))
             }else{
                 Resource.Success(result.documents)
             }
         }catch (e:Exception){
             e.printStackTrace()
             Resource.Failure(e)
         }
    }

}