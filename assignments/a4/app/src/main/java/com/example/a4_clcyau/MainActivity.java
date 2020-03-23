package com.example.a4_clcyau;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private DummyView dummyView;
    private Button infoButton;
    private Button resetButton;
    private AlertDialog infoAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dummyView = findViewById(R.id.dummy_view);
        infoButton = findViewById(R.id.info_button);
        resetButton = findViewById(R.id.reset_button);

        setInfoAlert();
        setResetButton();
    }

    private void setResetButton(){
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dummyView.setDummy();
            }
        });
    }

    private void setInfoAlert(){
        infoAlert = new AlertDialog.Builder(this).create();
        infoAlert.setTitle("a4_clcyau");
        infoAlert.setMessage("Celine Yau - 20610891");
        infoAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoAlert.show();
            }
        });
    }
}
