package com.njp.smartlab.ui.home

import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.utils.UserInfoHolder

class HomeViewModel : BaseViewModel() {

    val userInfo = UserInfoHolder.getInstance().getUser()

}