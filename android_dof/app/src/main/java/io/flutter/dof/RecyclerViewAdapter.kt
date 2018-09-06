package io.flutter.dof

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import java.util.ArrayList


class RecyclerViewAdapter(internal var mContext: Context, internal var mValues: ArrayList<DataModel>, protected var mListener: ItemListener?) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var textView: TextView
        var imageView: ImageView
        var relativeLayout: RelativeLayout
        private lateinit var item: DataModel

        init {

            v.setOnClickListener(this)
            textView = v.findViewById(R.id.textView) as TextView
            imageView = v.findViewById(R.id.imageView) as ImageView
            relativeLayout = v.findViewById(R.id.relativeLayout) as RelativeLayout

        }

        fun setData(item: DataModel) {
            this.item = item

            textView.text = item.text
            imageView.setImageResource(item.drawable)
            relativeLayout.setBackgroundColor(Color.parseColor(item.color))

        }


        override fun onClick(view: View) {
            if (mListener != null) {
                mListener!!.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(Vholder: ViewHolder, position: Int) {
        Vholder.setData(mValues[position])

    }

    override fun getItemCount(): Int {

        return mValues.size
    }

    interface ItemListener {
        fun onItemClick(item: DataModel)
    }
}