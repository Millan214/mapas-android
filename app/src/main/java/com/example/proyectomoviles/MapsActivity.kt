package com.example.proyectomoviles

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception
import kotlin.random.Random

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener{

    private lateinit var mMap: GoogleMap
    private val DBNAME = "mapsDBproyecto"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps2)
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

        playMusic()

        val sydney = LatLng(-34.0, 151.0)
        val newYork = LatLng(40.730610,-73.935242)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.addMarker(MarkerOptions().position(newYork).title("Marker in New York")).isDraggable = true
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newYork, 15F))

        val puntos = getPuntos()
        puntos.forEach { punto: Punto ->
            val pos = LatLng(punto.lat,punto.long)
            mMap.addMarker(MarkerOptions().position(pos).title("Lat: ${pos.latitude} / Long: ${pos.longitude}"))
        }

        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)
    }

    private fun guardarPunto(lat:Double , long:Double) {
        val admin = AdminSQLite(this,DBNAME,null,1)
        val bd = admin.writableDatabase
        val fila = ContentValues()
        fila.put("lat",lat)
        fila.put("long", long)
        bd.insert("puntos",null,fila)
        bd.close()
    }

    private fun getPuntos():MutableList<Punto> {
        val admin = AdminSQLite(this,DBNAME,null,1)
        val bd = admin.readableDatabase
        val cursor: Cursor

        try{
            cursor = bd.rawQuery("select * from puntos",null)
        }catch(e: SQLiteException){
            bd.execSQL("select * from puntos")
            return ArrayList()
        }

        var lat: Double
        var long: Double

        val puntos = mutableListOf<Punto>()

        if(cursor.moveToFirst()){
            do {
                lat = cursor.getDouble(cursor.getColumnIndex("lat"))
                long = cursor.getDouble(cursor.getColumnIndex("long"))
                puntos.add(Punto(lat,long))
            }while(cursor.moveToNext())
        }
        return puntos
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        Toast.makeText(this,"${marker?.position}",Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMapClick(pos: LatLng?) {
        mMap.addMarker(MarkerOptions().position(pos!!).title("Lat: ${pos.latitude} / Long: ${pos.longitude}"))
        guardarPunto(pos.latitude,pos.longitude)
    }

    private fun playMusic() {
        val mp = Intent(this,ServicioMusica::class.java)
        startService(mp)
    }

    private fun stopMusic() {
        val mp = Intent(this,ServicioMusica::class.java)
        stopService(mp)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
    }

}