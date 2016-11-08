package network.jdpay.com.networkframwork.net;

/**
 * Created by sunzeping on 2016/11/8.
 * Function:
 * Desc:
 */
public abstract class RequestParam {
  public RequestParam() {
  }

  public final void encrypt() {
    this.onEncrypt();
  }

  protected void onEncrypt() {
  }

  public String pack(String content) {
    return content;
  }

  public String unpack(String content) {
    return content;
  }
}
