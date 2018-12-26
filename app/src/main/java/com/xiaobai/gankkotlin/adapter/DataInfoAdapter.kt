package com.xiaobai.gankkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xiaobai.gankkotlin.R
import com.xiaobai.gankkotlin.model.DataInfo

/**
 * @author baiyunfei on 2018/12/6
 * email 306200335@qq.com
 */
class DataInfoAdapter constructor(
    private val context: Context,
    private val dataInfoList: MutableList<DataInfo>
) : RecyclerView.Adapter<DataInfoAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_data_info, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataInfoList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val dataInfo = dataInfoList[position]

        holder.titleTv.text = dataInfo.title

        //解析日期和时间
        val stringList: MutableList<String> = dataInfo.created_at.split("T") as MutableList<String>
        val string = stringList[0] + " " + stringList[1].substring(0, 5)
        holder.timeTv.text = string

        Glide.with(context).load(addCompressName(dataInfo.imageUrl)).into(holder.photoIv)
    }

    class MyViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        val photoIv: ImageView = view.findViewById(R.id.photo_iv_scene_i)
        val titleTv: TextView = view.findViewById(R.id.title_tv_scene_i)
        val timeTv: TextView = view.findViewById(R.id.time_tv_scene_i)
    }

    private fun addCompressName(name: String): String {
        return "$name?x-oss-process=image/resize,m_fixed,h_300,w_300"
    }
}