//package com.beetech.hsba.ui.chat
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import androidx.viewbinding.ViewBinding
//import com.bumptech.glide.Glide
//import com.example.core.R
//import com.example.core.databinding.ItemImageMessageOverTwoBinding
//import com.example.core.databinding.ItemImageMessageTwoBinding
//
//class ChatImageListAdapter :
//    ListAdapter<String, ChatImageListAdapter.ChatImageListViewHolder>(ChatImageListDiffUtil()) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatImageListViewHolder {
//        return when (viewType) {
//            MultiImageType.Two.ordinal -> TwoImageListViewHolder(ChatImageListViewHolder.from(parent, R.layout.item_image_message_two))
//            MultiImageType.OverTwo.ordinal -> OverTwoImageListViewHolder(ChatImageListViewHolder.from(parent, R.layout.item_image_message_over_two))
//            else -> OverTwoImageListViewHolder(ChatImageListViewHolder.from(parent, R.layout.item_image_message_over_two))
//        }
//    }
//
//    override fun onBindViewHolder(holder: ChatImageListViewHolder, position: Int) {
//        val item = getItem(position)
//        when (holder) {
//            is TwoImageListViewHolder -> {
//                holder.bind(item)
//            }
//
//            is OverTwoImageListViewHolder -> {
//                holder.bind(item)
//            }
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if (itemCount == 2) MultiImageType.Two.ordinal else MultiImageType.OverTwo.ordinal
//    }
//
//    class TwoImageListViewHolder (private val binding: ItemImageMessageTwoBinding) :
//        ChatImageListViewHolder(binding) {
//        fun bind(item: String) {
//            binding.imageItemImageMessageTwoImage.apply {
//                Glide
//                    .with(this.context)
//                    .load(item)
//                    .into(this)
//            }
//            binding.executePendingBindings()
//        }
//    }
//
//    class OverTwoImageListViewHolder (private val binding: ItemImageMessageOverTwoBinding) :
//        ChatImageListViewHolder(binding) {
//        fun bind(item: String) {
//            binding.imageItemImageMessageMoreThanTwoImage.apply {
//                Glide
//                    .with(this.context)
//                    .load(item)
//                    .into(this)
//            }
//            binding.executePendingBindings()
//        }
//    }
//
//    sealed class ChatImageListViewHolder(binding: ViewBinding) :
//        RecyclerView.ViewHolder(binding.root){
//        companion object {
//            inline fun <reified DB: ViewDataBinding> from(parent: ViewGroup, layoutId: Int): DB {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                return DataBindingUtil.inflate(layoutInflater, layoutId, parent, false) as DB
//            }
//        }
//        }
//
//    class ChatImageListDiffUtil : DiffUtil.ItemCallback<String>() {
//        override fun areContentsTheSame(oldItem: String, newItem: String) =
//            oldItem == newItem
//
//        override fun areItemsTheSame(oldItem: String, newItem: String) =
//            oldItem.hashCode() == newItem.hashCode()
//    }
//
//    enum class MultiImageType {
//        Two, OverTwo
//    }
//
//}