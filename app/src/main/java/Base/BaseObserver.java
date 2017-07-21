package Base;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * @author Muyangmin
 * @since 1.0.0
 */
public abstract class BaseObserver<E extends BaseResponse> implements Observer<E> {

    protected final String LOG_TAG = getClass().getSimpleName();

    private final BaseUiInterface mUiInterface;

    public BaseObserver(BaseUiInterface baseUiInterface) {
        mUiInterface = baseUiInterface;
    }

    @Override
    public void onCompleted() {
        mUiInterface.showLoadingComplete();
    }

    @Override
    public void onError(Throwable throwable) {
        //L.e("BaseObserver", "Request Error!", throwable);
        handleError(throwable, mUiInterface, LOG_TAG);
    }

    /**
     * 按照通用规则解析和处理数据请求时发生的错误。这个方法在执行支付等非标准的REST请求时很有用。
     */
    public static void handleError(Throwable throwable, BaseUiInterface mUiInterface, String LOG_TAG){
        mUiInterface.showLoadingComplete();
        if (throwable == null) {
            mUiInterface.showUnknownException();
            return;
        }
        //分为以下几类问题：网络连接，数据解析，客户端出错【空指针等】，服务器内部错误
        if (throwable instanceof SocketTimeoutException || throwable instanceof ConnectException || throwable instanceof UnknownHostException) {
            mUiInterface.showNetworkException();
        } else if ((throwable instanceof JsonSyntaxException) || (throwable instanceof
                NumberFormatException) || (throwable instanceof MalformedJsonException)) {
            mUiInterface.showDataException("数据解析出错");
        } else if ((throwable instanceof HttpException)) {
            mUiInterface.showDataException("服务器错误(" + ((HttpException) throwable).code()+")");
            //自动上报这个异常
           // L.e(true, LOG_TAG, "Error while performing response!", throwable);
        } else if (throwable instanceof NullPointerException) {
            mUiInterface.showDataException("客户端开小差了，攻城狮正在修复中...");
            //自动上报这个异常
            //L.e(true, LOG_TAG, "Error while performing response!", throwable);
        } else {
//            mUiInterface.showUnknownException();
        }
    }

    @Override
    public void onNext(E response) {
        Log.i("mrl","服务器大概返回"+response);
        switch (response.getCode()) {
            case BaseResponse.RESULT_CODE_SUCCESS:
                onSuccess(response);
                break;
           /* case BaseResponse.RESULT_CODE_TOKEN_EXPIRED:
                if (mUiInterface instanceof LoginActivity || mUiInterface instanceof SplashActivity){
                    onDataFailure(response);
                }
                else if (mUiInterface instanceof BaseActivity || mUiInterface instanceof BaseFragment){
                    final BaseActivity activity;
                    if (mUiInterface instanceof BaseFragment){
                        activity = (BaseActivity) ((BaseFragment)mUiInterface).getActivity();
                    }
                    else{
                        activity = (BaseActivity)mUiInterface;
                    }
                    AlertDialog alertDialog = new AlertDialog.Builder(activity)
                            .setMessage("你的账号在别处登录，请重新登录")
                            .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    activity.startActivity(LoginSelectActivity.createIntent(activity));
                                    activity.sendFinishBroadcast(LoginSelectActivity.class.getSimpleName());
                                }
                            })
                            .create();
                    alertDialog.show();
                } else{
                    onDataFailure(response);
                }
                break;*/
            default:
                onDataFailure(response);
        }
    }

    public abstract void onSuccess(E response);

    protected void onDataFailure(E response) {
        String msg = response.getMsg();
        //L.w(LOG_TAG, "request data but get failure:" + msg);
        if (!TextUtils.isEmpty(msg)) {
            mUiInterface.showDataException(response.getMsg());
        }
        else{
            mUiInterface.showUnknownException();
        }
    }

    /**
     * Create a new silence, non-leak observer.
     */
    public static <T> Observer<T> silenceObserver(){
        return new Observer<T>() {
            @Override
            public void onCompleted() {
                //Empty
            }

            @Override
            public void onError(Throwable e) {
                //Empty
            }

            @Override
            public void onNext(T t) {
                //Empty
            }
        };
    }

}