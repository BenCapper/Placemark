package org.wit.placemark.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.helpers.readImage
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel


class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

    var placemark = PlacemarkModel()
    lateinit var app : MainApp
    var edit = false
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placemark)
        app = application as MainApp
        info("Placemark Main Activity Started.")
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        if (intent.hasExtra("placemark_edit")) {
            edit = true
            placemark = intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!
            placemarkImage.setImageBitmap(readImageFromPath(this, placemark.image))
            placemarkTitle.setText(placemark.title)
            placemarkDescription.setText(placemark.description)
            btnAdd.setText(R.string.save_placemark)
            chooseImage.setText(R.string.button_changeImage)
        }

        btnAdd.setOnClickListener() {
            placemark.title = placemarkTitle.text.toString()
            placemark.description = placemarkDescription.text.toString()
            if (placemark.title.isEmpty()) {
                toast(R.string.request_title)
            } else {
                if (edit) {
                    app.placemarks.update(placemark.copy())
                    info("update Button Pressed: $placemarkTitle")
                    setResult(AppCompatActivity.RESULT_OK)
                    finish()
                } else {
                    app.placemarks.create(placemark.copy())
                    info("add Button Pressed: $placemarkTitle")
                    setResult(AppCompatActivity.RESULT_OK)
                    finish()
                }
            }

        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    placemark.image = data.getData().toString()
                    placemarkImage.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }


}