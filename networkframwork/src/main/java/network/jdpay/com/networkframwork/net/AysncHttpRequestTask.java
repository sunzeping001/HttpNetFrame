package network.jdpay.com.networkframwork.net;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Created by sunzeping on 2016/11/8.
 * Function:
 * Desc:
 */
@SuppressWarnings("ALL") public class AysncHttpRequestTask implements Runnable {

  private HttpUriRequest mHttpRequest;
  private RequestCallback mCallback;
  private AsyncHttpReponseHandler mReponseHandler;
  private final static int TIME_OUT = 30000;

  public AysncHttpRequestTask(HttpUriRequest request, RequestCallback callback,AsyncHttpReponseHandler handler) {
    this.mHttpRequest = request;
    this.mCallback = callback;
    this.mReponseHandler = handler;
  }

  @Override public void run() {
    try {

    } catch (IOException ex) {
      if(mReponseHandler != null){
          mReponseHandler.
      }
    }
  }

  private void sendRequest() throws IOException{
    HttpResponse response = getHttpResponse();
    if (!Thread.currentThread().isInterrupted()) {
      if(this.mReponseHandler != null){
        this.mReponseHandler.sendResponseMessage(mHttpRequest,response);
      }
    }else if(this.mReponseHandler != null){
      this.mReponseHandler.sendCancelMessage();
    }
  }

  private HttpResponse getHttpResponse() throws IOException {
    HttpParams httpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(httpParams, TIME_OUT);
    HttpClient client = new DefaultHttpClient(httpParams);
    return client.execute(mHttpRequest);
  }
}
