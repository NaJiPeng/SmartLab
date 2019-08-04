package com.njp.smartlab.ui.news

import androidx.lifecycle.MutableLiveData
import android.util.Log
import com.njp.smartlab.adapter.NewsAdapter
import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.bean.Banner
import com.njp.smartlab.network.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import org.greenrobot.eventbus.EventBus

class NewsViewModel : BaseViewModel() {

    val banners = MutableLiveData<List<Banner>>()

    val dataAdapter = NewsAdapter()

    val adapter = SlideInBottomAnimationAdapter(dataAdapter)

    var isFirstLoad = true

    var page = 1

    fun refresh() {
        Repository.getInstance().getBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                banners.value = it.banner_list
                                dataAdapter.setData(it.news_list)
                                page = 1
                                EventBus.getDefault().post(NewsEvent(NewsEvent.bannerSuccess))
                            } else {
                                EventBus.getDefault().post(NewsEvent(NewsEvent.bannerFail, it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(NewsEvent(NewsEvent.bannerFail, "网络连接失败"))
                        }
                ).let { disposables.add(it) }
    }

    fun loadmore() {
        Repository.getInstance().getNews(++page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                dataAdapter.addData(it.news_list)
                                EventBus.getDefault().post(NewsEvent(NewsEvent.newsSuccess))
                            } else {
                                EventBus.getDefault().post(NewsEvent(NewsEvent.newsFail, it.msg))
                                page--
                            }
                        },
                        {
                            EventBus.getDefault().post(NewsEvent(NewsEvent.newsFail, "网络连接失败"))
                            page--
                        }
                ).let { disposables.add(it) }

    }

}