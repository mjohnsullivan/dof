package io.flutter.dof

import android.content.Intent
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.ArrayList

class DataModel(var text: String, var drawable: Int, var color: String)

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.ItemListener {

    private lateinit var arrayList: ArrayList<DataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        arrayList = ArrayList()
        arrayList.add(DataModel("Item 1", R.drawable.ic_train_black_24dp, "#09A9FF"))
        arrayList.add(DataModel("Item 2", R.drawable.ic_train_black_24dp, "#3E51B1"))
        arrayList.add(DataModel("Item 3", R.drawable.ic_train_black_24dp, "#673BB7"))
        arrayList.add(DataModel("Item 4", R.drawable.ic_train_black_24dp, "#4BAA50"))
        arrayList.add(DataModel("Item 5", R.drawable.ic_train_black_24dp, "#F94336"))
        arrayList.add(DataModel("Item 6", R.drawable.ic_train_black_24dp, "#09A9FF"))
        arrayList.add(DataModel("Item 7", R.drawable.ic_train_black_24dp, "#3E51B1"))
        arrayList.add(DataModel("DashCam", R.drawable.ic_dash, "#0A9B88"))
        arrayList.add(DataModel("Item 8", R.drawable.ic_train_black_24dp, "#09A9FF"))
        arrayList.add(DataModel("Item 9", R.drawable.ic_train_black_24dp, "#3E51B1"))
        arrayList.add(DataModel("Item 10", R.drawable.ic_train_black_24dp, "#673BB7"))
        arrayList.add(DataModel("Item 11", R.drawable.ic_train_black_24dp, "#4BAA50"))
        arrayList.add(DataModel("Item 12", R.drawable.ic_train_black_24dp, "#F94336"))
        arrayList.add(DataModel("Item 13", R.drawable.ic_train_black_24dp, "#09A9FF"))
        arrayList.add(DataModel("Item 14", R.drawable.ic_train_black_24dp, "#3E51B1"))

        val adapter = RecyclerViewAdapter(this, arrayList, this)
        recyclerView.adapter = adapter


        /**
        AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        //val layoutManager = AutoFitGridLayoutManager(this, 500)
        //recyclerView.layoutManager = layoutManager

        /**
        Simple GridLayoutManager that spans two columns
         **/
        val manager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager
    }

    override fun onItemClick(item: DataModel) {
        if (item.text == "DashCam") {
            startActivity(Intent(this, IncrementerActivity::class.java))
        } else {
            Toast.makeText(applicationContext, item.text + " is clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
