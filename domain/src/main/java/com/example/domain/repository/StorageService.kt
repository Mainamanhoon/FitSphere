package com.example.domain.repository

import com.example.common.Resource
import com.google.firebase.firestore.DocumentSnapshot

interface StorageService {
    suspend fun fetchDoc(collectionId:String, docId:String): Resource<DocumentSnapshot>
    suspend fun fetchCollection(collectionId:String): Resource<List<DocumentSnapshot>>
    suspend fun fetchListOfDoc(collectionId: String, docList: List<String>): Resource<List<DocumentSnapshot>>



}