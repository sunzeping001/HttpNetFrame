package network.jdpay.com.networkframwork.net;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

/**
 * Created by sunzeping on 2016/11/8.
 * Function:
 * Desc:
 */
@SuppressWarnings("ALL") public class RemoteService {

  private static int count = 0;

  public RemoteService() {
  }

  public <T> HttpEntityEnclosingRequestBase buildPostRequest(String url, T entity)
      throws UnsupportedEncodingException {
    HttpPost request = new HttpPost(url);
    Log.e("请求信息url------>", "url:" + url);
    if (entity != null) {
      request.setEntity(this.getEntity(entity));
      request.addHeader("Content-Type", "application/json; charset=UTF-8");
    }
    return request;
  }

  private <T> HttpEntity getEntity(T object) throws UnsupportedEncodingException {
    GsonBuilder builder = new GsonBuilder();
    builder.disableHtmlEscaping();
    Gson gson = builder.create();
    String jsonParamString = gson.toJson(object);
    Log.e("请求信息------>", "request params: " + jsonParamString);
    return new StringEntity(jsonParamString, "UTF-8");
  }
}
