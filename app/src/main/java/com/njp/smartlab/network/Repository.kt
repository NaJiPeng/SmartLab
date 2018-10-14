package com.njp.smartlab.network

/**
 * 应用网络请求唯一入口
 */
class Repository private constructor() {

    companion object {
        private var instance: Repository? = null

        fun getInstance(): Repository {
            if (instance == null) {
                instance = Repository()
            }
            return instance!!
        }
    }




}