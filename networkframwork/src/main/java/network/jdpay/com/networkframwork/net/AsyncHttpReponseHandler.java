package network.jdpay.com.networkframwork.net;

import android.os.Handler;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * Created by sunzeping on 2016/11/8.
 * Function:
 * Desc:
 */
@SuppressWarnings("ALL") public class AsyncHttpReponseHandler {

  private Handler mHandler;

  void onStart() {
  }

  void onFinish() {
  }

  void onSuccess() {
  }

  public void sendResponseMessage(HttpUriRequest uriRequest, HttpResponse response){

  }

  public void sendCancelMessage(){

  }

  /**
   * 终止请求
   * @param request
   */
  void abortRequest(HttpUriRequest request) {
    if(request != null) {
      try {
        request.abort();
      } catch (Exception e) {
      }
    }

  }

}
