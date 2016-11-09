package network.jdpay.com.networkframwork.net;

import android.os.Handler;
import android.os.Message;
import java.io.IOException;
import network.jdpay.com.networkframwork.net.util.TransforUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

/**
 * Created by sunzeping on 2016/11/8.
 * Function:
 * Desc:
 */
@SuppressWarnings("ALL") public class AsyncHttpReponseHandler {

  void onStart() {
  }

  void onFinish() {
  }

  void onSuccess(String content) {
  }

  public void onFailure(Throwable error, String content){

  }

  void sendFailureMessage(Throwable e, String responseBody) {
    this.sendMessage(this.obtainMessage(1, new Object[] { e, responseBody }));
  }

  void sendMessage(Message msg) {
    this.handleMessage(msg);
  }

  protected void handleMessage(Message msg) {
    Object response;
    switch (msg.what) {
      case 0:
        response = (Object) msg.obj;
        this.onSuccess(TransforUtil.transforObjToString(response));
        break;
      case 1:
        Object[] res = (Object[]) msg.obj;
        this.onFailure((Throwable) res[0], (String) res[1]);
        break;
      case 2:
        this.onStart();
        break;
      case 3:
        this.onFinish();
        break;
      case 4:
        this.onCancel();
    }
  }

  Message obtainMessage(int responseMessage, Object response) {
    Message msg;
    msg = Message.obtain();
    msg.what = responseMessage;
    msg.obj = response;
    return msg;
  }

  public void sendResponseMessage(HttpUriRequest uriRequest, HttpResponse response) {
    StatusLine status = response.getStatusLine();
    String responseBody = null;

    try {
      BufferedHttpEntity e;
      HttpEntity temp = response.getEntity();
      if (temp != null) {
        e = new BufferedHttpEntity(temp);
        responseBody = EntityUtils.toString(e, "UTF-8");
      }
    } catch (IOException e) {
      this.sendFailureMessage(e, (String) null);
    }

    this.abortRequest(uriRequest);
    if (status.getStatusCode() >= 300) {
      this.sendFailureMessage(
          new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()),
          responseBody);
    } else {
      this.sendSuccessMessage(status.getStatusCode(), response.getAllHeaders(), responseBody);
    }
  }

  private void sendSuccessMessage(int statusCode, Header[] headers, String responseBody) {
    this.sendMessage(
        this.obtainMessage(0, new Object[] { new Integer(statusCode), headers, responseBody }));
  }

  public void sendCancelMessage() {
    this.sendMessage(this.obtainMessage(4, null));
  }

  public void sendFailMessage(Throwable e, String content){
    this.sendMessage(this.obtainMessage(1, new Object[]{e, content}));
  }

  /**
   * 终止请求
   */
  void abortRequest(HttpUriRequest request) {
    if (request != null) {
      try {
        request.abort();
      } catch (Exception e) {
      }
    }
  }
}
