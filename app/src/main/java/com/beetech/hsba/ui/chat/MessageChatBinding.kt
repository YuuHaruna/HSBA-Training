package com.beetech.hsba.ui.chat

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beetech.hsba.entity.BaseMessage

@BindingAdapter("app:messageChat")
fun messageChat(recyclerView: RecyclerView?, listMessage: List<BaseMessage>?) {
    if (recyclerView?.adapter != null && listMessage != null) {
        (recyclerView.adapter as? ListAdapter<BaseMessage, *>)?.submitList(listMessage)
    }
}

@BindingAdapter("app:galleryImage")
fun galleryImage(recyclerView: RecyclerView?, galleryImageUIModel: List<GalleryImageUIModel>?){
    if (recyclerView?.adapter != null && galleryImageUIModel != null) {
        (recyclerView.adapter as? ListAdapter<GalleryImageUIModel, *>)?.submitList(galleryImageUIModel)
    }
}