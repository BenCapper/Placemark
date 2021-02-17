package org.wit.placemark

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.placemark.models.PlacemarkModel


class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

    val placemarks = ArrayList<PlacemarkModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placemark)
        info("Placemark Main Activity Started.")

        btnAdd.setOnClickListener() {
            val placemark = PlacemarkModel(placemarkTitle.text.toString(), placemarkDescription.text.toString())
            if (placemark.title.isNotEmpty() && placemark.description.isNotEmpty()) {
                placemarks.add(placemark.copy())
                info("add Button Pressed: $placemark")
                info(placemarks)
            }
            else {
                toast ("Enter a title and description")
            }
        }
    }
}