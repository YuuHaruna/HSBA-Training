package com.beetech.hsba.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.beetech.hsba.R
import com.beetech.hsba.databinding.ItemMesssageReceiveBinding
import com.beetech.hsba.databinding.ItemMesssageSendBinding
import com.beetech.hsba.entity.BaseMessage
import com.beetech.hsba.entity.Message
import com.beetech.hsba.entity.MessageType
import com.bumptech.glide.Glide

class ChatListAdapter(val avatarUrl: String = "") :
    ListAdapter<BaseMessage, ChatListViewHolder>(ChatListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        return when (viewType) {
            MessageType.Send.ordinal -> MessageSendListViewHolder(ChatListViewHolder.from(parent, R.layout.item_messsage_send))
            MessageType.Receive.ordinal -> MessageReceiveListViewHolder(ChatListViewHolder.from(parent, R.layout.item_messsage_receive))
//            MessageType.SendImg.ordinal -> ImageMessageSendListViewHolder(ChatListViewHolder.from(parent, R.layout.item_image_messsage_send))
//            MessageType.ReceiveImg.ordinal -> ImageMessageReceiveListViewHolder(ChatListViewHolder.from(parent, R.layout.item_image_messsage_receive))
//            MessageType.SendMultiImg.ordinal -> MultiImageMessageSendListViewHolder(
//                ChatListViewHolder.from(parent, R.layout.item_multi_image_messsage_send)
//            )
//            MessageType.ReceiveMultiImg.ordinal -> MultiImageMessageReceiveListViewHolder(
//                ChatListViewHolder.from(parent, R.layout.item_multi_image_messsage_receive)
//            )
            else -> MessageReceiveListViewHolder(ChatListViewHolder.from(parent, R.layout.item_messsage_receive))
        }
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MessageSendListViewHolder -> {
                holder.bind(item as Message)
            }

            is MessageReceiveListViewHolder -> {
                holder.bind(item as Message, avatarUrl)
            }

//            is ImageMessageSendListViewHolder -> {
//                holder.bind(item as ImageMessage)
//            }
//
//            is ImageMessageReceiveListViewHolder -> {
//                holder.bind(item as ImageMessage, avatarUrl)
//            }
//
//            is MultiImageMessageSendListViewHolder -> {
//                holder.bind(item as ImageMessage)
//            }
//
//            is MultiImageMessageReceiveListViewHolder -> {
//                holder.bind(item as ImageMessage, avatarUrl)
//            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).msgType) {
            MessageType.Send -> MessageType.Send.ordinal
            MessageType.Receive -> MessageType.Receive.ordinal
            MessageType.SendImg -> MessageType.SendImg.ordinal
            MessageType.ReceiveImg -> MessageType.ReceiveImg.ordinal
            MessageType.ReceiveMultiImg -> MessageType.ReceiveMultiImg.ordinal
            MessageType.SendMultiImg -> MessageType.SendMultiImg.ordinal
            MessageType.ReceiveVoice -> MessageType.ReceiveVoice.ordinal
            MessageType.SendVoice -> MessageType.SendVoice.ordinal
        }
    }

    class MessageSendListViewHolder(private val binding: ItemMesssageSendBinding) :
        ChatListViewHolder(binding) {
        fun bind(item: Message) {
            binding.message = item.message
            binding.executePendingBindings()
        }
    }

    class MessageReceiveListViewHolder(private val binding: ItemMesssageReceiveBinding) :
        ChatListViewHolder(binding) {
        fun bind(item: Message, avatarUrl: String) {
            binding.message = item.message

            binding.imageViewItemMessageReceiveAvatar.apply {
                Glide
                    .with(this.context)
                    .load(avatarUrl)
                    .centerCrop()
                    .placeholder(R.drawable.avatar)
                    .into(this)
            }
            binding.executePendingBindings()
        }
    }

