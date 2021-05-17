package com.example.proyectomoviles

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyectomoviles.pokeapi.APIActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val child = "LogDeProyectoFinal"
        val file = "logproyecto.txt"

        permissions()
        guardar(getDate(),child,file)

        ma_btn_api.setOnClickListener {
            val intent = Intent(this, APIActivity::class.java)
            startActivity(intent)
        }

        ma_btn_maps.setOnClickListener {
            val intent = Intent(this,MapsActivity::class.java)
            startActivity(intent)
        }

        ma_btn_log.setOnClickListener{
            showAlert(cargar(child, file))
        }

        ma_btn_nopulses.setOnClickListener {
            val intent = Intent(this,VideoActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getDate(): String {
        val currentTime = Calendar.getInstance().time
        return SimpleDateFormat("EEEE , dd-MMM-yyyy hh:mm:ss a").format(currentTime)
    }

    fun guardar(texto: String, child: String, file: String){
        try {
            val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
            val miCarpeta = File(rutaSD, child)
            if (!miCarpeta.exists()){ miCarpeta.mkdir() }
            val ficheroFisico = File(miCarpeta, file)
            ficheroFisico.appendText("$texto\n")
        }catch (e: Exception){
            Toast.makeText(this,"error al guardar", Toast.LENGTH_SHORT).show()
        }
    }

    fun cargar(child: String, file: String):String{
        var texto = ""
        try {
            val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
            val miCarpeta = File(rutaSD, child)
            val ficheroFisico = File(miCarpeta, file)
            val fichero = BufferedReader(
                InputStreamReader(FileInputStream(ficheroFisico))
            )
            texto = fichero.use(BufferedReader::readText)
        }catch (e: Exception){
            Toast.makeText(this,"error al cargar", Toast.LENGTH_SHORT).show()
        }
        return texto
    }

    private fun showAlert(texto: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Log")
        builder.setMessage(texto)
        val dialog = builder.create()
        dialog.show()
    }

    private fun permissions() {
        if ((ContextCompat.checkSelfPermission(
                this,android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED)
            || (ContextCompat.checkSelfPermission(
                this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),123
            )
        }
    }
}