package network.jdpay.com.networkframwork.net.util;

/**
 * Created by sunzeping on 2016/11/9.
 * Function:
 * Desc:
 */
public class TransforUtil {

  public static String transforObjToString(Object obj) {
    if (obj == null) {
      return "";
    } else {
      return obj.toString().trim();
    }
  }
}
