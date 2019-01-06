package com.baghalii.support.network

import com.baghalii.support.utilities.App
import io.menio.android.models.BaseResponseModel
import io.menio.android.models.MenuModel
import io.menio.android.models.ShoppingCartModel
import io.menio.android.network.postJsonFormats.UpdateShoppingCartJsonModel
import io.menio.android.utilities.AppController
import io.menio.android.utilities.AppController.Companion.app
import io.menio.android.utilities.Constants.ACCESS_TOKEN
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by Amin on 30/05/2018.
 *
 */
interface Webservice {

    @POST("auth/login")
    fun login(@QueryMap query: Map<String, String>, @Body post: LoginPostJsonModel):
            Observable<ShoppingCartModel>

    @GET("restaurant/shopping-cart")
    fun getShoppingCart(@QueryMap query: Map<String, String>):
            Observable<ShoppingCartModel>


    companion object {
        fun create(): Webservice {
            val okHttpClientBuilder = OkHttpClient.Builder()
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
            okHttpClientBuilder.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                if (App.prefs.isSet(App.prefs.accessToken)) {
                    request.addHeader("Authorization", "Bearer " + App.prefs.accessToken)
                }
                request.addHeader("OS", "Android")
                chain.proceed(request.build())
            }
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .baseUrl("http://api.baghalii.com/api/support/v1/")
                    .build()

            return retrofit.create(Webservice::class.java)
        }
    }
}