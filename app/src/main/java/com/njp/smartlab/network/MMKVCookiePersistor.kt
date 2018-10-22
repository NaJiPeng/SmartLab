package com.njp.smartlab.network

import com.franmontiel.persistentcookiejar.persistence.CookiePersistor
import com.franmontiel.persistentcookiejar.persistence.SerializableCookie
import com.tencent.mmkv.MMKV
import okhttp3.Cookie

/**
 * MMVK的Cookie持久化实现
 */
class MMKVCookiePersistor : CookiePersistor {

    private val mmkv = MMKV.mmkvWithID("CookiePersistence")

    override fun loadAll(): List<Cookie> {
        return mmkv.allKeys()?.map {
            SerializableCookie().decode(mmkv.decodeString(it) as String)
        }?: ArrayList()
    }

    override fun saveAll(cookies: MutableCollection<Cookie>?) {
        cookies?.forEach {
            mmkv.encode(createCookieKey(it), SerializableCookie().encode(it))
        }
    }

    override fun removeAll(cookies: MutableCollection<Cookie>?) {
        cookies?.forEach {
            mmkv.remove(createCookieKey(it))
        }
    }

    override fun clear() {
        mmkv.clearAll()
    }

    private fun createCookieKey(cookie: Cookie): String {
        return (if (cookie.secure()) "https" else "http") + "://" + cookie.domain() + cookie.path() + "|" + cookie.name()
    }

}