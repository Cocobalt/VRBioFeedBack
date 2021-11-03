package com.example.vrbiofeedback.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vrbiofeedback.Globals.GlobalBluetoothDevice;
import com.example.vrbiofeedback.R;
import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.BodyDataType;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;

import java.util.Calendar;
import java.util.Set;

public class BlueToothConnect extends AppCompatActivity {

    private Button btn_back;
    private Button btn_next;
    private TextView text_connect;
    private ImageView image_connect;

//蓝牙连接部分
    public static TgStreamReader tgStreamReader;
    // TODO connection sdk
    public static BluetoothAdapter mBluetoothAdapter;
    public static BluetoothDevice mBluetoothDevice;
    public static int currentState = 0;
    private static boolean ifConnected = false;

    private static final int MSG_UPDATE_BAD_PACKET = 1001;
    private static final int MSG_UPDATE_STATE = 1002;
    private static final int MSG_CONNECT = 1003;

    public static String deviceName = null;
    public static int badPacketCount = 0;
    public static int GlobalFlag=0;
    public static long preSysTime=0;
    public static String rri = "0";
    public static String bpm="0";
    public int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth_connect);

        findElement();
        listenElement();

        if(SearchDevice()){
            ConnectToBlueTooth();
            image_connect.setImageResource(R.drawable.right);
            text_connect.setText("Find Device BMD101!");
            btn_next.setVisibility(View.VISIBLE);
        }
        else{
            image_connect.setImageResource(R.drawable.incorrect);
            text_connect.setText("Please first pair BT04-A with the mobile phone");
        }

    }

    public boolean SearchDevice() {

        try {
            // TODO
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                System.out.println("Please enable your Bluetooth and re-run this program !");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("AutoConnect", "error:" + e.getMessage());
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        for(BluetoothDevice device: pairedDevices){
            if(device.getName().equals("BT04-A")){
                mBluetoothDevice=device;
               deviceName = "BT04-A";
               ifConnected = true;
               return true;
            }
        }
        return false;
    }

    public void ConnectToBlueTooth() {

        BluetoothDevice bd = mBluetoothAdapter.getRemoteDevice(mBluetoothDevice.getAddress());
        GlobalBluetoothDevice d = (GlobalBluetoothDevice) getApplication();
        d.setBluetoothDevice(bd);
    }

    private void listenElement() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BlueToothConnect.this,SetUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ifConnected) {
                    Intent intent = new Intent(BlueToothConnect.this, ShowData.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void findElement() {
        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);
        text_connect = findViewById(R.id.text_connect);
        image_connect = findViewById(R.id.image_connect);
    }
}