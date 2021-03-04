package com.fooddelivery.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fooddelivery.retrofit.adapter.PokemonAdapter
import com.fooddelivery.retrofit.data.PokemonDao
import com.fooddelivery.retrofit.data.PokemonDatabase
import com.fooddelivery.retrofit.data.PokemonEntity
import com.fooddelivery.retrofit.databinding.ActivityMainBinding
import com.fooddelivery.retrofit.model.Pokemon
import com.fooddelivery.retrofit.networking.ApiService
import com.fooddelivery.retrofit.networking.NetworkClient
import kotlinx.android.synthetic.main.pokemon_item.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

private lateinit var pokemonAdapter: PokemonAdapter
private lateinit var database: PokemonDatabase
private lateinit var dao: PokemonDao

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokemonAdapter = PokemonAdapter()
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = pokemonAdapter
        }

        val service = NetworkClient().getRetrofit().create(ApiService::class.java)
        service.getAllPokemons().enqueue(object : Callback<List<Pokemon>> {
            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                if (response.isSuccessful) {
                    val listPok: List<Pokemon>? = response.body()

                    val database = PokemonDatabase.getInstance(this@MainActivity)
                    val dao = database.getPokemonDao()

                    GlobalScope.launch(Dispatchers.Main) {
                        dao.insertAll(listPok!!.map {
                            PokemonEntity(
                                name = it.name,
                                categ = it.category,
                                image = it.imageurl
                            )
                        })

                        var list = dao.getAll().map {
                            Pokemon(
                                name = it.name,
                                category = it.categ,
                                imageurl = it.image
                            )
                        }

                        pokemonAdapter.submitList(list)
                    }


                }
            }

            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
            }

        })


        isOnline(this@MainActivity)


    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                Toast.makeText(this@MainActivity, "connection etabliee", Toast.LENGTH_LONG).show()


            } else {
                Toast.makeText(this@MainActivity, "no connection", Toast.LENGTH_LONG).show()

                val database = PokemonDatabase.getInstance(this@MainActivity)
                val dao = database.getPokemonDao()
                GlobalScope.launch(Dispatchers.Main) {
                    var list = dao.getAll().map {
                        Pokemon(
                            name = it.name,
                            category = it.categ,
                            imageurl = it.image
                        )
                    }
                    pokemonAdapter.submitList(list)
                }

            }
        }
        return false
    }
}