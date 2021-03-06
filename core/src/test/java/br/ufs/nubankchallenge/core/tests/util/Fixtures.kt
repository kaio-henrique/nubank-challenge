package br.ufs.nubankchallenge.core.tests.util

import br.ufs.nubankchallenge.core.domain.chargeback.models.ChargebackOptions
import br.ufs.nubankchallenge.core.domain.chargeback.models.PossibleReclaimReason
import br.ufs.nubankchallenge.core.domain.notice.ChargebackNotice
import br.ufs.nubankchallenge.core.infrastructure.rest.NubankWebService
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * Created by @ubiratanfsoares
 *
 */

object Fixtures {

    fun chargebackOptions(blockCard: Boolean) = ChargebackOptions(
            "Não reconheço essa compra",
            rawCommentHint = "Descreva o que aconteceu",
            shouldBlockCreditcard = blockCard,
            possibleReasons = listOf(
                    PossibleReclaimReason("merchant_recognized", "Reconhece o estabelecimento?"),
                    PossibleReclaimReason("card_in_possession", "Está com o cartão em mãos?")
            )
    )

    fun chargebackNotice() = ChargebackNotice(
            title = "Antes de continuar",
            rawDescription = "<p>Estamos com você nesta! Certifique-se dos pontos abaixo, são muito importantes:<br/><strong>• Você pode <font color=\\\"#6e2b77\\\">procurar o nome do estabelecimento no Google</font>. Diversas vezes encontramos informações valiosas por lá e elas podem te ajudar neste processo.</strong><br/><strong>• Caso você reconheça a compra, é muito importante pra nós que entre em contato com o estabelecimento e certifique-se que a situação já não foi resolvida.</strong></p>",
            primaryActionText = "Continuar",
            secondaryActionText = "Fechar"
    )

    fun httpError(statusCode: Int, errorMessage: String): HttpException {
        val jsonMediaType = MediaType.parse("application/json")
        val body = ResponseBody.create(jsonMediaType, errorMessage)
        val response: Response<String> = Response.error(statusCode, body)
        return HttpException(response)
    }

    fun nubankWebService(apiURL: String): NubankWebService {

        val converter = GsonConverterFactory.create()
        val rxAdapter = RxJava2CallAdapterFactory.create()

        val retrofit = Retrofit.Builder()
                .baseUrl(apiURL)
                .addConverterFactory(converter)
                .addCallAdapterFactory(rxAdapter)
                .build()

        return retrofit.create(NubankWebService::class.java)
    }


}