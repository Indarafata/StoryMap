package com.dicoding.picodiploma.storyapp.view.map

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.dicoding.picodiploma.storyapp.R
import com.dicoding.picodiploma.storyapp.data.response.ListStoryItem
import com.dicoding.picodiploma.storyapp.data.response.PostStoryResponse
import com.dicoding.picodiploma.storyapp.data.retrofit.ApiConfig

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.dicoding.picodiploma.storyapp.databinding.ActivityMapsBinding
import com.dicoding.picodiploma.storyapp.view.story.StoryActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    val ViewModel = MapsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        ViewModel.story.observe(this) { story ->
            addManyMarker(story)
        }
    }

    private fun addManyMarker(story: List<ListStoryItem>) {
        story.forEach { stories ->
            val latLng = LatLng(stories.lat, stories.lon)
            mMap.addMarker(MarkerOptions().position(latLng).title(stories.name))
        }
    }
}