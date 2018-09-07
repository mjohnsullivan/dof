package io.flutter.dof

import android.content.Intent
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast

import kotlinx.android.synthetic.main.content_main.*
import java.util.ArrayList

class DataModel(var text: String, var drawable: Int, var color: String)

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.ItemListener {

    private lateinit var arrayList: ArrayList<DataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // setSupportActionBar(toolbar)

        arrayList = ArrayList()
        arrayList.add(DataModel(getString(R.string.train_ticket), R.drawable.ic_train_black_24dp, "#09A9FF"))
        arrayList.add(DataModel(getString(R.string.taxi), R.drawable.ic_local_taxi_black_24dp, "#3E51B1"))
        arrayList.add(DataModel(getString(R.string.concert_ticket), R.drawable.ic_music_note_black_24dp, "#673BB7"))
        arrayList.add(DataModel(getString(R.string.shopping), R.drawable.ic_shopping_cart_black_24dp, "#4BAA50"))
        arrayList.add(DataModel(getString(R.string.movie_ticket), R.drawable.ic_local_movies_black_24dp, "#F94336"))
        arrayList.add(DataModel(getString(R.string.book), R.drawable.ic_book_black_24dp, "#09A9FF"))
        arrayList.add(DataModel(getString(R.string.fitness), R.drawable.ic_directions_run_black_24dp, "#3E51B1"))
        arrayList.add(DataModel(getString(R.string.bird_photo), R.drawable.ic_dash, "#0A9B88"))
        arrayList.add(DataModel(getString(R.string.chat), R.drawable.ic_chat_black_24dp, "#09A9FF"))
        arrayList.add(DataModel(getString(R.string.phone_call), R.drawable.ic_local_phone_black_24dp, "#3E51B1"))
        arrayList.add(DataModel(getString(R.string.save_file), R.drawable.ic_cloud_black_24dp, "#673BB7"))
        arrayList.add(DataModel(getString(R.string.book_flight), R.drawable.ic_flight_black_24dp, "#4BAA50"))
        arrayList.add(DataModel(getString(R.string.map), R.drawable.ic_map_black_24dp, "#F94336"))
        arrayList.add(DataModel(getString(R.string.auction), R.drawable.ic_gavel_black_24dp, "#09A9FF"))
        arrayList.add(DataModel(getString(R.string.more_apps), R.drawable.ic_apps_black_24dp, "#3E51B1"))

        val adapter = RecyclerViewAdapter(this, arrayList, this)
        recyclerView.adapter = adapter

        // AutoFitGridLayoutManager that auto fits the cells by the column width defined.
        //val layoutManager = AutoFitGridLayoutManager(this, 500)
        //recyclerView.layoutManager = layoutManager


        // Simple GridLayoutManager that spans two columns
        val manager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager
    }

    override fun onItemClick(item: DataModel) {
        if (item.text == getString(R.string.bird_photo)) {
            startActivity(Intent(this, IncrementerActivity::class.java))
        } else {
            Toast.makeText(applicationContext, item.text, Toast.LENGTH_SHORT).show()
        }
    }
}
