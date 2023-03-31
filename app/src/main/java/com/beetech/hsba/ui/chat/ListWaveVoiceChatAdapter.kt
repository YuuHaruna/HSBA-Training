package com.beetech.hsba.ui.chat

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beetech.hsba.R
import com.beetech.hsba.base.adapter.RecyclerViewAdapter
import com.beetech.hsba.extension.inflate
import com.beetech.hsba.utils.dpToPx

class ListWaveVoiceChatAdapter(mContext: Context): RecyclerViewAdapter(mContext, false) {
    private val maxHeight = 32.dpToPx(mContext)
    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ListWaveVoiceViewHolder(parent.inflate(R.layout.item_wave_voicechat))

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        (holder as ListWaveVoiceViewHolder).itemView.apply {
            var db = (getItem(position, java.lang.Integer::class.java) ?: 0).toInt()
            if(db <= 9_000) db = 9_000
            if (db > 50_000) db = 50_000

            val height = (maxHeight * db / 50_000.0).toInt()

            val wave = findViewById<View>(R.id.view_itemWaveVoiceChat_wave)
            val layoutParams = wave.layoutParams
            layoutParams.height = height
        }
    }

    inner class ListWaveVoiceViewHolder(view: View) : NormalViewHolder(view)
}