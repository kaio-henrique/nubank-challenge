package br.ufs.nubankchallenge.core.domain.transactions

/**
 *
 * Created by @ubiratanfsoares
 *
 */

data class PurchaseTransaction(
        val transactionId: String,
        val venueName: String,
        val formattedDateTime: String,
        val formattedValue: String
)