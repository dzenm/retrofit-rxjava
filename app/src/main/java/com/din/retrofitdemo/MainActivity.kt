package com.din.retrofitdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import rx.Observer
import rx.schedulers.Schedulers
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PoetryAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        adapter = PoetryAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        // 请求数据
        rxjavaAndGson("1", "20")
    }

    fun rxjavaAndGson(page: String, count: String) {
        // 获取json数据bean
        val retrofit = Retrofit.Builder()
                // 使用GsonConverter将返回结果解析为JSON
                .addConverterFactory(GsonConverterFactory.create())
                // 设置网络请求的Url地址
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://api.apiopen.top/")
                .build()
        val api = retrofit.create(Api::class.java)
        api.getPoetries(page, count)
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<Poetry> {
                    override fun onError(e: Throwable?) {
                        Log.d("DZY", "onError: " + e?.message)
                        Toast.makeText(this@MainActivity, "onError: " + e?.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onNext(t: Poetry?) {
                        Log.d("DZY", "message: " + t?.message)
                        Log.d("DZY", "code: " + t?.code)
                        // 数据加载成功
                        runOnUiThread {
                            adapter.poetries = (t?.result as MutableList<Poetry.ResultBean>?)!!
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onCompleted() {
                        Toast.makeText(this@MainActivity, "onCompleted", Toast.LENGTH_SHORT).show()
                        Log.d("DZY", "onCompleted: ")
                    }
                })
    }

    fun stringFun() {
        // 获取字符串数据
        thread {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://www.baidu.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(OkHttpClient())
                    .build()
            val api = retrofit.create(Api::class.java)
            val res = api.getCall("http://wwww.baidu.com")
            res.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {

                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("DZY", response.body())
                }
            })
        }
    }

    fun jsonFun() {
        // 获取json数据bean
        thread {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    // 设置网络请求的Url地址
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl("https://api.apiopen.top/")
                    .build()

            val api = retrofit.create(Api::class.java)
            val call = api.getPoetry("1", "20")
            call.enqueue(object : Callback<Poetry> {
                override fun onResponse(call: Call<Poetry>, response: Response<Poetry>) {
                    val protry = response.body()?.result
                    Log.d("DZY", "title: " + protry?.get(0)?.title)
                    Log.d("DZY", "authors: " + protry?.get(0)?.authors)
                }

                override fun onFailure(call: Call<Poetry>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }
}