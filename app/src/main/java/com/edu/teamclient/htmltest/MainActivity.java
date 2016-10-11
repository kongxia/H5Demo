package com.edu.teamclient.htmltest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    private WebView mWebView;
    private WebAppInterface appInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.btn);
        mWebView = (WebView) findViewById(R.id.wv);

        mButton.setOnClickListener(this);

        //打开本包内assets目录下的index.html文件
        mWebView.loadUrl("file:///android_asset/index.html");
        //设置允许执行JS脚本
        mWebView.getSettings().setJavaScriptEnabled(true);
        appInterface = new WebAppInterface(this);
        //添加通信接口
        mWebView.addJavascriptInterface(appInterface, "app");

    }

    @Override
    public void onClick(View v) {
        appInterface.showName("内容：");
    }

    //类或插件
    class WebAppInterface {

        private Context context;

        public WebAppInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void sayHello(String name) {
            Toast.makeText(context, "输入的内容：" + name, Toast.LENGTH_LONG).show();
        }

        //Android调用JS
        //webView.loadUrl("javascript:functionName()");
        public void showName(String name) {
            //拼接参数
            mWebView.loadUrl("javascript:showName('" + name + "')");
        }
    }
}
