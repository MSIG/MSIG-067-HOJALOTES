package com.msig.clasificacionlotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.msig.config.Servidor;
import com.msig.config.Sistema;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfigActivity extends AppCompatActivity {

    private Servidor sr = new Servidor(this);
    private Sistema si = new Sistema(this);
    @Bind(R.id.txt_config_server) EditText txt_config_server;
    @Bind(R.id.txt_config_sistem) EditText txt_config_system;
    @Bind(R.id.btn_config_save) Button btn_config_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        ButterKnife.bind(this);

        //show actual IP server
        TextView txtIpServer = (TextView) findViewById(R.id.txt_config_server);
        if(sr.getServer() != null){
            txtIpServer.setText(sr.getServer().toString());
        }
        //show actual device name
        TextView txtDeviceName = (TextView) findViewById(R.id.txt_config_sistem);
        if(si.getSistem() != null){
            txtDeviceName.setText(si.getSistem().toString());
        }
    }

    @OnClick(R.id.btn_config_save)
    public void configuarar(){
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        TextView txtServer = (TextView) findViewById(R.id.txt_config_server);
        TextView txtSystem = (TextView) findViewById(R.id.txt_config_sistem);
        sr.setServer(txtServer.getText().toString());
        si.setSistem(txtSystem.getText().toString());
        startActivity(main);
        finish();
    }
}
