package com.suek.ex33threadnetimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv= findViewById(R.id.iv);
    }





    public void clickBtn(View view) {
        //네트워크 작업을 하려면 반드시 internet 사용에 대한 허가(permission)을 받아야함!!!!!!!!!!!!----> Android Manifest.xml 에서
        //네트워크상에 있는 이미지 읽어와서 ImageView 에 보여주기!!
        //안드로이드에서는 Main Thread 는 네트워크 작업을 할 수 없음
        // 1) 네트워크 작업을 하는 별도의 Thread 가 있어야만함.
        Thread t= new Thread(){         //상속받지 않고 사용가능
            @Override
            public void run() {
                //network 에 있는 이미지 주소
                String imgUrl="https://s3.amazonaws.com/cdn-origin-etr.akc.org/wp-content/uploads/2017/11/12234558/Chinook-On-White-03.jpg";


                //파일까지 연결되는 무지개로드(Stream)을 만들어주는 해임달(URL)객체생성
                try {
                    URL url= new URL(imgUrl);

                    //해임달에게 무지개로드 열어달라고...
                    InputStream is= url.openStream();

                    //무지개로드를 통해 이미지데이터를 읽어와서
                    //안드로이드에서 이미지를 관리하는 객체인 Bitmap 객체로 만들기
                    final Bitmap bm= BitmapFactory.decodeStream(is);

                    //그 Bitmap 객체를 이미지뷰에 설정
                    //이미지 변경은 화면이 변경되는 것이고..
                    //별도의 스레드는 화면병경을 할 수 없음..
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(bm);
                        }
                    });



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();

    }


}
