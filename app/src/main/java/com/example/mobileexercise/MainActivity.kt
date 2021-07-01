package com.example.mobileexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobileexercise.adapter.ItemAdapter
import com.example.mobileexercise.constants.Constants
import com.example.mobileexercise.models.SearchModel
import com.example.mobileexercise.models.SizeModel
import com.example.mobileexercise.service.SearchService
import com.example.mobileexercise.service.SizeService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

private var searchList: SearchModel? = null
private var sizeList: SizeModel? = null

private var photoIncrementation: Int = 0

private var list = ArrayList<String>()

private var adapters = ItemAdapter(list)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvList!!.layoutManager = GridLayoutManager(this, 2)
        rvList!!.adapter = adapters

        getPhotoSearch()
    }

    private fun getPhotoSearch() {
        if (Constants.isNetworkAvailable(this)) {
            val retrofit: Retrofit = Retrofit.Builder().baseUrl(
                Constants.BASE_URL).addConverterFactory(
                GsonConverterFactory.create()).build()

            val service: SearchService = retrofit.create<
                    SearchService>(SearchService::class.java)

            val listCall: Call<SearchModel> = service.getSearch(
                Constants.METHOD_SEARCH,
                Constants.APP_ID,
                Constants.TAGS,
                Constants.PAGE,
                Constants.FORMAT,
                Constants.NO_JSON_CALLBACK
            )

            listCall.enqueue(object: Callback<SearchModel> {
                override fun onResponse(call: Call<SearchModel>,
                                        response: Response<SearchModel>) {
                    if (response!!.isSuccessful) {
                        searchList = response.body()
                        getSizeSearch()
                        Log.i("Response Result", "$searchList")
                    } else {
                        when (response.code()) {
                            400 -> {
                                Log.e("ERROR 400", "Bad Request")
                            }
                            404 -> {
                                Log.e("ERROR 404", "Not Found")
                            }
                            else -> {
                                Log.e("ERROR 400", "Generic Error")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<SearchModel>, t: Throwable) {
                    Log.e("ERROR FAILURE", t!!.message.toString())
                }
            })
        } else {
            Toast.makeText(this@MainActivity,
                "No internet connection.",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSizeSearch() {
        if (Constants.isNetworkAvailable(this)) {
            val retrofit: Retrofit = Retrofit.Builder().baseUrl(
                Constants.BASE_URL
            ).addConverterFactory(
                GsonConverterFactory.create()
            ).build()

            val service: SizeService = retrofit.create<
                    SizeService>(SizeService::class.java)

            for (photo in searchList!!.photos.photo) {

                val listCall: Call<SizeModel> = service.getSize(
                    Constants.METHOD_SIZE,
                    Constants.APP_ID,
                    searchList!!.photos.photo[photoIncrementation].id,
                    Constants.FORMAT,
                    Constants.NO_JSON_CALLBACK
                )

                photoIncrementation++

                listCall.enqueue(object: Callback<SizeModel> {
                    override fun onResponse(call: Call<SizeModel>,
                                            response: Response<SizeModel>) {
                        if (response.isSuccessful) {
                            sizeList = response.body()
                            list.add(sizeList!!.sizes.size[1].source)
                            adapters.notifyDataSetChanged()
                        } else {
                            when (response.code()) {
                                400 -> {
                                    Log.e("ERROR 400", "Bad Request")
                                }
                                404 -> {
                                    Log.e("ERROR 404", "Not Found")
                                }
                                else -> {
                                    Log.e("ERROR 400", "Generic Error")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<SizeModel>,
                                           t: Throwable) {
                        Log.e("ERROR FAILURE", t!!.message.toString())
                    }

                })
            }
        } else {
            Toast.makeText(this@MainActivity,
                "No internet connection.",
                Toast.LENGTH_SHORT).show()
        }
    }
}