//    class ImageMessageSendListViewHolder (private val binding: ItemImageMesssageSendBinding) :
//        ChatListViewHolder(binding) {
//        fun bind(item: ImageMessage) {
//            binding.imageViewItemImageMessageSendImage.apply {
//                Glide
//                    .with(this.context)
//                    .load(item.image[0])
//                    .into(this)
//            }
//            binding.executePendingBindings()
//        }
//    }

//    class ImageMessageReceiveListViewHolder (private val binding: ItemImageMesssageReceiveBinding) :
//        ChatListViewHolder(binding) {
//        fun bind(item: ImageMessage, avatarUrl: String) {
//            binding.imageViewItemImageMessageReceiveImage.apply {
//                Glide
//                    .with(this.context)
//                    .load(item.image[0])
//                    .centerCrop()
//                    .into(this)
//            }
//
//
//            binding.imageViewItemImageMessageReceiveAvatar.apply {
//                Glide
//                    .with(this.context)
//                    .load(avatarUrl)
//                    .centerCrop()
//                    .placeholder(R.drawable.avatar)
//                    .into(this)
//            }
//            binding.executePendingBindings()
//        }
//    }

//    class MultiImageMessageSendListViewHolder (private val binding: ItemMultiImageMesssageSendBinding) :
//        ChatListViewHolder(binding) {
//        fun bind(item: ImageMessage) {
//            binding.recyclerViewMultiImageMessageSend.apply {
//                adapter = ChatImageListAdapter()
//                if (item.image.size == 2) {
//                    layoutManager = GridLayoutManager(this.context, 2)
//                    addItemDecoration(
//                        MarginItemDecorationGridLayout(
//                            2 * this.context.resources.displayMetrics.density.toInt(),
//                            2
//                        )
//                    )
//                } else {
//                    layoutManager = GridLayoutManager(this.context, 3)
//                    addItemDecoration(
//                        MarginItemDecorationGridLayout(
//                            2 * this.context.resources.displayMetrics.density.toInt(),
//                            3
//                        )
//                    )
//                }
//                (adapter as? ListAdapter<String, *>)?.submitList(item.image)
//            }
//            binding.executePendingBindings()
//        }
//    }

//    class MultiImageMessageReceiveListViewHolder (private val binding: ItemMultiImageMesssageReceiveBinding) :
//        ChatListViewHolder(binding) {
//        fun bind(item: ImageMessage, avatarUrl: String) {
//            binding.recyclerViewMultiImageMessageReceive.apply {
//                adapter = ChatImageListAdapter()
//                if (item.image.size == 2) {
//                    layoutManager = GridLayoutManager(this.context, 2)
//                    addItemDecoration(
//                        MarginItemDecorationGridLayout(
//                            2 * this.context.resources.displayMetrics.density.toInt(),
//                            2
//                        )
//                    )
//                } else {
//                    layoutManager = GridLayoutManager(this.context, 3)
//                    addItemDecoration(
//                        MarginItemDecorationGridLayout(
//                            2 * this.context.resources.displayMetrics.density.toInt(),
//                            3
//                        )
//                    )
//                }
//                (adapter as? ListAdapter<String, *>)?.submitList(item.image)
//            }
//
//            binding.imageViewItemMultiImageMessageReceiveAvatar.apply {
//                Glide
//                    .with(this.context)
//                    .load(avatarUrl)
//                    .centerCrop()
//                    .placeholder(R.drawable.avatar)
//                    .into(this)
//            }
//            binding.executePendingBindings()
//        }
}

sealed class ChatListViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        inline fun <reified DB : ViewDataBinding> from(parent: ViewGroup, layoutId: Int): DB {
            val layoutInflater = LayoutInflater.from(parent.context)
            return DataBindingUtil.inflate(layoutInflater, layoutId, parent, false) as DB
        }
    }
}

class ChatListDiffUtil : DiffUtil.ItemCallback<BaseMessage>() {
    override fun areContentsTheSame(oldItem: BaseMessage, newItem: BaseMessage) =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: BaseMessage, newItem: BaseMessage) =
        oldItem.hashCode() == newItem.hashCode()
}