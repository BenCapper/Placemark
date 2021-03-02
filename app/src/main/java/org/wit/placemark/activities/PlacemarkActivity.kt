package org.wit.placemark.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel


class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

    var placemark = PlacemarkModel()
    lateinit var app : MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placemark)
        app = application as MainApp
        info("Placemark Main Activity Started.")
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        if (intent.hasExtra("placemark_edit")) {
            placemark = intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!
            placemarkTitle.setText(placemark.title)
            placemarkDescription.setText(placemark.description)
            btnAdd.setText(R.string.save_placemark)
        }

        btnAdd.setOnClickListener() {
            placemark.title = placemarkTitle.text.toString()
            placemark.description = placemarkDescription.text.toString()
            if (placemark.title.isNotEmpty() && !app.placemarks.findAll().contains(placemark)) {
                app.placemarks.create(placemark.copy())
                info("Add Button Pressed: ${placemark}")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
            else if (app.placemarks.findAll().contains(placemark)){
                app.placemarks.update(placemark.copy())
                info("Update Button Pressed: ${placemark}")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
            else {
                toast(getString(R.string.request_title))
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}