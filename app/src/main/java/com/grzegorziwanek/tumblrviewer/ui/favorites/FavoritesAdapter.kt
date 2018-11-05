package com.grzegorziwanek.tumblrviewer.ui.favorites

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import com.grzegorziwanek.tumblrviewer.ui.base.OnGenericClickListener
import com.grzegorziwanek.tumblrviewer.util.AdapterDiffUtil
import com.grzegorziwanek.tumblrviewer.util.ImageLoader
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.favorite_list_item.view.*

class FavoritesAdapter(private val imageLoader: ImageLoader) : RecyclerView.Adapter<FavoritesAdapter.FavoriteVH>(),
    OnGenericClickListener<Favourite> {

    private val clickedSubject = PublishSubject.create<Favourite>()
    private val items = mutableListOf<Favourite>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_list_item, parent, false)
        return FavoriteVH(view, this)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavoriteVH, position: Int) {
        holder.bind(items[position])
    }

    fun setData(items: List<Favourite>) {
        val diffCallback = AdapterDiffUtil(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onClick(t: Favourite) {
        clickedSubject.onNext(t)
    }

    fun clickedFavorite(): Observable<Favourite> {
        return clickedSubject.doOnNext { println(it) }
    }

    inner class FavoriteVH(itemView: View,
                           private val listener: OnGenericClickListener<Favourite>) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.btn_clear.setOnClickListener {
                if (this::favourite.isInitialized) {
                    listener.onClick(favourite)
                }
            }
        }

        private lateinit var favourite: Favourite

        fun bind(favourite: Favourite) {
            this.favourite = favourite
            itemView.tv_name.text = favourite.name
            imageLoader.loadImageFromUrlGlide(favourite.avatar, R.drawable.abc_ab_share_pack_mtrl_alpha, itemView.iv_avatar)
        }
    }
}