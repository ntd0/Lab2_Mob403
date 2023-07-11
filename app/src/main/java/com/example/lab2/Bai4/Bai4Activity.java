package com.example.lab2.Bai4;

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
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Bai4Activity extends AppCompatActivity {
    private EditText edtA;
    private EditText edtB;
    private EditText edtC;
    private Button btnSend;
    private TextView tvResult;
    private AsyncTask<String, Integer, String> asyncTask;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai4);

        edtA = (EditText) findViewById(R.id.edt_a);
        edtB = (EditText) findViewById(R.id.edt_b);
        edtC = (EditText) findViewById(R.id.edt_c);
        btnSend = (Button) findViewById(R.id.btn_send);
        tvResult = (TextView) findViewById(R.id.tv_result);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Lab2-B4");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        btnSend.setOnClickListener(v -> {
            String a = edtA.getText().toString();
            String b = edtB.getText().toString();
            String c = edtC.getText().toString();

            if (a.length() == 0) {
                Toast.makeText(this, "Please enter your number", Toast.LENGTH_SHORT).show();
                edtA.requestFocus();
                return;
            }

            if (b.length() == 0) {
                Toast.makeText(this, "Please enter your number", Toast.LENGTH_SHORT).show();
                edtB.requestFocus();
                return;
            }

            if (c.length() == 0) {
                Toast.makeText(this, "Please enter your number", Toast.LENGTH_SHORT).show();
                edtC.requestFocus();
                return;
            }

            String URL_SEVER = "http://192.168.1.43/dungnt_ph26817/giaiphuongtrinh_POST.php";

            progressDialog.show();
            asyncTask = new AsyncTask<String, Integer, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    String line;
                    StringBuffer buffer = null;
                    try {
                        String param = "a=" + URLEncoder.encode(a, "utf-8")
                                + "&b=" + URLEncoder.encode(b, "utf-8")
                                + "&c=" + URLEncoder.encode(c, "utf-8");
                        URL url = new URL(strings[0]);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoOutput(true);
                        connection.setRequestMethod("POST");
                        connection.setFixedLengthStreamingMode(param.getBytes().length);
                        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                        PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
                        printWriter.print(param);
                        printWriter.close();

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
            asyncTask.execute(URL_SEVER);
        });
    }
}