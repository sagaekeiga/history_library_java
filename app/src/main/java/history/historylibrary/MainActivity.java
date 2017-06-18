package history.historylibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText editText;
    private TextView textViewRes;
    private TextView textViewDes;
    private Button buttonGetTest;
    private Button buttonPostTest;
    private String res = "";
    private String des = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edit_text);


        textViewRes = (TextView) findViewById(R.id.tv_res);
        textViewDes = (TextView) findViewById(R.id.tv_des);

        buttonPostTest = (Button) findViewById(R.id.btn_post_test);
        buttonPostTest.setOnClickListener(this);
    }

    public void onClick(View v) {
        String text = editText.getText().toString();

        switch (v.getId()) {
            case R.id.btn_post_test: postTest(text); break;
            default: break;
        }
    }


    // POST
    private void postTest(String text) {
        MediaType MIMEType= MediaType.parse("application/text; charset=utf-8");
        RequestBody body = RequestBody.create(MIMEType, text);


        Request request = new Request.Builder()
                .url("http://mlearning.ga/api/v1/cards/search")       // HTTPアクセス POST送信 テスト確認用ページ
                .post(body)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failMessage();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                res = response.body().string();
                runOnUiThread(new Runnable() {
                    public void run() {
                        textViewRes.setText(res);
                        textViewDes.setText("No Data");
                    }
                });
            }
        });
    }

    private void failMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                textViewRes.setText("onFailure");
                textViewDes.setText("No Data");
            }
        });
    }
}
