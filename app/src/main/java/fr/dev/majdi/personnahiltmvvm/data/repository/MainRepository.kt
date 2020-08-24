package fr.dev.majdi.personnahiltmvvm.data.repository

import fr.dev.majdi.personna.model.ResponsePersonna
import fr.dev.majdi.personnahiltmvvm.data.network.ApiPersonna
import fr.dev.majdi.personnahiltmvvm.utils.Constants
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

/**
 *Created by Majdi RABEH on 24/08/2020
 *Email = "m.rabeh.majdi@gmail.com")
 */
class MainRepository @Inject constructor(
    private val apiPersonna: ApiPersonna
) {
    fun getUsers(onProductData: OnPersonnaData) = apiPersonna.getAllUsers(Constants.NUMBER_OF_USERS)
        .enqueue(object : retrofit2.Callback<ResponsePersonna> {
            override fun onResponse(
                call: Call<ResponsePersonna>,
                response: Response<ResponsePersonna>
            ) {
                if (response.body() != null)
                    onProductData.onSuccess((response.body() as ResponsePersonna))
            }

            override fun onFailure(call: Call<ResponsePersonna>, t: Throwable) {
                onProductData.onFailure()
            }
        })

    interface OnPersonnaData {
        fun onSuccess(responsePersonna: ResponsePersonna)
        fun onFailure()
    }
}