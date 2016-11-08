package network.jdpay.com.networkframwork.net;

import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.http.HttpRequest;
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

  private List<SoftReference<HttpRequest>> requestList = new ArrayList<>();


  public RequestManager(String url,RequestParam param,RequestCallback callback) {
    this.mUrl = url;
    this.mParam = param;
    this.mRequestCallback = callback;
    mThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
  }

  /**
   * 构建请求信息
   */
  public void sendRequest(){
    mThreadPool.execute(new AysncHttpRequestTask(getHttpRequest(),mRequestCallback));

  }

  private HttpUriRequest getHttpRequest() {
    RemoteService remoteService = new RemoteService();
    HttpUriRequest request = null;
    try {
      request = remoteService.buildPostRequest(mUrl,mParam);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return request;
  }

  /**
   * 清除所有请求
   */
  public static void cancleRequest(){



  }

}
