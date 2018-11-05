package com.njp.smartlab.ui.lesson

import com.njp.smartlab.adapter.LessonsAdapter
import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.network.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import org.greenrobot.eventbus.EventBus

class LessonViewModel : BaseViewModel() {

    val dataAdapter = LessonsAdapter()

    val adapter = SlideInBottomAnimationAdapter(dataAdapter)

    var isFirstLoad = true

    fun getLessons() {
        Repository.getInstance().getLessons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                dataAdapter.setData(it.lessions)
                                EventBus.getDefault().post(LessonEvent(LessonEvent.lessonSuccess))
                            } else {
                                EventBus.getDefault().post(LessonEvent(LessonEvent.lessonFail, it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(LessonEvent(LessonEvent.lessonFail, "网络连接失败"))
                        }
                ).let { disposables.add(it) }
    }

    fun choose(activityId: Int) {
        Repository.getInstance().choose(activityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                EventBus.getDefault().post(LessonEvent(LessonEvent.chooseSuccess))
                            } else {
                                EventBus.getDefault().post(LessonEvent(LessonEvent.chooseFail, it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(LessonEvent(LessonEvent.chooseFail, "网络连接失败"))
                        }
                ).let { disposables.add(it) }
    }

}