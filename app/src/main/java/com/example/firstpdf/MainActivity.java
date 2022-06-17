package com.example.firstpdf;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button button;

    Bitmap bmp, Scaledbmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.certificate);
        Scaledbmp = Bitmap.createScaledBitmap(bmp,2000,1414,false);
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        createPDF();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    private void createPDF(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for create pdf
                PdfDocument newpdf = new PdfDocument();
                Paint mypaint = new Paint();
                PdfDocument.PageInfo mypageinfo = new PdfDocument.PageInfo.Builder(2000,1414,1).create();
                PdfDocument.Page mypage = newpdf.startPage(mypageinfo);

                Canvas canvas = mypage.getCanvas();
                canvas.drawBitmap(Scaledbmp,0,0,mypaint);

                String name = "Robin Mark Adinson";
                String product = "Electical Car";
                //Edittext
                int amount = 500000;
                float equity = 2.5f;
                String pitcher = "rohitrg1522@gmail.com";
                String Sharkname = "Ashneer Grover";
                //Writing text from given Strings.
                mypaint.setTextSize(60f);
                canvas.drawText(name,960,493,mypaint);

                mypaint.setTextSize(50f);
                canvas.drawText(String.valueOf(amount),900,871,mypaint);

                mypaint.setTextSize(60f);
                canvas.drawText(product,700,725,mypaint);

                mypaint.setTextSize(50f);
                canvas.drawText(String.valueOf(equity),1320,871,mypaint);

                mypaint.setTextSize(60f);
                canvas.drawText(Sharkname,1150,1195,mypaint);
                newpdf.finishPage(mypage);

                File file = new File(Environment.getExternalStorageDirectory(),"Download/vShark Certificate.pdf");
                try {
                    newpdf.writeTo(new FileOutputStream(file));
                }catch (IOException e){
                    e.printStackTrace();
                }
                newpdf.close();
                Toast.makeText(MainActivity.this, "PDF created", Toast.LENGTH_SHORT).show();
                //pdf created
                //code for sending mail
                Intent intentshare = new Intent(Intent.ACTION_SEND);
                intentshare.setType("application/pdf");
                intentshare.putExtra(Intent.EXTRA_EMAIL,pitcher);
                intentshare.putExtra(Intent.EXTRA_SUBJECT,"vShark Certificate");
                intentshare.putExtra(Intent.EXTRA_TEXT, "looking forward to work with you");

                startActivity(Intent.createChooser(intentshare, "Sending mail"));

            }
        });
    }
}