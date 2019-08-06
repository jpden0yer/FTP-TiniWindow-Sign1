package com.example.android.ftp_tiniwindow_sign1;

//042419 CF  following JP's version of 042319
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
import java.util.ArrayList;
import java.util.List;

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

    String[] chargen = new String[]{
            "0000",  //0
            "EEEE",  //1
            "DDDD",  //2
            "BBB7",   //3
            "777B",  //4
            "F7F4",   //5
            "FF8B",   //6
            "8F7F",   //7
            "78FF",   //8
            "2AB2",   //9
            "3AE2",   //10
            "2FA3",   //11
            "EBA6",   //
            "F72B",   //
            "EBA2",   //
            "FBE2",   //
            "FFFF",   //
            "FFFF",   //
            "FFFF",   //
            "FFFF",   //
            "FFFF",   //20
            "FFFF",
            "FFFF",
            "FFFF",
            "633B",
            "7D79",
            "6322",
            "72FF",
            "FDE5",
            "673B",  //
            "D75F",  //30
            "F57D",
            "FFFF",
            "BEFF",
            "F7F7",
            "0000",
            "2332",
            "2552",
            "A11C",
            "FDFF", //
            "DDFF", //40
            "FFDD", //41
            "5559", //42
            "777B", //43
            "FFDF", //44
            "7FFB", //45
            "FF2B",
            "FDDF", //
            "A886", //48 - 0
            "BEFF", //49 - 1
            "6AAA", //50 - 2
            "2ABE", //51 - 3
            "3EF3", //52 - 4
            "CBB2", //53  - 5
            "2BA2", //54 - 6
            "BAFE", //55 - 7
            "2AA2", //56 - 8
            "2AB2", //57 -  9
            "F77F", //58 - :
            "F7DF", //59
            "DDFF", //60 - <
            "6FBB", //61
            "FFDD", //62
            "7A7F", //63
            "62A6", //64
            "3AE2", //65 - A
            "223E", //66 - B
            "EBA6", //67 - C
            "A23E", //68 - D
            "6BA2", //69 - E
            "FBE2", //70 - F
            "2BA6", //71 - F
            "3EE3", //72 - G
            "F77F", //73 - I
            "AEAF", //74 - J
            "DDE3", //75 - K
            "EFA7", //76 - L
            "BCE5", //77 - M
            "9EE5", //78 - N
            "AAA6", //79 - O
            "7AE2", //80 - P
            "8AA6", //81 - Q
            "5AE2", //82 - R
            "2BB2", //83 - S
            "F37E", //84 - T
            "AEA7", //85 - U
            "FDC7", //86 - V
            "9EC7", //87 - W
            "DDDD", //88 - X
            "FD7D", //89 - Y
            "E99E", //90 - Z
            "E37F",
            "DFFD",
            "F73E",
            "F8DF",
            "EFBF",
            "FFFD",
            "3AE2",//97
            "223E",//98
            "EBA6",//99
            "A23E",//100
            "6BA2",//101
            "FBE2",//102
            "2BA6",//103
            "FFFF",//104
            "FFFF",//105
            "FFFF",//106
            "FFFF",//107
            "FFFF",//108
            "FFFF",//109
            "FFFF",  //110
            "FFFF"  //111

    };
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
        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public char hexTochar (String hexval){

        if (hexval.equals("FFFF") ) return ' ';

        for (int i = 0 ; i < chargen.length ; i++)
        {
            if (chargen [i].equals(hexval))
                return (char) i;
        }

        return '\0';
    }

    public String hexToText (String hexstring){
        String returnval = "";

        for (int i = 0; i < hexstring.length() ; i = i + 4 ){
            returnval += hexTochar (hexstring.substring(i, i + 4)) ;
        }
        return returnval;
    }

    public String charToHex (char ch){

       return  chargen[ch];

    }


    public String textToHex (String text){
        String returnval = "";

        for (int i = 0; i < text.length() ; i++ ){
            returnval += chargen [text.charAt(i)] ;
        }

        return returnval;
    }

    String strReverse (String str){
        String returnval = "";

        for (int i = str.length() - 1; i >=0; i--   )
        {
            returnval = returnval + str.charAt(i);

        }
        return returnval;
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
        mTextData.setText(mTextData.getText().toString().toUpperCase());
        String text = mTextData.getText().toString();
        String thisline = "";
        char thischar;
        int linecount = 0;
        String returnval = "";
        for (int j = 0; j < text.length(); j++ )
        {
            thischar = text.charAt(j);
            if (thischar == '\r' || thischar == '\n' || thisline.length() == 24 )
            {
                if (thisline.length() == 0)
                    continue;

                while (thisline.length() < 24 )
                    thisline = thisline + ' ';

                returnval = returnval + textToHex( strReverse(thisline) )  + "\r\n";
                linecount = linecount + 1;
                thisline = "";
                if (linecount >= 10 )
                    break;

                continue;
            }



            thisline = thisline +  thischar;

        }



        while (linecount < 10)
        {
            while (thisline.length() < 24 )
                thisline = thisline + ' ';

            returnval = returnval + textToHex( strReverse(thisline) )  + "\r\n";
            linecount = linecount + 1;
            thisline = "";
        }

        return returnval;
    };

    /*
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

*/
    public void Send(View view) {

        mTextData.setText(mTextData.getText().toString().toUpperCase());

        String fileContent = generateFileContents();

        String [] params = {            //params
                server,                 //0
                "" + port,              //1
                user,                   //2
                pass,                   //3
                fileName,               //4
                fileContent             //5

        };
        new SendDataTask().execute(params);

    }


    public void Get(View view) {

        String [] params = {            //params
                server,                 //0
                "" + port,              //1
                user,                   //2
                pass,                   //3
                fileName                //4


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

public String[] splitlines(String s){
    List<String> linelist = new ArrayList<String>();

    int start = 0;
    int end = 0;



    for (int i = 0; i <s.length(); i++)
    {
       String thisletter = s.substring(i,i+1 );
        if (thisletter.equals("\r") || thisletter.equals("\n")  )
       {
            if( (i - 1) <= start ){
                start = i + 1;
            }
            else {
                end = i ;
                String thisline =  s.substring(start, end);
                start = i + 1;
                linelist.add(thisline);
            }
       }

    }

    String thisletter = s.substring(s.length() - 1,s.length() );
    if (!thisletter.equals("\r") && !thisletter.equals("\n")  )
    {
        String thisline =  s.substring(start, s.length());
        linelist.add(thisline);

    }
    String[] retval = new String[linelist.size()] ;
    return linelist.toArray(retval );

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

            String textData = "";

            String [] splitData;
            splitData = splitlines(fileContents);

            for (int i=0; i<splitData.length; i++){

                textData +=  strReverse( hexToText( splitData[i])) + "\r";
            }
            mTextData.setText(textData);
        }
    }
}
