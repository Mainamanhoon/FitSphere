package com.example.fitsphere.data.service

import com.google.firebase.firestore.DocumentSnapshot

interface StorageService {
    suspend fun fetchDoc(collectionId:String, docId:String): Resource<DocumentSnapshot>
    suspend fun fetchCollection(collectionId:String): Resource<List<DocumentSnapshot>>



}