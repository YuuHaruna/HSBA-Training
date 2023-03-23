package com.beetech.hsba.base.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.beetech.hsba.R
import com.beetech.hsba.base.adapter.EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener
import com.beetech.hsba.databinding.LayoutBaseRecyclerviewBinding
import com.beetech.hsba.extension.ProjectColors
import com.beetech.hsba.extension.color

class BaseRecyclerView : RelativeLayout {
    private var mAdapter: EndlessLoadingRecyclerViewAdapter? = null
    private val binding: LayoutBaseRecyclerviewBinding = LayoutBaseRecyclerviewBinding.inflate(LayoutInflater.from(context), this, false)

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs
    ) {
        setParams(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        setParams(attrs)
    }

    private fun setParams(attrs: AttributeSet) {
        val a =
            context!!.theme.obtainStyledAttributes(attrs, R.styleable.BaseRecyclerView, 0, 0)
        val padding = a.getDimension(R.styleable.BaseRecyclerView_brv_padding, 0f)
        val textNoResult =
            a.getString(R.styleable.BaseRecyclerView_brv_text_no_result)
        binding.tvNoResult.text = textNoResult
        if (padding != 0f) {
            binding.rcvData.setPadding(padding.toInt(), padding.toInt(), padding.toInt(), padding.toInt())
        } else {
            val paddingStart =
                a.getDimension(R.styleable.BaseRecyclerView_brv_padding_start, 0f)
            val paddingEnd =
                a.getDimension(R.styleable.BaseRecyclerView_brv_padding_end, 0f)
            val paddingTop =
                a.getDimension(R.styleable.BaseRecyclerView_brv_padding_top, 0f)
            val paddingBottom =
                a.getDimension(R.styleable.BaseRecyclerView_brv_padding_bottom, 0f)
            binding.rcvData.setPadding(
                paddingStart.toInt(),
                paddingTop.toInt(),
                paddingEnd.toInt(),
                paddingBottom.toInt()
            )
        }
        val enableRefresh =
            a.getBoolean(R.styleable.BaseRecyclerView_brv_enable_refresh, true)
        binding.swipeRefresh.isEnabled = enableRefresh
        binding.swipeRefresh.setColorSchemeColors(context.color(ProjectColors.colorPrimary),context.color(R.color.colorPrimaryDark))
    }

    fun setEnableRefresh(enableRefresh: Boolean) {
        binding.swipeRefresh.isEnabled = enableRefresh
    }

    fun enableLoadMore(enableLoadMore: Boolean) {
        mAdapter?.enableLoadingMore(enableLoadMore)
    }

    fun enableRefresh(enableRefresh: Boolean) {
        binding.swipeRefresh.isRefreshing = enableRefresh
    }


    fun setListLayoutManager(orientation: Int) {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, orientation, false)
        binding.rcvData.layoutManager = layoutManager
    }

    fun setGridLayoutManager(spanCount: Int) {
        val layoutManager = GridLayoutManager(context, spanCount)
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (mAdapter!!.getItemViewType(position) == EndlessLoadingRecyclerViewAdapter.VIEW_TYPE_LOADING) {
                    spanCount
                } else 1
            }
        }
        binding.rcvData.layoutManager = layoutManager
    }

    fun refresh(data: List<Any>) {
        if (data.isNullOrEmpty()) {
            binding.layoutNoResult.visibility = View.VISIBLE
        } else {
            binding.layoutNoResult.visibility = View.GONE
            mAdapter?.refresh(data)
        }
        binding.swipeRefresh.isRefreshing = false
    }

    fun addItem(data: List<Any>) {
        mAdapter?.hideLoadingItem()
        mAdapter?.addModels(data, false)
    }

    fun setOnRefreshListener(refreshListener: OnRefreshListener?) {
        mAdapter?.clear()
        binding.swipeRefresh.setOnRefreshListener(refreshListener)
    }

    fun setOnLoadingMoreListener(loadingMoreListener: OnLoadingMoreListener) {
        mAdapter?.setLoadingMoreListener(loadingMoreListener)
    }

    fun setOnItemClickListener(onItemClickListener: RecyclerViewAdapter.OnItemClickListener?) {
        mAdapter?.addOnItemClickListener(onItemClickListener!!)
    }

    fun setAdapter(adapter: EndlessLoadingRecyclerViewAdapter?) {
        mAdapter = adapter
        binding.rcvData.adapter = adapter
    }
}