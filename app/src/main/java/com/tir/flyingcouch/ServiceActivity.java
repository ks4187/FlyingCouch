package com.tir.flyingcouch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Karan Shah on 4/3/2016.
 */
public class ServiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    TextView serviceSelect;
    String furnitureSelect;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_service);

        spinner = (Spinner) findViewById(R.id.numberDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numberList, R.layout.spinner_select);
        adapter.setDropDownViewResource(R.layout.spinner_select);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Change Button Name To Reflect Its Use
        Button toNextPage = (Button) findViewById(R.id.serviceNext);
        toNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                furnitureSelect = spinner.getSelectedItem().toString();
                RadioButton vanSelect = (RadioButton) findViewById(R.id.vanRadioButton);
                RadioButton truckSelect = (RadioButton) findViewById(R.id.truckRadioButton);

                if (furnitureSelect.equals("-")) {
                    Toast.makeText(ServiceActivity.this, "Please select the number of Furniture Items", Toast.LENGTH_LONG).show();
                } else if (vanSelect.isChecked() && furnitureSelect.equals("5+")) {
                    Toast.makeText(ServiceActivity.this, "Select a Truck or reduce number of Furniture Items", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(ServiceActivity.this, MainActivity.class);
                    //startActivity(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        serviceSelect = (TextView) view;
        //Toast.makeText(ServiceActivity.this, "You Selected: " + serviceSelect.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
