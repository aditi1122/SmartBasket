package com.wordpress.csediwakar.smartbasket;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.BufferedInputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.nio.charset.StandardCharsets;
        import java.util.ArrayList;
        import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
        import java.util.Set;
        import java.util.UUID;
        import java.util.Arrays;

public class DataReceive extends AppCompatActivity {

    private final String DEVICE_ADDRESS = "98:D3:32:70:9D:EC";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    boolean stopThread;
    byte buffer[];
    boolean deviceConnected = false;
    private InputStream inputStream;
    private TextView t1;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    final String result="";
    String str="";
    Thread time;
    BufferedInputStream bis = null;
    String ans="";
    String name ="Jacket-1";
    String name1="Jacket-2";

    String s1="";
    ArrayList<String> singleAddress = new ArrayList<String>();
    ListView l1;
    private ArrayAdapter<String> listAdapter ;
    Hashtable table = new Hashtable();
    int i=0;
    int k=0;
    TextView totalcost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_receive);
        t1 = (TextView) findViewById(R.id.tr);
        Button B = (Button) findViewById(R.id.b1);
        listAdapter = new ArrayAdapter<String>(this,R.layout.simplerow,singleAddress);
        l1=(ListView)findViewById(R.id.lst);
        totalcost =(TextView) findViewById(R.id.totalc);
        table.put("","");
        Button b=(Button) findViewById(R.id.Pay);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(DataReceive.this,PaymentGateway.class);
                startActivity(i);
            }
        });
        if (BTinit()) {
            if (BTconnect()) {
                //setUiEnabled(true);
                deviceConnected = true;
                Toast.makeText(this, "Connection made", Toast.LENGTH_SHORT).show();
                beginListenForData();
                B.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        t1.setText("");
                    }
                });

            }

        }


    }



    void beginListenForData() {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        final Thread t11,t21;
        t11 = new Thread(new Runnable()
        {

            public void run()
            {
                while (!Thread.currentThread().isInterrupted() && !stopThread )
                {
                    try {
                        final int byteCount = inputStream.available();
                        if (byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);

                            str = new String(rawBytes, "UTF-8");

                            //result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                            //t2.append(s1);
                            handler.post(new Runnable()
                            {
                                public void run()
                                {

                                    t1.append(str +"\n");
                                /*    Enumeration e = table.keys();
                                    if(singleAddress.contains(str))
                                    {
                                       while(e.hasMoreElements())
                                       {
                                           String key= (String) e.nextElement();

                                       }

                                    }
                                    else
                                    {
                                        singleAddress.add(str+ "\n");
                                    }*/
                                    String s=str;
                                    if(s.equals("54BD465"))
                                    {
                                        //String name ="Jacket-1";
                                        singleAddress.add(name);
                                    }
                                    if(s.equals("F89B2"))
                                    {
                                        //String name1="Jacket-2";
                                        singleAddress.add(name1);
                                    }


                                    test();

                                }
                            });
                           // System.out.print("----2222-----");
                        }

                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                };
            }
        });


        t11.start();

    }
    public void test()
    {
        Iterator it=singleAddress.iterator();
        int key=100;
        int card=20;
        //Iterator it=singleAddress.iterator();
        //listAdapter = new ArrayAdapter<String>(this,R.layout.simplerow,singleAddress);
        while(it.hasNext())
        {
            l1.setAdapter(listAdapter);
            //t2.setText(""+ it.next());
            //System.out.println("--------000000000000-----------");
            //System.out.println(it.next());
            if(it.next().equals("54BD465"))
            {
                System.out.println(Collections.frequency(singleAddress,"54BD465"));
            }
            //
            totalcost.setText("Total Money=" +(Collections.frequency(singleAddress,name)*key + Collections.frequency(singleAddress,name1)*card));

        }
    }

    public void cost()
    {

    }

    public boolean BTconnect()
    {
        boolean connected = true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }
        if (connected) {

            try {
                inputStream = socket.getInputStream();
                Toast.makeText(this, "U are in socket", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return connected;
    }

    public boolean BTinit() {
        boolean found = false;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
}

