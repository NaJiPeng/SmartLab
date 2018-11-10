package com.njp.smartlab.ui.main

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.view.View
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.core.cause.ResumeFailedCause
import com.liulishuo.okdownload.core.listener.DownloadListener3
import com.njp.smartlab.R
import com.njp.smartlab.bean.UpdateResponseBody
import com.njp.smartlab.databinding.ActivityMainBinding
import com.njp.smartlab.databinding.DialogUpdateBinding
import com.njp.smartlab.utils.ToastUtil
import com.tencent.mmkv.MMKV
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import java.lang.Exception

/**
 * 主activity
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var exitTime: Long = 0
    lateinit var navController: NavController
    lateinit var loadingDialog: Dialog
    private lateinit var updateDialog: Dialog
    private lateinit var updateBinding: DialogUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = fragment.navController

        loadingDialog = Dialog(this, R.style.dialog).apply {
            setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_loading, null))
            setCancelable(false)
        }

        updateDialog = Dialog(this, R.style.dialog).apply {
            updateBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_update, null, false)
            setContentView(updateBinding.root)
            setCancelable(false)
        }

        viewModel.login()
        viewModel.update()

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.home && (System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis()
            ToastUtil.getInstance().show("再按一次退出程序")
        } else {
            super.onBackPressed()
        }
    }

    private fun download(url: String, file: File) {
        DownloadTask.Builder(url, file.parentFile)
                .setFilename(file.name)
                .setMinIntervalMillisCallbackProcess(10)
                .setPassIfAlreadyCompleted(false)
                .build().enqueue(object : DownloadListener3() {
                    override fun warn(task: DownloadTask) {
                    }

                    override fun connected(task: DownloadTask, blockCount: Int, currentOffset: Long, totalLength: Long) {
                    }

                    override fun retry(task: DownloadTask, cause: ResumeFailedCause) {
                    }

                    override fun started(task: DownloadTask) {
                        updateBinding.layoutBtn.visibility = View.INVISIBLE
                        updateBinding.progressBar.visibility = View.VISIBLE
                        updateBinding.progressBar.progress = 0
                    }

                    override fun completed(task: DownloadTask) {
                        updateDialog.dismiss()
                        install(file)
                    }

                    override fun error(task: DownloadTask, e: Exception) {
                        updateDialog.dismiss()
                        ToastUtil.getInstance().show("应用更新失败")
                    }

                    override fun canceled(task: DownloadTask) {
                        updateDialog.dismiss()
                    }

                    override fun progress(task: DownloadTask, currentOffset: Long, totalLength: Long) {
                        updateBinding.progressBar.progress = (100 * currentOffset / totalLength).toInt()
                    }

                })
    }

    private fun install(file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val apkUri = FileProvider.getUriForFile(this, "com.njp.smartlab", file)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        }
        startActivity(intent)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun uodate(update: UpdateResponseBody) {
        if (!updateDialog.isShowing) {
            updateBinding.update = update

            updateBinding.btnCancel.setOnClickListener {
                MMKV.defaultMMKV().encode("ignore", update.version_code)
                updateDialog.dismiss()
            }

            updateBinding.btnUpdate.setOnClickListener {
                val file = File(externalCacheDir, "SmartLab${update.version_code}.apk")
                download(update.link, file)
            }
            updateBinding.layoutBtn.visibility = View.VISIBLE
            updateBinding.progressBar.visibility = View.INVISIBLE
            updateBinding.progressBar.progress = 0
            updateDialog.show()
        }
    }

}
