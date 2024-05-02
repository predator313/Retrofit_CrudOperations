package com.example.retrofit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.adapter.MainAdapter
import com.example.retrofit.api.ApiInterface
import com.example.retrofit.api.RetrofitInstance
import com.example.retrofit.databinding.ActivityMainBinding
import com.example.retrofit.model.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpRetryException

class MainActivity : AppCompatActivity() {
    private val TAG="coroutine"
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
//        lifecycleScope.launch(Dispatchers.IO) {
//            //network operations were should execute in a background thread
//            //it is a good practice to use this
//            binding.progress.visibility=View.VISIBLE
//            val response=try {
//                RetrofitInstance.api.getAllUser()
//
//            }
//            catch (e:IOException){
//                binding.progress.visibility=View.VISIBLE
//                //basically when there is no internet permission available
//                Log.e(TAG,e.message.toString())
//                return@launch
//            }
//            catch (e:HttpRetryException){
//                binding.progress.visibility=View.VISIBLE
//                Log.e(TAG,e.message.toString())
//                return@launch
//            }
//
//            //now check the response is success or fail
//            if(response.isSuccessful && response.body()!=null){
//                //if every thing is successful then switch to the main main thread
//                withContext(Dispatchers.Main){
//                    myAdapter.users= response.body()!!
//
//                }
//            }
//            binding.progress.visibility=View.GONE
//        }
//        postDataToServer()
        uploadDataToServerViaUrl()
        

    }

    private fun setUpRecyclerView()=binding.rvMain.apply {
        myAdapter= MainAdapter()
        adapter=myAdapter
        layoutManager=LinearLayoutManager(this@MainActivity)
    }
    private fun postDataToServer(){
        lifecycleScope.launch(Dispatchers.IO) {
            val response=try {
                val user=User("Aamir dummy api body",null,"dummy api",101)
                RetrofitInstance.api.createPost(user)
            }
            catch (e:IOException){
                Log.e(TAG,"please check your internet permission")
                return@launch
            }
            catch (e:HttpException){
                Log.e(TAG,"http exception ${e.message()}")
                return@launch
            }
            if(response.isSuccessful && response.body()!=null){
                withContext(Dispatchers.Main){
                    Snackbar.make(binding.root,"${response.code()}",Snackbar.LENGTH_LONG).show()
                    delay(3000L)
                    Snackbar.make(binding.root,"${response.body()}",Snackbar.LENGTH_LONG).show()

                }
            }
        }
    }
    private fun uploadDataToServerViaUrl(){
        lifecycleScope.launch(Dispatchers.IO){
            val response=try {
                RetrofitInstance.api.createPostUrls(101,"urls title","body of the urls")
            }
            catch (e:IOException){
                Log.e(TAG,"please check your internet connection")
                return@launch
            }
            catch (e:HttpException){
                Log.e(TAG,"https exception ${e.message()}")
                return@launch
            }
            if(response.isSuccessful && response.body()!=null){
                withContext(Dispatchers.Main){
                    //means we got the successful response from the api
                    Snackbar.make(binding.root,"${response.body()}",Snackbar.LENGTH_LONG).show()
                    delay(5000L)
                    Snackbar.make(binding.root,"${response.code()}",Snackbar.LENGTH_LONG).show()
                }

            }
        }
    }


}