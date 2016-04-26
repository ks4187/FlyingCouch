package com.tir.flyingcouch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Karan Shah on 4/9/2016.
 */
public class PaymentActivity extends AppCompatActivity {


    double baseCost = 0, perMile = 0, totalCost = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        int serviceSelected = getIntent().getIntExtra("serviceSelected", 0);
        double distance = getIntent().getDoubleExtra("distance", 0);

        TextView setBaseCost = (TextView) findViewById(R.id.baseCostValue);
        TextView setPerMileCost = (TextView) findViewById(R.id.perMileCostValue);
        perMile = distance * 2.00;
        setPerMileCost.setText("$" + round(perMile, 2));
        if (serviceSelected == 1) {
            baseCost = 30.00;
            setBaseCost.setText("$" + round(baseCost, 2));
        } else if (serviceSelected == 2) {
            baseCost = 50.00;
            setBaseCost.setText("$" + round(baseCost, 2));
        } else if (serviceSelected == 0) {
            setBaseCost.setText("ERROR");
            Toast.makeText(PaymentActivity.this, "ERROR ENCOUNTERED", Toast.LENGTH_SHORT).show();
        }

        final TextView setTotalCost = (TextView) findViewById(R.id.totalCostValue);
        CheckBox flexCheck = (CheckBox) findViewById(R.id.flexibleCheck);

        if(flexCheck.isChecked()){
            totalCost = (baseCost + perMile) - (baseCost + perMile) * 0.3;
            setTotalCost.setText("$" + round(totalCost, 2));
        }

        flexCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalCost = (baseCost + perMile) - (baseCost + perMile) * 0.3;
                    setTotalCost.setText("$" + round(totalCost, 2));
                } else {
                    totalCost = baseCost + perMile;
                    setTotalCost.setText("$" + round(totalCost, 2));
                }
            }
        });

        Button toNextPage = (Button) findViewById(R.id.checkoutButton);

        toNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, AdditionalActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("totalCost", totalCost);
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
