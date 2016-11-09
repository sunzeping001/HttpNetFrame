package network.jdpay.com.networkframwork.net;

import android.text.TextUtils;

/**
 * Created by sunzeping on 2016/11/9.
 * Function:
 * Desc:
 */
public class ClientResponseHandler extends AsyncHttpReponseHandler {

  private RequestParam mParam;
  private RequestCallback mCallback;

  public ClientResponseHandler(RequestParam param, RequestCallback callback) {
    this.mParam = param;
    this.mCallback = callback;
  }

  public void onSuccess(String content) {
    if (this.mCallback != null) {
      TypeResult result;
      if (TextUtils.isEmpty(content)) {
        result = new TypeResult(TypeResult.INTERNAL_EXCEPTION, "", new Exception("contentis null"));
      } else {
        try {
          result = getPackResult(content);
        } catch (Exception ex) {
          result = new TypeResult(13, "1", ex);
        }
      }
      mCallback.callBack(result);
    }
  }

  public void onFailure(Throwable error, String content) {
    if(this.mCallback != null) {
      this.mCallback.callBack(new TypeResult(TypeResult.INTERNAL_EXCEPTION, "", error));
    }
  }


  /**
   * 包装返回参数
   */
  public TypeResult getPackResult(String content) {
    TypeResult result = new TypeResult(0, content);
    return result;
  }
}
