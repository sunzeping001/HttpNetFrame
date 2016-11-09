package network.jdpay.com.networkframwork.net;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;

/**
 * Created by sunzeping on 2016/11/9.
 * Function: 分发返回信息给前端进行解析使用
 * Desc:
 */
@SuppressWarnings("ALL") public class ResultCallback implements RequestCallback {

  public static final int Pay_Error = 15;
  protected ResultNotifier mResultNotifier = null;
  private volatile int resultCode = 0;

  public ResultCallback(ResultNotifier resultNotifier) {
    this.mResultNotifier = resultNotifier;
  }

  public ResultCallback(ResultNotifier mResultNotifier, int resultCode) {
    this.mResultNotifier = mResultNotifier;
    this.resultCode = resultCode;
  }

  @Override public void callBack(TypeResult result) {
    if (result != null && result.existError()) {
      this.dispatchInternalResult(result);
      this.notifyFinish();
    } else {
      this.dispatchResult(result);
      this.notifyFinish();
    }
  }

  protected void dispatchInternalResult(TypeResult result) {
    switch (result.getResultCode()) {
      case TypeResult.INTERNAL_EXCEPTION:
        String message = "网络异常，请检查您的网络";
        Throwable resultException;
        //noinspection ThrowableResultOfMethodCallIgnored
        if ((resultException = result.getThrowable()) != null) {
          if ((!(resultException instanceof ConnectException)
              || resultException.getCause() == null
              || !(resultException.getCause() instanceof SSLHandshakeException))
              && !(resultException instanceof SSLHandshakeException)) {
            if (resultException instanceof UnknownHostException) {
              message = "服务器解析地址失败";
            } else if (!(resultException instanceof SocketTimeoutException)
                && !(resultException instanceof ConnectTimeoutException)) {
              if (!(resultException instanceof SocketException)
                  && !(resultException instanceof InterruptedIOException)) {
                if (resultException instanceof HttpResponseException) {
                  message = "请求失败，请检查您的网络";
                }
              } else {
                message = "网络异常，请检查您的网络";
              }
            } else {
              message = "网络超时，请检查您的网络";
            }
          } else {
            message = "证书校验失败，请换一个可信任的网络重试，或者访问 m.jdpay.com 官网下载最新版本.";
          }
        }

        if (this.mResultNotifier != null) {
          this.mResultNotifier.onFaile(message);
          return;
        }
        break;
      case TypeResult.INTERNAL_DATA_ERROR:
        if (this.mResultNotifier != null) {
          this.mResultNotifier.onFaile("网络数据解析异常");
          return;
        }
        break;
      case TypeResult.INTERNAL_INTERRUPT:
        if (this.mResultNotifier != null) {
          this.mResultNotifier.onFaile(result.getResultCode(),
              result.getContent() == null ? "请求中断，请检查您的网络" : result.getContent());
        }

        return;
      case Pay_Error:
        if (this.mResultNotifier != null) {
          this.mResultNotifier.onFaile("网络异常，请查看订单状态或稍后重试");
        }
        return;
      default:
        if (this.mResultNotifier != null) {
          this.mResultNotifier.onFaile("网络异常，请检查您的网络");
        }
    }
  }

  protected void dispatchResult(TypeResult result) {
    if (this.mResultNotifier != null && result != null) {
      switch (result.getResultCode()) {
        case 0:
          this.mResultNotifier.onSuccess(result.getContent());
          return;
        case 2:
          this.mResultNotifier.onSMS(result.getContent());
          return;
        default:
          if (result.getResultCode() > 0) {
            this.mResultNotifier.onVerifyFaile(result.getContent());
            return;
          }
          this.mResultNotifier.onFaile(result.getResultCode(), result.getContent());
      }
    }
  }

  /**
   * 结束
   */
  protected void notifyFinish() {
    if (this.mResultNotifier != null) {
      this.mResultNotifier.onFinish();
    }
  }
}
