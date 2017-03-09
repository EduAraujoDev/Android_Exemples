package com.eduaraujodev.wsdl_ksoap2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import livroandroid.lib.utils.HttpHelper;
import livroandroid.lib.utils.XMLUtils;

public class MainActivity extends AppCompatActivity {
    String URL = "http://www.w3schools.com/xml/tempconvert.asmx/CelsiusToFahrenheit";

    private EditText etCelcius;
    private EditText etFahrenheit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCelcius = (EditText) findViewById(R.id.etCelcius);
        etFahrenheit = (EditText) findViewById(R.id.etFahrenheit);
        Button btnConverter = (Button) findViewById(R.id.btnConverte);

        btnConverter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        String celcius = etCelcius.getText().toString();

                        // Parâmetros para enviar por post
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Celsius", celcius);

                        try {
                            // Retorno: <string xmlns="http://www.w3schools.com/webservices/">33.8</string>
                            HttpHelper http = new HttpHelper();
                            String s = http.doPost(URL, params, "UTF-8");

                            Element root = XMLUtils.getRoot(s, "UTF-8");

                            // Lê o texto do XML
                            final String fahrenheit = XMLUtils.getText(root);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    etFahrenheit.setText(fahrenheit);
                                }
                            });
                        } catch (IOException e) {
                            Log.e("livroandroid", "Erro: " + e.getMessage(), e);
                        }
                    }
                }.start();
            }
        });
    }
}