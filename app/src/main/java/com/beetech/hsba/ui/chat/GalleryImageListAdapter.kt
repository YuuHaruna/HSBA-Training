//package com.example.core.presentation.ui.chat
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.core.R
//import com.example.core.databinding.ItemGalleryImageBinding
//
//class GalleryImageListAdapter(private val onClickGalleryImage: OnClickGalleryImageRecyclerView) :
//    ListAdapter<GalleryImageUIModel, GalleryImageListAdapter.GalleryImageListViewHolder>(
//        GalleryImageListDiffUtil()
//    ) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryImageListViewHolder =
//        GalleryImageListViewHolder.from(parent)
//
//    override fun onBindViewHolder(holder: GalleryImageListViewHolder, position: Int) {
//        val galleryImageUIModel = getItem(position)
//        holder.bind(galleryImageUIModel, onClickGalleryImage)
//    }
//
//    class GalleryImageListViewHolder private constructor(private val binding: ItemGalleryImageBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(
//            galleryImageUIModel: GalleryImageUIModel,
//            itemListener: OnClickGalleryImageRecyclerView
//        ) {
//            binding.imageViewItemGalleryImage.apply {
//                Glide
//                    .with(this.context)
//                    .load(galleryImageUIModel.image)
//                    .placeholder(R.drawable.avatar)
//                    .into(this)
//            }
//
//            binding.checkboxItemDeleteRoom.isChecked = galleryImageUIModel.selected
//
//            binding.checkboxItemDeleteRoom.setOnClickListener {
//                itemListener.onClickGalleryImage(adapterPosition)
//            }
//            binding.executePendingBindings()
//        }
//
//        companion object {
//            fun from(parent: ViewGroup): GalleryImageListViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = ItemGalleryImageBinding.inflate(layoutInflater, parent, false)
//                return GalleryImageListViewHolder(binding)
//            }
//        }
//    }
//
//    class GalleryImageListDiffUtil : DiffUtil.ItemCallback<GalleryImageUIModel>() {
//        override fun areContentsTheSame(
//            oldItem: GalleryImageUIModel,
//            newItem: GalleryImageUIModel
//        ) =
//            oldItem == newItem
//
//        override fun areItemsTheSame(oldItem: GalleryImageUIModel, newItem: GalleryImageUIModel) =
//            oldItem.image == newItem.image
//    }
//
//}