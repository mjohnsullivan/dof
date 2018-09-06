package io.flutter.dof

import android.content.Intent
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Gravity
import android.widget.GridLayout
import android.widget.ImageView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var birdieImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        addImagesToGrid()

        birdieImageView.setOnClickListener { _ ->
            startActivity(Intent(this, IncrementerActivity::class.java))
        }
    }

    private fun addImagesToGrid() {

        for (i in 1..7) {
            gridLayout.addView(createImageView(R.drawable.ic_train_black_24dp))
        }
        birdieImageView = createImageView(R.drawable.ic_dash)

        gridLayout.addView(birdieImageView)
        for (i in 1..10) {
            gridLayout.addView(createImageView(R.drawable.ic_train_black_24dp))
        }
    }

    /*
    In xml, it looks like this:

    <ImageView
            android:layout_width="0dp"
            android:layout_height="100dp"
            android:layout_columnWeight="1"
            android:layout_gravity="fill_horizontal"
            android:scaleType="centerCrop"
            android:src="@drawable/ic_train_black_24dp"
            android:background="@color/colorPrimary"
            android:padding="20dp"/>
     */
    private fun createImageView(imageRes: Int) : ImageView {
        val imageView = ImageView(this)

        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        val padding = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                30f,
                resources.displayMetrics).toInt()

        imageView.setPadding(padding, padding, padding, padding)
        imageView.setImageResource(imageRes)
        // imageView.setBackgroundColor(0xFF8363FF.toInt())

        val params = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
        )

        params.height = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                100f,
                resources.displayMetrics).toInt()

        params.width = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                0f,
                resources.displayMetrics).toInt()

        params.setGravity(Gravity.FILL_HORIZONTAL)

        imageView.layoutParams = params

        return imageView
    }
}
