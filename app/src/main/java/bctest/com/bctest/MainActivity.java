package bctest.com.bctest;

import android.content.res.AssetFileDescriptor;
import android.os.AsyncTask;
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


import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

public class MainActivity extends AppCompatActivity {

    final static String PRIVATE_KEY = "0xbe4520597951c900e5f51fa94293c6eb7f439499bba7f15937e3fec9cdd893ba";
    final static String PUBLIC_KEY = "0xF917bB3aC69b2aaCb12F8B9D8926B9B5130A01B5";
    final static String CONTRACT_ADDRESS = "0xA164B12c1D4D287Fc155F48AB549B7Ad369b1688";
    final static String PASSWORD = "grihsobha";
    final static String KEY_FILE_NAME = "key_file.json";

    public static String infuraTestRinkebyUrl = "https://rinkeby.infura.io/wFIm9wRQ6plphHy3rN9P ";

    //final static BigInteger GAS_PRICE = new BigInteger();
    final static String TAG = "bc_dev";
    TextView statusTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonAdopt = (Button)findViewById(R.id.buttonAdopt);
        statusTV = (TextView)findViewById(R.id.statusTV);
        buttonAdopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "doing something awesome");
                statusTV.setText("Wait..");
                new DOBCWorkTask().execute();
            }
        });
    }


    class DOBCWorkTask  extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            doBCWork();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            statusTV.setText("Done.");
        }
    }
    private void doBCWork(){


        Web3j web3j = Web3jFactory.build(new HttpService(infuraTestRinkebyUrl));
        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            Log.d(TAG, "web3ClientVersion :" + web3ClientVersion.getWeb3ClientVersion());
        } catch (IOException e) {
            Log.d(TAG, "web3ClientVersion exception");
            e.printStackTrace();
        }

        try {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(PUBLIC_KEY, DefaultBlockParameterName.LATEST).send();
            Log.d(TAG, "balance : " + ethGetBalance.getBalance());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //browse field to upload from user.
        File file = new File("/storage/emulated/0/Download/key_file.json");
        if(file.exists()){
            Log.d(TAG, "file exists");
        }else{
            Log.d(TAG, "file does not exists");
        }



        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(PASSWORD, file);
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
            try {
                TransactionReceipt receipt = adoption_sol_adoption.adopt(BigInteger.valueOf(6l)).send();
                Log.d(TAG, "transaction hash : " + receipt.getTransactionHash() + " transaction gas used " + receipt.getGasUsedRaw());
            } catch (Exception e) {
                Log.d(TAG, "Some exception in case of calling adopt");
                e.printStackTrace();
            }
        }

    }
}
