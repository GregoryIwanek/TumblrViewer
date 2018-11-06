package com.grzegorziwanek.tumblrviewer.ui.common

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import com.grzegorziwanek.tumblrviewer.model.data.entity.Post
import com.grzegorziwanek.tumblrviewer.ui.common.base.OnGenericClickListener
import com.grzegorziwanek.tumblrviewer.ui.common.viewholders.PostPhotoVH
import com.grzegorziwanek.tumblrviewer.ui.common.viewholders.PostQuestionVH
import com.grzegorziwanek.tumblrviewer.ui.common.viewholders.PostRegularVH
import com.grzegorziwanek.tumblrviewer.ui.common.viewholders.PostVH
import com.grzegorziwanek.tumblrviewer.util.AdapterDiffUtil
import com.grzegorziwanek.tumblrviewer.util.ImageLoader
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class BlogAdapter (private val imageLoader: ImageLoader) : RecyclerView.Adapter<PostVH>(),
    OnGenericClickListener<Post> {

    private val clickedFavorite = PublishSubject.create<Favourite>()
    private val items = mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutRes(viewType), parent, false)
        return getViewHolder(viewType, view)
    }

    override fun onBindViewHolder(holder: PostVH, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun setData(items: List<Post>) {
        val diffCallback = AdapterDiffUtil(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int =
        when(items[position].type) {
            TYPE_REGULAR.first -> TYPE_REGULAR.second
            TYPE_PHOTO.first -> TYPE_PHOTO.second
            TYPE_ANSWER.first -> TYPE_ANSWER.second
            else -> TYPE_REGULAR.second
        }

    private fun getLayoutRes(viewType: Int): Int =
        when(viewType) {
            TYPE_REGULAR.second -> R.layout.regular_list_item
            TYPE_PHOTO.second -> R.layout.post_list_item
            TYPE_ANSWER.second -> R.layout.question_list_item
            else -> R.layout.regular_list_item
        }

    private fun getViewHolder(viewType: Int, view: View): PostVH =
        when(viewType) {
            TYPE_REGULAR.second -> PostRegularVH(view, imageLoader, this)
            TYPE_PHOTO.second -> PostPhotoVH(view, imageLoader, this)
            TYPE_ANSWER.second -> PostQuestionVH(view, imageLoader, this)
            else -> throw Throwable("BlogAdapter getItemViewType, unknown item type, type is $viewType")
        }

    override fun onClick(post: Post) {
        val name = post.tumbleLogInner.name
        val avatar = post.tumbleLogInner.avatarSmall
        clickedFavorite.onNext(Favourite(name, avatar))
    }

    fun addFavoriteClicked(): Observable<Favourite> =
        clickedFavorite.doOnNext { println(it) }

    companion object {
        private val TYPE_PHOTO = Pair("photo", 1)
        private val TYPE_REGULAR = Pair("regular", 2)
        private val TYPE_ANSWER = Pair("answer", 3)
    }
}