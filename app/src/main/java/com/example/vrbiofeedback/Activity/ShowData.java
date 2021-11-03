package com.example.vrbiofeedback.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vrbiofeedback.Globals.GlobalBluetoothDevice;
import com.example.vrbiofeedback.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.BodyDataType;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class ShowData extends AppCompatActivity {
    private Button btn_back;
    public static TgStreamReader tgStreamReader;
    // TODO connection sdk
    public static BluetoothAdapter mBluetoothAdapter;
    public static BluetoothDevice mBluetoothDevice;
    public static BluetoothDevice bd;
    public static int currentState = 0;

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

    private LineChart mLineChart;
//    private Data[] mDatas;
    private List<Entry> mEntries = new ArrayList<>();

    private Thread mThread;
    private Handler mHandler;
    private Random mRandom;
    private Integer dataNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        mLineChart = findViewById(R.id.heart_rate_chart);
        dataNum = 0;
        findElement();
        listenElement();
        ConnectToBlueTooth();
    }

    public void ConnectToBlueTooth(){
        GlobalBluetoothDevice t = ((GlobalBluetoothDevice) getApplication());
        bd = t.getBluetoothDevice();
        if (bd.equals(null)) {
            Toast.makeText(ShowData.this, "null!", Toast.LENGTH_LONG);
        }
        else {
            createStreamReader(bd);
            tgStreamReader.connectAndStart();
        }
    }

    public void createStreamReader(BluetoothDevice bd){

        if(tgStreamReader == null){
            tgStreamReader = new TgStreamReader(bd,callback);
            tgStreamReader.startLog();
        }else{
            tgStreamReader.setTgStreamHandler(callback);
        }
    }

    public TgStreamHandler callback = new TgStreamHandler() {

        @Override
        public void onStatesChanged(int connectionStates) {
            // TODO Auto-generated method stub
            Log.d("AutoConnect", "connectionStates change to: " + connectionStates);
            currentState  = connectionStates;
            switch (connectionStates) {
                case ConnectionStates.STATE_CONNECTED:
                    System.out.println("Connected");
                    Log.v("states","Connected");
                    //获取当前时间
                    break;
                case ConnectionStates.STATE_WORKING:

                    Log.v("states","Working");
                    System.out.println("Subscribe");
                    //每次进入工作状态时将前一次的记录清空
                    LinkDetectedHandler.sendEmptyMessageDelayed(1234, 5000);
                    break;
                case ConnectionStates.STATE_GET_DATA_TIME_OUT:
                    //get data time out
                    Log.v("states","STATE_GET_DATA_TIME_OUT");
                    break;
                case ConnectionStates.STATE_COMPLETE:
                    //read file complete
                    Log.v("states","Complete");
                    break;
                case ConnectionStates.STATE_STOPPED:
                    Log.v("states","Unsubscribe");
                    System.out.println("Unsubscribe");
                    break;
                case ConnectionStates.STATE_DISCONNECTED:
                    Log.v("states","Disconnect");
                    System.out.println("Disconnect");

                    break;
                case ConnectionStates.STATE_ERROR:
                    Log.v("states","Error");
                    Log.d("AutoConnect","Connect error, Please try again!");
                    break;
                case ConnectionStates.STATE_FAILED:
                    Log.v("states","Failed");
                    Log.d("AutoConnect","Connect failed, Please try again!");
                    break;
            }
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = MSG_UPDATE_STATE;
            msg.arg1 = connectionStates;
            LinkDetectedHandler.sendMessage(msg);

        }

        @Override
        public void onRecordFail(int a) {
            // TODO Auto-generated method stub
            Log.e("AutoConnect","onRecordFail: " +a);

        }

        @Override
        public void onChecksumFail(byte[] payload, int length, int checksum) {
            // TODO Auto-generated method stub
            badPacketCount ++;
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = MSG_UPDATE_BAD_PACKET;
            msg.arg1 = badPacketCount;
            LinkDetectedHandler.sendMessage(msg);

        }

        @Override
        public void onDataReceived(int datatype, int data, Object obj) {
            // TODO Auto-generated method stub
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = datatype;
            msg.arg1 = data;
            msg.obj = obj;
            LinkDetectedHandler.sendMessage(msg);
        }

    };

    @SuppressLint("HandlerLeak")
    public Handler LinkDetectedHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

//            System.out.println(msg.what);

//            if(msg.arg1==100&&flag==0){
//                image_connect.setImageResource(R.drawable.incorrect);
//                text_connect.setText("Can not connect to BMD101");
//            }
//            if(msg.arg1==2){
//                flag = 1;
//                image_connect.setImageResource(R.drawable.right);
//                text_connect.setText("Connected to BMD101");
//                btn_next.setVisibility(0);
//            }
            switch (msg.what) {

//                case BodyDataType.CODE_RAW:
////                    System.out.println(msg.arg1);
//                    break;
                case BodyDataType.CODE_HEATRATE:
                    if (GlobalFlag != 0) {
                        bpm = Integer.toString(msg.arg1);
                        long currentSysTime = Calendar.getInstance().getTimeInMillis();
                        rri = String.valueOf(currentSysTime - preSysTime);
                    }
                    preSysTime = Calendar.getInstance().getTimeInMillis();
                    GlobalFlag = 1;
                    dataNum += 1;
                    System.out.println("CODE_HEATRATE ");
                    System.out.println(bpm);
                    System.out.println(rri);
                    updateChart(msg);
                    break;

                case BodyDataType.CODE_POOR_SIGNAL:
                    System.out.println("CODE_POOR_SIGNAL");

                    break;
                case MSG_UPDATE_BAD_PACKET:
                    System.out.println("MSG_UPDATE_BAD_PACKET");
                    break;

                case MSG_UPDATE_STATE:
                    System.out.println("MSG_UPDATE_STATE");
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void updateChart(Message msg){
        mEntries.add(new Entry(dataNum,msg.arg1));
        LineDataSet dataSet = new LineDataSet(mEntries,"number");
        LineData lineData = new LineData(dataSet);
        mLineChart.setData(lineData);
        mLineChart.invalidate();
    }
    private void listenElement() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowData.this,BlueToothConnect.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findElement() {
        btn_back = findViewById(R.id.btn_back);
    }
}