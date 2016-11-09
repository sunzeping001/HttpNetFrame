package network.jdpay.com.networkframwork.net;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * Created by sunzeping on 2016/11/8.
 * Function:
 * Desc:
 */
@SuppressWarnings("ALL") public class RequestManager {

  /**
   * 线程池
   */
  private ThreadPoolExecutor mThreadPool;
  private String mUrl;
  private RequestParam mParam;
  private RequestCallback mRequestCallback;
  private HttpUriRequest mRequest;
  private List<HttpUriRequest> requestList = new ArrayList<>();
  private ClientResponseHandler mHandler;

  public RequestManager(String url, RequestParam param, RequestCallback callback) {
    this.mUrl = url;
    this.mParam = param;
    this.mRequestCallback = callback;
    this.mHandler = new ClientResponseHandler(mParam,mRequestCallback);
    mThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
  }

  /**
   * 构建请求信息
   */
  public void sendRequest() {
    mThreadPool.execute(new AysncHttpRequestTask(getHttpRequest(), mHandler));
    requestList.add(mRequest);
  }

  private HttpUriRequest getHttpRequest() {
    RemoteService remoteService = new RemoteService();
    mRequest = null;
    try {
      mRequest = remoteService.buildPostRequest(mUrl, mParam);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return mRequest;
  }

  /**
   * 清除所有请求
   */
  public void cancleRequest() {
    if (requestList != null && requestList.size() > 0) {
      for (HttpUriRequest request : requestList) {
        request.abort();
      }
    }
    this.requestList.clear();
  }
}
