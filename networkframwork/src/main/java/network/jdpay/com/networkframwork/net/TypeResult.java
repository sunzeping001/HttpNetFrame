package network.jdpay.com.networkframwork.net;

import java.io.Serializable;

/**
 * Created by sunzeping on 2016/11/9.
 * Function:
 * Desc:
 */
public class TypeResult implements Serializable {

  public static final int INTERNAL_EXCEPTION = 11;
  public static final int INTERNAL_DATA_ERROR = 13;
  public static final int INTERNAL_INTERRUPT = 14;

  private int resultCode;
  private String content;
  private Throwable throwable;

  public TypeResult(int resultCode, String content) {
    this(resultCode, content, null);
  }

  public TypeResult(int resultCode, String content, Throwable throwable) {
    this.resultCode = resultCode;
    this.content = content;
    this.throwable = throwable;
  }

  public int getResultCode() {
    return resultCode;
  }

  public void setResultCode(int resultCode) {
    this.resultCode = resultCode;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  public void setThrowable(Throwable throwable) {
    this.throwable = throwable;
  }

  /**
   * 是否存在异常
   * @return
   */
  public boolean existError() {
    if (throwable != null) {
      return true;
    } else {
      return false;
    }
  }
}
