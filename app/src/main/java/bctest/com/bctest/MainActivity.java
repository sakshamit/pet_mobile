package bctest.com.bctest;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;


import lib.folderpicker.FolderPicker;

import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

public class MainActivity extends AppCompatActivity {

    final static String PRIVATE_KEY = "0xbe4520597951c900e5f51fa94293c6eb7f439499bba7f15937e3fec9cdd893ba";
    final static String PUBLIC_KEY = "0xF917bB3aC69b2aaCb12F8B9D8926B9B5130A01B5";
    final static String CONTRACT_ADDRESS = "0xA164B12c1D4D287Fc155F48AB549B7Ad369b1688";
    final static String PASSWORD = "grihsobha";


    public static String infuraTestRinkebyUrl = "https://rinkeby.infura.io/wFIm9wRQ6plphHy3rN9P ";

    final static int BROWSE_REQUEST_CODE = 1001;

    //final static BigInteger GAS_PRICE = new BigInteger();
    final static String TAG = "bc_dev";

    TextView filePathTV;
    String keyPath = "";
    String keyPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAdopt = findViewById(R.id.buttonAdopt);
        buttonAdopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "doing something awesome");
                if(!new File(keyPath).exists()){
                    Log.d(TAG, "Key file does not exist");
                    Snackbar.make(view.getRootView(), "Path is wrong.", Snackbar.LENGTH_LONG);
                    return;
                }
                TextView passwordTV = (findViewById(R.id.passwordTV));
                keyPassword = passwordTV.getText().toString();
                if(keyPassword.isEmpty()){
                    Log.d(TAG, "Password is empty");
                    Snackbar.make(view.getRootView(), "Password is empty.", Snackbar.LENGTH_LONG);
                    return;
                }
                new GetAdooptersTask().execute();
                new DOBCWorkTask().execute();
            }
        });

        Button browseButton = findViewById(R.id.browseButton);
        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FolderPicker.class);
                intent.putExtra("pickFiles", true);
                intent.putExtra("location", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
                intent.putExtra("title", "Select key file");
                startActivityForResult(intent, BROWSE_REQUEST_CODE);

            }
        });

        filePathTV = findViewById(R.id.keyFilePath);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BROWSE_REQUEST_CODE  && resultCode == RESULT_OK) {
            keyPath = data.getExtras().getString("data");
            Log.i( TAG, keyPath + " exists : " + (new File(keyPath)).exists());
            filePathTV.setText(keyPath);
        }

    }

    class GetAdooptersTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Adoption_sol_Adoption adoptionSolAdoption = getAdoptionSol();
            if(adoptionSolAdoption!=null){
                try {
                    String adopter = adoptionSolAdoption.adopters(BigInteger.valueOf(1l)).send();
                    Log.d(TAG, "adopter of 1l : " + adopter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    class DOBCWorkTask  extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            doBCWork();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Snackbar.make(filePathTV.getRootView(), "Done, yay!", Snackbar.LENGTH_LONG);
        }
    }
    private void doBCWork(){
        Adoption_sol_Adoption adoptionSolAdoption = getAdoptionSol();
        if(adoptionSolAdoption!=null){
            try {
                TransactionReceipt receipt = adoptionSolAdoption.adopt(BigInteger.valueOf(6l)).send();
                Log.d(TAG, "transaction hash : " + receipt.getTransactionHash() + " transaction gas used " + receipt.getGasUsedRaw());
            } catch (Exception e) {
                Log.d(TAG, "Some exception in case of calling adopt");
                e.printStackTrace();
            }
        }
    }

    private Adoption_sol_Adoption getAdoptionSol(){
        Web3j web3j = Web3jFactory.build(new HttpService(infuraTestRinkebyUrl));
        /*try {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(PUBLIC_KEY, DefaultBlockParameterName.LATEST).send();
            Log.d(TAG, "balance : " + ethGetBalance.getBalance());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //browse field to upload from user.
        File file = new File(keyPath);
        if(file.exists()){
            Log.d(TAG, "file exists");
        }else{
            Log.d(TAG, "file does not exists");
        }

        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(keyPassword, file);
        } catch (IOException e) {
            Log.d(TAG, "Credential io exception");
            e.printStackTrace();
        } catch (CipherException e) {
            Log.d(TAG, "Cipher exception");
            e.printStackTrace();
        }
        if(credentials!=null){
            Adoption_sol_Adoption adoption_sol_adoption = Adoption_sol_Adoption.load(CONTRACT_ADDRESS,
                    web3j, credentials, GAS_PRICE, Contract.GAS_LIMIT);
            return adoption_sol_adoption;
        }
        return null;
    }
}
