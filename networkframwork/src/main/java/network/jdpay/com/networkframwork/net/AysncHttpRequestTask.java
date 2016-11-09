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
  private AsyncHttpReponseHandler mReponseHandler;
  private HttpClient mClient;
  private final static int TIME_OUT = 10 * 1000;

  public AysncHttpRequestTask(HttpUriRequest request,
      AsyncHttpReponseHandler handler) {
    this.mHttpRequest = request;
    this.mReponseHandler = handler;
  }

  @Override public void run() {
    makeRequest();
  }

  public void makeRequest() {
    try {
      sendRequest();
    } catch (IOException ex) {
      if (mReponseHandler != null) {
        mReponseHandler.sendCancelMessage();
      }
    }
    this.abortRequest();
  }

  private void sendRequest() throws IOException {
    HttpResponse response = getHttpResponse();
    if (!Thread.currentThread().isInterrupted()) {
      if (this.mReponseHandler != null) {
        this.mReponseHandler.sendResponseMessage(mHttpRequest, response);
      }
    } else if (this.mReponseHandler != null) {
      this.mReponseHandler.sendCancelMessage();
    }
  }

  private HttpResponse getHttpResponse() throws IOException {
    HttpParams httpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(httpParams, TIME_OUT);
    mClient = new DefaultHttpClient(httpParams);
    return mClient.execute(mHttpRequest);
  }

  private void abortRequest() {
    try {
      this.mHttpRequest.abort();
    } catch (Exception e) {
    }
  }

  private void cleanConnections() {
    try {
      this.mClient.getConnectionManager().closeExpiredConnections();
    } catch (Exception e) {
    }
  }
}
