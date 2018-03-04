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

import org.web3j.abi.datatypes.Address;
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
import org.web3j.tuples.generated.Tuple3;
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
import java.util.List;


import lib.folderpicker.FolderPicker;

import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

public class MainActivity extends AppCompatActivity {

    final static String CONTRACT_ADDRESS = "0xA164B12c1D4D287Fc155F48AB549B7Ad369b1688";
    final static String CONTRACT_ADDRESS_NEW = "0xff0397a6c675096576e163e992f16158baf8d194";

    public static String infuraTestRinkebyUrl = "https://rinkeby.infura.io/wFIm9wRQ6plphHy3rN9P ";
    final static int BROWSE_REQUEST_CODE = 1001;
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
                keyPath  = "/storage/emulated/0/Download/key_file.json";
                File keyFile = new File(keyPath);
                if(!keyFile.exists()){
                    Log.d(TAG, "Key file does not exist " + keyPath);
                    Snackbar.make(view.getRootView(), "Path is wrong.", Snackbar.LENGTH_LONG);
                    return;
                }else{

                }
                TextView passwordTV = (findViewById(R.id.passwordTV));
                keyPassword = passwordTV.getText().toString();
                keyPassword = "grihsobha";
                if(keyPassword.isEmpty()){
                    Log.d(TAG, "Password is empty");
                    Snackbar.make(view.getRootView(), "Password is empty.", Snackbar.LENGTH_LONG);
                    return;
                }
                //new GetAdooptersTask().execute();
                //new DOBCWorkTask().execute();
                new GetPets().execute();
            }
        });

        Button browseButton = findViewById(R.id.browseButton);
        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FolderPicker.class);
                intent.putExtra("pickFiles", true);
                //intent.putExtra("location", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
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

    class GetPets extends  AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Adoption adoption = getAdoptionContractNew();
            if(adoption!=null){
                try {
                    Log.d(TAG, "calling  adoptionSolAdoption.getAdopters()");
                    Tuple3<String, BigInteger, String> tuple3 = adoption.pets(BigInteger.valueOf(0l)).send();
                    if(tuple3!=null){
                        Log.d(TAG, tuple3.getValue1().toString() + tuple3.getValue2().toString() + tuple3.getValue3().toString() + "");
                    }else{
                        Log.d(TAG, "adopers list is null");
                    }
                } catch (Exception e) {
                    Log.d(TAG, "some exception in adoptionSolAdoption.getAdopters()");
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

    class GetAdooptersTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Adoption_sol_Adoption adoptionSolAdoption = getAdoptionSol();
            if(adoptionSolAdoption!=null){
                try {
                    Log.d(TAG, "calling  adoptionSolAdoption.getAdopters()");
                    List<Address> adopters = adoptionSolAdoption.getAdopters().send();
                    if(adopters!=null){
                        Log.d(TAG, "adopters size : " + adopters.size());
                        for(Address adopter : adopters){
                            Log.d(TAG, "adopter : " + adopter.toString());
                        }
                    }else{
                        Log.d(TAG, "adopers list is null");
                    }
                } catch (Exception e) {
                    Log.d(TAG, "some exception in adoptionSolAdoption.getAdopters()");
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

    private Adoption getAdoptionContractNew(){

        Web3j web3j = Web3jFactory.build(new HttpService(infuraTestRinkebyUrl));
        //browse field to upload from user.
        File file = new File(keyPath);
        if(file.exists()){
            Log.d(TAG, "file exists");
        }else{
            Log.d(TAG, "file does not exists");
        }

        Credentials credentials = null;
        try {
            keyPassword = "grihsobha";
            credentials = WalletUtils.loadCredentials(keyPassword, file);
        } catch (IOException e) {
            Log.d(TAG, "Credential io exception : " + keyPassword);
            e.printStackTrace();
        } catch (CipherException e) {
            Log.d(TAG, "Cipher exception " + keyPassword);
            e.printStackTrace();
        }
        if(credentials!=null){
            Adoption adoption = Adoption.load(CONTRACT_ADDRESS_NEW, web3j, credentials, GAS_PRICE, Contract.GAS_LIMIT);
            return adoption;
        }
        return null;

    }
    private Adoption_sol_Adoption getAdoptionSol(){
        Web3j web3j = Web3jFactory.build(new HttpService(infuraTestRinkebyUrl));
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
