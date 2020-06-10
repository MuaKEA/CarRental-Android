package dk.nodes.template.repositories


import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonObject
import dk.nodes.template.models.Authentication
import dk.nodes.template.network.IAutService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val iAutService: IAutService,  private val sharedPreferences: SharedPreferences) {


    suspend fun fetch_Token(): String? {

        //hide this somewere safe!!
        var accesinfo = Authentication("d2cc153a-3ad4-42b0-b832-43ee335e5ea5", "refresh_token", "Cd_XvxL-ukaX4wFejL1rjQ")

        val response = iAutService.getTestMessage(accesinfo.client_id, accesinfo.grant_type, accesinfo.refresh_token).execute()

        if (response.isSuccessful) {

            val message = response.body()

            if (message != null) {

                message.message?.let {
                    sharedPreferences.edit().putString("access_token", it).apply()

                    return it

                }
            }
        }

        return null
    }




}