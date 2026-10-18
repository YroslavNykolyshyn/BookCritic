package com.bc.database

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val isbn: String? = null,
    val coverPath: String? = null,
    val description: String? = null,
    val purchaseLinks: List<PurchaseLink> = emptyList()
)

data class PurchaseLink(
    val storeName: String,
    val url: String
)