package com.njp.smartlab.ui.timetable

import androidx.lifecycle.MutableLiveData
import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.network.Repository
import com.zhuangfei.timetable.model.Schedule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class TimeTableViewModel : BaseViewModel() {

    val schedules = MutableLiveData<List<Schedule>>().apply { value = ArrayList() }
    val curWeek = MutableLiveData<Int>().apply { value = 1 }

    fun getLessons() {
        Repository.getInstance().getMyLessons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.isSuccess) {
                                schedules.value = it.data
                                curWeek.value = it.curWeek
                                EventBus.getDefault().post(TimeTableEvent(TimeTableEvent.lessonsSuccess))
                            } else {
                                EventBus.getDefault().post(TimeTableEvent(TimeTableEvent.lessonsFail, it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(TimeTableEvent(TimeTableEvent.lessonsFail, "网络连接失败"))
                        }
                ).let { disposables.add(it) }
    }
}