package com.tir.flyingcouch;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by Karan Shah on 4/1/2016.
 */
public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    TextView sourceSelect;
    String sourceSelText;
    String furnitureSelect;

    double ikeaLat = 40.672189, ikeaLng = -74.011347;
    double walmartLat = 40.792984, walmartLng = -74.042466;
    double homeLat = 40.741850, homeLng = -73.991420;
    double abcLat = 40.738094, abcLng = -73.989660;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_destination);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        spinner = (Spinner) findViewById(R.id.storeListDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.storeList, R.layout.spinner_select);
        adapter.setDropDownViewResource(R.layout.spinner_select);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Button toServicePage = (Button) findViewById(R.id.addressSubmitButton);
        toServicePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    hideSoftKeyboard(v);
                    sourceSelText = spinner.getSelectedItem().toString();
                    EditText firstLineToText = (EditText) findViewById(R.id.destinationFirstLine);
                    String firstLineCheck = firstLineToText.getText().toString();
                    EditText zipToText = (EditText) findViewById(R.id.destinationZip);
                    String zipCheck = zipToText.getText().toString();
                    EditText sourceAddress = (EditText) findViewById(R.id.sourceAddress);
                    String sourceAdd = sourceAddress.getText().toString();
                    furnitureSelect = spinner.getSelectedItem().toString();
                    RadioButton vanSelect = (RadioButton) findViewById(R.id.vanRadioButton);
                    RadioButton truckSelect = (RadioButton) findViewById(R.id.truckRadioButton);

                    if (sourceSelText.equals("Select Store")) {
                        Toast.makeText(SearchActivity.this, "Please Select Your Store", Toast.LENGTH_SHORT).show();
                    } else if (sourceSelText.equals("-Enter Location-") && sourceAdd.equals("")) {
                        Toast.makeText(SearchActivity.this, "Please Enter Pickup Location", Toast.LENGTH_SHORT).show();
                    } else if (firstLineCheck.equals("")) {
                        Toast.makeText(SearchActivity.this, "Please Enter First Line Address", Toast.LENGTH_SHORT).show();
                    } else if (zipCheck.equals("")) {
                        Toast.makeText(SearchActivity.this, "Please Enter Your Zip Code", Toast.LENGTH_SHORT).show();
                    } else {
                        EditText et = (EditText) findViewById(R.id.destinationFirstLine);
                        String location = et.getText().toString();

                        Geocoder gc = new Geocoder(SearchActivity.this);
                        List<Address> list = gc.getFromLocationName(location, 1);
                        Address add = list.get(0);
                        double lat = add.getLatitude();
                        double lng = add.getLongitude();
                        LatLng from = new LatLng(lat, lng);
                        if (sourceSelText.equals("IKEA Brooklyn")) {
                            from = new LatLng(ikeaLat, ikeaLng);
                        } else if (sourceSelText.equals("Walmart")) {
                            from = new LatLng(walmartLat, walmartLng);
                        } else if (sourceSelText.equals("Home Depot")) {
                            from = new LatLng(homeLat, homeLng);
                        } else if (sourceSelText.equals("ABC Carpet & Home")) {
                            from = new LatLng(abcLat, abcLng);
                        } else if (sourceSelText.equals("-Enter Location-")) {
                            List<Address> sourceList = gc.getFromLocationName(sourceAdd, 1);
                            Address sourceAddr = sourceList.get(0);
                            double sourceLat = sourceAddr.getLatitude();
                            double sourceLng = add.getLongitude();
                            from = new LatLng(sourceLat, sourceLng);
                        }

                        LatLng to = new LatLng(lat, lng);
                        double distance = SphericalUtil.computeDistanceBetween(from, to);
                        String showText = "Distance: " + Math.round((distance * 0.000621) * 100.0) / 100.0 + " Mi";
                        Toast.makeText(SearchActivity.this, showText, Toast.LENGTH_SHORT).show();
                        int serviceSelected = 1;
                        if(vanSelect.isChecked()){
                            serviceSelected = 1;
                        } else if(truckSelect.isChecked()){
                            serviceSelected = 2;
                        }

                        Intent intent = new Intent(SearchActivity.this, PaymentActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("serviceSelected", serviceSelected);
                        intent.putExtra("distance", Math.round((distance * 0.000621) * 100.0) / 100.0);

                        startActivity(intent);
                    }


                } catch (IOException ex) {
                    Toast.makeText(SearchActivity.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void hideSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sourceSelect = (TextView) view;
        if (sourceSelect.getText().equals("-Enter Location-")) {
            EditText sourceAdd = (EditText) findViewById(R.id.sourceAddress);
            sourceAdd.setVisibility(view.VISIBLE);
        } else {
            EditText sourceAdd = (EditText) findViewById(R.id.sourceAddress);
            sourceAdd.setVisibility(view.INVISIBLE);
            sourceAdd.setText("");
        }
        //Toast.makeText(SearchActivity.this, "You Selected: " + sourceSelect.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
