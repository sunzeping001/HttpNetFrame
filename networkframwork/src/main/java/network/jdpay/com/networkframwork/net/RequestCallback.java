package network.jdpay.com.networkframwork.net;

/**
 * Created by sunzeping on 2016/11/8.
 * Function:
 * Desc:
 */
public interface RequestCallback {
  void onSuccess(String response);
  void onFaile(String message);
  void onSMS(String reponse);
  void onVerifyFaile(String reponse);

}
