package com.din.retrofitdemo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 *
 * @author: dinzhenyan
 * @time: 2018/12/10 下午6:09
 */
class PoetryAdapter : RecyclerView.Adapter<PoetryAdapter.ViewHolder>() {

    var poetries: MutableList<Poetry.ResultBean>? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView
        var author: TextView
        var content: TextView

        init {
            title = itemView.findViewById(R.id.title)
            author = itemView.findViewById(R.id.author)
            content = itemView.findViewById(R.id.content)
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PoetryAdapter.ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.rv_poetry, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return poetries?.size ?: 0
    }

    override fun onBindViewHolder(p0: PoetryAdapter.ViewHolder, p1: Int) {
        val result = poetries?.get(p1)!!
        p0.title.setText(result.title)
        p0.author.setText(result.authors)

        val content = result.content!!.replace("|", "")
        val res = content.split("。")
        val stringBuilder = StringBuilder()
        for (i in res.indices) {
            stringBuilder.append("\n" + res[i])
        }
        p0.content.setText(stringBuilder)
    }
}