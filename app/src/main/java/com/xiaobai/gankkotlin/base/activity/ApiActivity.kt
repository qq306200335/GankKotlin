package com.xiaobai.gankkotlin.base.activity

import com.xiaobai.gankkotlin.http.ApiClient
import com.xiaobai.gankkotlin.http.ApiStores
import com.xiaobai.libutils.common.MyLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call

/**
 * @author baiyunfei on 2018/11/23
 * email 306200335@qq.com
 */
abstract class ApiActivity : BaseActivity() {

    private var mCompositeDisposable: CompositeDisposable? = null
    private var calls: MutableList<Call<*>>? = null

    fun apiStores(): ApiStores {
        return ApiClient.retrofit()!!.create(ApiStores::class.java)
    }

    fun addCalls(call: Call<*>) {
        if (calls == null) {
            calls = mutableListOf()
        }
        calls!!.add(call)
    }

    private fun callCancel() {
        if (calls != null && calls!!.size > 0) {
            for (call in calls!!) {
                if (!call.isCanceled)
                    call.cancel()
            }
            calls!!.clear()
        }
    }


    fun <T> addSubscription(observable: Observable<T>, observer: DisposableObserver<T>) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(observer)

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    fun addSubscription(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)
    }

    fun onUnsubscribe() {
        MyLog.d("ApiActivity", "onUnSubscribe")
        //取消注册，以避免内存泄露
        if (mCompositeDisposable != null)
            mCompositeDisposable!!.dispose()
    }

    override fun onDestroy() {
        callCancel()
        onUnsubscribe()
        super.onDestroy()
    }
}