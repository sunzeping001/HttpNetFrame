package network.jdpay.com.httpnetframe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import network.jdpay.com.networkframwork.net.AysncHttpRequestTask;
import network.jdpay.com.networkframwork.net.RemoteService;

public class MainActivity extends Activity {

  private TextView tv_show;
  private Button btn_send;
  private List<String> list;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
  }

  private void initView() {
    tv_show = (TextView) findViewById(R.id.tv_show);
    btn_send = (Button) findViewById(R.id.btn_send);
    list = new ArrayList<>();
    list.add(0, "小孙哥哥");
    list.add(0, "张三");
    list.add(0, "李四");
    list.add(0, "王五");
    list.add(0, "旺财");
    list.add(0, "狗不理");
    list.add(0, "酸菜鱼");

    btn_send.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {



      }
    });
  }
}
