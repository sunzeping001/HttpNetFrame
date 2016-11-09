package network.jdpay.com.networkframwork.net;

/**
 * Created by sunzeping on 2016/11/9.
 * Function:
 * Desc:
 */
public interface ResultNotifier {

  void onStart();
  void onFinish();
  void onSuccess(String response);
  void onFaile(int resultCode,String reponse);
  void onFaile(String reponse);
  void onSMS(String reponse);
  void onVerifyFaile(String reponse);
}
