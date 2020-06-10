package dk.nodes.template.network

import com.google.gson.JsonObject
import dk.nodes.template.models.AccessToken
import dk.nodes.template.models.Authentication
import retrofit2.Call
import retrofit2.http.*

interface IAutService {
    @Headers(
            "Content-Type:application/x-www-form-urlencoded"
    )
    @FormUrlEncoded
    @POST("/connect/token")
    fun getTestMessage(@Field("client_id") client_id: String, @Field("grant_type") grant_type: String, @Field("refresh_token") refresh_token: String): Call<AccessToken>


}