package com.example.astra.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.astra.R
import com.example.astra.presentation.Singletons
import com.example.astra.presentation.api.PlanetListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlanetListFragment : Fragment() {

    private lateinit var recyclerView:RecyclerView

    private val adapter = PlanetAdapter(listOf(), ::onClickedPlanet)


    private val layoutManager = LinearLayoutManager(context)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planet_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.country_recyclerview)

        recyclerView.apply {
            layoutManager = this@PlanetListFragment.layoutManager
            adapter = this@PlanetListFragment.adapter
        }



        Singletons.planetApi.getPlanetList().enqueue(object: Callback<PlanetListResponse>{
            override fun onFailure(call: Call<PlanetListResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<PlanetListResponse>, response: Response<PlanetListResponse>) {
                if(response.isSuccessful && response.body() != null){
                    val planetListResponse = response.body()!!
                    adapter.updateList(planetListResponse.bodies)
                }
            }

        })

        /*val countryList = arrayListOf<Country>().apply {
            add(Country("France"))
            add(Country("Roumanie"))
            add(Country("Russie"))
            add(Country("Test"))
        }*/


    }
    private fun onClickedPlanet(planet: Planet) {
        findNavController().navigate(R.id.navigateToPlanetDetailFragment)
    }
}