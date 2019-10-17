package com.yyzh.myapplication.base

//import com.franmontiel.persistentcookiejar.PersistentCookieJar
//import com.franmontiel.persistentcookiejar.cache.SetCookieCache
//import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.yyzh.commenlibrary.base.BaseApplication
import com.yyzh.commenlibrary.utils.NetWorkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File


/**
 * Created by luyao
 * on 2018/3/13 15:45
 */
object WanRetrofitClient : BaseRetrofitClient() {

    val service by lazy { getService(WanService::class.java, WanService.BASE_URL) }
    private val cookieJar by lazy { PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.CONTEXT)) }

    override fun handleBuilder(builder: OkHttpClient.Builder) {

        val httpCacheDirectory = File(BaseApplication.CONTEXT.cacheDir, "responses")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        builder.cache(cache)
                .cookieJar(cookieJar)
                .addInterceptor { chain ->
                    var request = chain.request()
                    if (!NetWorkUtils.isNetworkAvailable(BaseApplication.CONTEXT)) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build()
                    }
                    val response = chain.proceed(request)
                    if (!NetWorkUtils.isNetworkAvailable(BaseApplication.CONTEXT)) {
                        val maxAge = 60 * 60
                        response.newBuilder()
                                .removeHeader("Pragma")
                                .header("Cache-Control", "public, max-age=$maxAge")
                                .build()
                    } else {
                        val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                        response.newBuilder()
                                .removeHeader("Pragma")
                                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                                .build()
                    }

                    response
                }
    }
}