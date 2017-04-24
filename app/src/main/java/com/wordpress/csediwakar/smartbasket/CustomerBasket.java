package com.wordpress.csediwakar.smartbasket;

import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.bluetooth.BluetoothSocket;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
        import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
        import android.widget.Toast;

        import java.io.InputStream;
        import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
        import java.util.UUID;

public class CustomerBasket extends AppCompatActivity {
    private final String DEVICE_ADDRESS = "98:D3:31:40:3C:19";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    ImageButton startButton;
    TextView textView;
    EditText editText;
    boolean deviceConnected = false;
    Thread thread;
    byte buffer[];
    int bufferPosition;
    boolean stopThread;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private ListView mainListView ;
    //private ArrayAdapter<String> listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_basket);
        startButton = (ImageButton) findViewById(R.id.im1);
        mainListView = (ListView) findViewById( R.id.lt1);
        String[] baskets = new String[] { "Basket-1", "Basket-2", "Basket-3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, baskets);

        mainListView.setAdapter(adapter);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(CustomerBasket.this,DataReceive.class));

            }
        });
    }


    public boolean BTinit() {
        boolean found = false;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Device doesnt Support Bluetooth", Toast.LENGTH_SHORT).show();
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                //Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if (bondedDevices.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Pair the Device first", Toast.LENGTH_SHORT).show();
        } else {
            for (BluetoothDevice iterator : bondedDevices) {
                if (iterator.getAddress().equals(DEVICE_ADDRESS)) {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    public void onClickStart(View view) {
        if (BTinit()) {


            //setUiEnabled(true);
            deviceConnected = true;
            Toast.makeText(this, "Final Connection", Toast.LENGTH_SHORT).show();
            Intent o = new Intent(CustomerBasket.this, DataReceive.class);
            startActivity(o);


        }
    }

}

