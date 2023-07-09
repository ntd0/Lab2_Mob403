package com.example.lab2.Bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab2.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Bai1Activity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtScore;
    private Button btnSend;
    private TextView tvResult;
    private AsyncTask<String, Integer, String> asyncTask;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);

        edtName = (EditText) findViewById(R.id.edt_name);
        edtScore = (EditText) findViewById(R.id.edt_score);
        btnSend = (Button) findViewById(R.id.btn_send);
        tvResult = (TextView) findViewById(R.id.tv_result);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Lab2-B1");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        btnSend.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String score = edtScore.getText().toString();
            if (name.length() == 0) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                edtName.requestFocus();
                return;
            }
            if (score.length() == 0) {
                Toast.makeText(this, "Please enter your score", Toast.LENGTH_SHORT).show();
                edtScore.requestFocus();
                return;
            }

            String URL_SEVER = "http://192.168.1.43/dungnt_ph26817/student_GET.php";
            String query = "?name=" + name + "&score=" + score;

            progressDialog.show();
            asyncTask = new AsyncTask<String, Integer, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    String line;
                    StringBuffer buffer = null;
                    try {
                        URL url = new URL(strings[0]);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        int responseCode = connection.getResponseCode();
                        if (responseCode == connection.HTTP_OK) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            buffer = new StringBuffer();
                            while ((line = reader.readLine()) != null) {
                                buffer.append(line);
                            }
                            reader.close();
                            connection.disconnect();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return buffer.toString();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);

                    progressDialog.dismiss();
                    tvResult.setText(s);
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                }
            };
            asyncTask.execute(URL_SEVER + query);
        });
    }
}