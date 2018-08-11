package io.flutter.dof

import android.os.Bundle
import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant

class IncrementerActivity : FlutterActivity() {

    private val _tag = "IncrementerActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)
    }

    /*
    override fun createFlutterView(context: Context) : FlutterView {
        val flutterEngine = FlutterNativeView(this)
        val flutterView = FlutterView(this, null, flutterEngine)
        flutterView.setInitialRoute("route1")
        flutterView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        )
        Log.w(_tag, "Creating Flutter view")
        setContentView(flutterView)
        return flutterView
    }*/
}
