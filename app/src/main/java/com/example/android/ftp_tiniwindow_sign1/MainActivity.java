package com.example.android.ftp_tiniwindow_sign1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import android.widget.Toast;

//import com.example.android.sunshine.utilities.NetworkUtils;
//import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;




import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.OutputStream;

import static android.util.Half.NaN;
import static android.util.Half.isNaN;

public class MainActivity extends AppCompatActivity {
    private int lineCount;
    private int lineLength = 10;
    private EditText mSpeed;
    private EditText mTextData;
    private TextView mTextMessage;


    private String server = "107.180.55.10";
    private int port = 21;
    private String user = "Sign1@tiniliteworld.com";
    private String pass = "Sign1";
    private String fileName = "dat/Sign1.data";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextData = findViewById(R.id.et_textdata);
        mSpeed = findViewById(R.id.et_speed);

        mTextMessage = findViewById(R.id.message);

        Get(new View(this));
        //formatText() ;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    void formatText(){
        String mlines = mTextData.getText().toString()  ;
        String [] splitData;
        splitData = mlines.split("\n") ;
        lineCount = splitData.length;
        for (int j = 0; j < splitData.length; j ++ ){
            if (splitData [j].length() > lineLength)  {
                splitData [j] = splitData [j].substring(0, lineLength);
            }
            else if (splitData [j].length() > lineLength) {
                splitData [j] = padRight (splitData[j], " ", lineLength);
            }

        }
        mlines = join( "\n", splitData) ;
        mlines = mlines.toUpperCase();
        mTextData.setText(mlines);
    };

    private String generateFileContents(){
       int speed =  Integer.parseInt(mSpeed.getText().toString()) ;
        formatText();
        String retval = mTextData.getText().toString().replace("\n", "\r\n") +
                "\r\n[trick coding version 2.2]\r\n" +
                "020105010001FF050100" +
                padLeft(Integer.toHexString(lineCount), "0", 2 ) +
                padLeft(Integer.toHexString(speed * 10), "0", 2 ) ;


        retval = retval.toUpperCase();

        return retval;

    };


    public void Send(View view) {

        String [] params = {            //params
                server,                 //0
                "" + port,              //1
                user,                   //2
                pass,                   //3
                fileName,               //4
                generateFileContents()  //5

        };
        new SendDataTask().execute(params);

    }


    public void Get(View view) {

        String [] params = {            //params
                server,                 //0
                "" + port,              //1
                user,                   //2
                pass,                   //3
                fileName               //4


        };
        new GetDataTask().execute(params);

    }

    protected static int pow (int base, int exp )
    {
        int returnval = 1;
        if (exp == 0  && base != 0 ) return 1;
        if (base == 0 && exp != 0 ) return 0;
        if (base == 0 && exp == 0 ) return NaN;
        if (exp < 0) return NaN ;

        for (int i = 0; i <exp; i++)
        {
            returnval = returnval * base;

        }

        return returnval;


    }


    protected static int parseUnsignedInt (String val, int radix )
    {


        char[] chars = val.toLowerCase().toCharArray();
        int returnval = 0;

        if ( radix < 2) return -1;

        for (int i = 0 ; i < val.length() ; i++) {

            if (  chars[i] >= '0'   && chars[i] <= '9'  ) {

                if ( chars[i] - '0' > radix ) return -1;

                int ex = val.length() - i -1;
                returnval = returnval + (chars[i] - '0') * pow ( radix, ex ) ;

            }

            else if (  chars[i] >= 'a'   && chars[i] <= 'z'  ) {

                if ( chars[i] - 'a' + 11 > radix ) return -1;


                returnval = returnval + (chars[i] - 'a' + 11) * pow ( radix, val.length() - i) ;

            }
            else return -1 ;



        }

        return    returnval ;
    }

    protected static String join( String delim, String [] arr){

        String returnValue = "";
        for (int i = 0; i < arr.length; i ++){
            returnValue = returnValue + arr[i];
            if (i < arr.length - 1 ) {
                returnValue = returnValue + delim;
            }

        }


        return returnValue;
    }
    protected static String padRight(String s, String pad, int n) {
        String returnValue;

        returnValue =  String.format("%-" + n + "s", s);

        if (pad != " ") {
            returnValue = returnValue.replace(" ", pad);
        }

        if (returnValue.length() > n ) {
            returnValue = returnValue.substring(0, n);
        }
        return returnValue;
    }

    protected static String padLeft(String s, String pad, int n) {

        String returnValue;
        returnValue =  String.format("%" + n + "s", s);

        if (pad != " ") {
            returnValue = returnValue.replace(" ", pad);
        }

        if (returnValue.length() > n ) {
            returnValue = returnValue.substring(returnValue.length() - n);
        }
        return returnValue;
    }

    public class SendDataTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String[] doInBackground(String... params) {

            Log.e("FTP-jp0", "begin doInBackground");


            String server = params[0];
            int port =   Integer.parseInt(params[1]);
            String user = params[2];
            String pass = params[3];
            String fileName = params[4];
            String fileContents = params[5];


            FTPClient ftpClient = new FTPClient();


            try {

                ftpClient.connect(server, port);

                ftpClient.login(user, pass);


                ftpClient.enterLocalPassiveMode();

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                OutputStream outputStream = ftpClient.storeFileStream(fileName);

                byte[] bytesIn = fileContents.getBytes() ;

                outputStream.write(bytesIn, 0, fileContents.length());

                outputStream.close();

                boolean completed = ftpClient.completePendingCommand();


                Log.e("FTP-jp0", fileContents);
                //"end doInBackground " + completed  );
            } catch (IOException ex) {



                Log.e("FTP-jp0","catch block after location " );
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();


            }



            return null;

        }

        @Override
        protected void onPostExecute(String[] passedData) {
        }

    }


    public class GetDataTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String[] doInBackground(String... params) {

            Log.e("FTP-jp0", "begin doInBackground");


            String server = params[0];
            int port =   Integer.parseInt(params[1]);
            String user = params[2];
            String pass = params[3];
            String fileName = params[4];
            String fileContents = "";

            int bytesRead;

            FTPClient ftpClient = new FTPClient();


            try {

                ftpClient.connect(server, port);

                ftpClient.login(user, pass);


                ftpClient.enterLocalPassiveMode();

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                InputStream inputStream  = ftpClient.retrieveFileStream(fileName);



                byte[] bytesIn = new byte[2048];

                while ((bytesRead = inputStream.read(bytesIn)) > 0) {

                        fileContents = fileContents + new String( bytesIn) ;
                }

            } catch (IOException ex) {



                Log.e("FTP-jp0","catch block after location " );
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();


            }



            return new String[] { fileContents};

        }

        @Override
        protected void onPostExecute(String[] passedData) {
            if (passedData == null)  return;

            String fileContents = passedData[0];

            String textdata = "";

            String [] splitData;
            splitData = fileContents.split("\r\n") ;

            int speed;
            int i;

            for (i=0; i<splitData.length; i++){

                  if (splitData[i].equals("[TRICK CODING VERSION 2.2]" )) break;
                if (! textdata.equals("") ) {

                    textdata = textdata + "\n" ;
                    lineLength = splitData[i].length();
                }


                textdata = textdata + splitData[i];

                if (lineLength <  splitData[i].length()) lineLength =  splitData[i].length();


            }

            lineCount = i;

            i = i + 1;




                    String dataline = splitData[i];
                    int len = dataline.length();

                    String lineCountStr = dataline.substring(20,22);
                    String speedStr = dataline.substring(22,24);

                    lineCount = parseUnsignedInt( lineCountStr , 16);
                    speed  = parseUnsignedInt( speedStr , 16) ;

                    if (speed == NaN ||  speed < 1 || speed > 255 ) speed = 100;

                    speed = speed / 10;
                    mSpeed.setText("" + speed);
                    mTextData.setText(textdata);


        }

    }


}
