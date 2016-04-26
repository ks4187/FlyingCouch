package com.tir.flyingcouch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Karan Shah on 4/23/2016.
 */
public class AdditionalActivity extends AppCompatActivity {

    double finalCost = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional);

        finalCost = getIntent().getDoubleExtra("totalCost", 0);
        final TextView setTotalCost = (TextView) findViewById(R.id.finalTotalCostValue);
        setTotalCost.setText("$" + round(finalCost, 2));

        CheckBox moverCheck = (CheckBox) findViewById(R.id.moverText);
        CheckBox assemblyCheck = (CheckBox) findViewById(R.id.assemblyText);

        moverCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    finalCost = finalCost + 50;
                    setTotalCost.setText("$" + round(finalCost, 2));
                } else {
                    finalCost = finalCost - 50;
                    setTotalCost.setText("$" + round(finalCost, 2));
                }
            }
        });

        assemblyCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    finalCost = finalCost + 50;
                    setTotalCost.setText("$" + round(finalCost, 2));
                } else {
                    finalCost = finalCost - 50;
                    setTotalCost.setText("$" + round(finalCost, 2));
                }
            }
        });


        Button toNextPage = (Button) findViewById(R.id.finalCheckoutButton);
        toNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdditionalActivity.this, DriverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
