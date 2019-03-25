package team.har.busqr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.view.View;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DropActivity extends AppCompatActivity{

    public TextView showBus;
    public  Spinner spinner1,spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        spinner1 = (Spinner) findViewById(R.id.drop1);
        spinner2 = (Spinner) findViewById(R.id.drop2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("busdetails");
        initializeViews();
        Intent in = getIntent();
        Bundle b = in.getExtras();

        String busnoString = b.getString("busno");
        showBus.setText("Bus No : " + busnoString);

        myRef.child(busnoString).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> nomeConsulta = new ArrayList<String>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String consultaName = areaSnapshot.getValue(String.class);
                    nomeConsulta.add(consultaName);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DropActivity.this, R.layout.spinner_item, nomeConsulta);

                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(arrayAdapter);
                spinner2.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        findViewById(R.id.buttonPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DropActivity.this, checksum.class);


                String amnt = generateValue();
                String custID = generateString();
                String orderID = generateString();
                intent.putExtra("orderid", orderID);
                intent.putExtra("custid", custID);
                intent.putExtra("amount",amnt);
                startActivity(intent);


            }

        });
        if (ContextCompat.checkSelfPermission(DropActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DropActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
    }


            private void initializeViews() {
                showBus = (TextView) findViewById(R.id.showBus);
            }

    private String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
    private String generateValue()
    {
        Random r = new Random();

        StringBuffer temp = new StringBuffer();
        int i1 = r.nextInt(25 - 4) + 4;
        temp.append(String.valueOf(i1));
        Toast.makeText(this, "Amount to be paid: "+temp, Toast.LENGTH_LONG).show();
        return String.valueOf(temp);


    }
}