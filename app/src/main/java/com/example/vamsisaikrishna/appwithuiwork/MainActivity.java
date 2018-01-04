package com.example.vamsisaikrishna.appwithuiwork;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bttn1;
    Button bttn2;
    TextView txt;
    private int count=0;
    Handler handler;
    private boolean mstopLoop;
    CustomHandlerThread mHandlerThread;
    TextView txt_finalCount;
    EditText edtxt;
    MyAsync myAsync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bttn1 = (Button)findViewById(R.id.start_button);
        bttn2 = (Button)findViewById(R.id.stop_button);
        txt = (TextView)findViewById(R.id.txt);
        txt_finalCount = (TextView)findViewById(R.id.finalcount_txt);
        edtxt =(EditText)findViewById(R.id.edtxt);
        bttn1.setOnClickListener(this);
        bttn2.setOnClickListener(this);
        Log.e(MainActivity.class.getSimpleName(),String.valueOf(Thread.currentThread().getId()));
        handler = new Handler(getApplicationContext().getMainLooper());
        mHandlerThread = new CustomHandlerThread("CustomHandlerThread");
        mHandlerThread.start();
        myAsync = new MyAsync();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_button:

                mstopLoop=true;

                myAsync.execute(count);
               // customHandlerThread();
                txt.setVisibility(View.VISIBLE);

               /* new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(mstopLoop){
                            try {
                                Thread.sleep(1000);
                                count++;
                            } catch (InterruptedException e) {
                               Log.e(MainActivity.class.getSimpleName(),e.getMessage());
                            }

                            Log.e(MainActivity.class.getSimpleName(),String.valueOf(Thread.currentThread().getId())+ " : "+count);
                          *//* handler.post(new Runnable() {
                               @Override
                               public void run() {
                                   txt.setText(String.valueOf(count));
                               }
                           });*//*
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txt.setText(String.valueOf(count));
                                }
                            });
                            *//*txt.post(new Runnable() {
                                @Override
                                public void run() {
                                    txt.setText(String.valueOf(count));
                                }
                            });*//*
                        }
                    }
                }).start();*/

                break;
            case R.id.stop_button:
                mstopLoop=false;

                txt_finalCount.setText(String.valueOf(count));
                txt_finalCount.setVisibility(View.VISIBLE);
                txt.setVisibility(View.GONE);
                break;
        }

    }

    private void customHandlerThread() {
        mHandlerThread.handler.post(new Runnable() {
            @Override
            public void run() {
                while(mstopLoop){
                    try {
                        Log.e(MainActivity.class.getSimpleName(),"thread id that sends message:" +Thread.currentThread().getId());
                        Thread.sleep(1000);
                        count++;
                        Message message =new Message();
                        message.obj =""+count;
                        mHandlerThread.handler.sendMessage(message);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txt.setText(String.valueOf(count));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        });
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                while(mstopLoop){
                    try {
                        Log.e(MainActivity.class.getSimpleName(),"thread id that sends message:" +Thread.currentThread().getId());
                        Thread.sleep(1000);
                        count++;
                        Message message =new Message();
                        message.obj =""+count;
                        mHandlerThread.handler.sendMessage(message);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txt.setText(String.valueOf(count));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHandlerThread!=null){
            mHandlerThread.getLooper().quit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("finalCount",(count));
        outState.putString("txtHolder",edtxt.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        txt_finalCount.setText( ""+savedInstanceState.getInt("finalCount"));
        edtxt.setText(savedInstanceState.getString("txtHolder"));
       // txt_finalCount.setVisibility(View.VISIBLE);
    }
private class MyAsync extends AsyncTask<Integer,Integer, Integer>{

    private int count;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        count =0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        txt.setText(""+integer);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        txt.setText(""+ values[0]);

    }

    @Override
    protected Integer doInBackground(Integer... params) {
        while(mstopLoop){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            publishProgress(count);

        }return count;
    }
}
}
