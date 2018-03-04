package bctest.com.bctest;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class Adoption extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b60018054600160a060020a03191633600160a060020a031617905561049c806100396000396000f3006060604052600436106100615763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166366db674581146100665780638588b2c514610095578063b155fb47146100a2578063cfb869bf146100f3575b600080fd5b341561007157600080fd5b6100796101a9565b604051600160a060020a03909116815260200160405180910390f35b6100a06004356101b8565b005b34156100ad57600080fd5b6100a060046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284375094965061021c95505050505050565b34156100fe57600080fd5b6101096004356102dc565b60405160208101839052600160a060020a0382166040820152606080825284546002600019610100600184161502019091160490820181905281906080820190869080156101985780601f1061016d57610100808354040283529160200191610198565b820191906000526020600020905b81548152906001019060200180831161017b57829003601f168201915b505094505050505060405180910390f35b600154600160a060020a031681565b60005481106101c657600080fd5b346000828154811015156101d657fe5b9060005260206000209060030201600101541015156101f457600080fd5b3460008281548110151561020457fe5b90600052602060002090600302016001018190555050565b60015433600160a060020a0390811691161461023757600080fd5b60008054600181016102498382610314565b916000526020600020906003020160006060604051908101604090815285825260006020830152600154600160a060020a031690820152919050815181908051610297929160200190610345565b50602082015181600101556040820151600291909101805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055505050565b60008054829081106102ea57fe5b600091825260209091206003909102016001810154600282015491925090600160a060020a031683565b8154818355818115116103405760030281600302836000526020600020918201910161034091906103c3565b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061038657805160ff19168380011785556103b3565b828001600101855582156103b3579182015b828111156103b3578251825591602001919060010190610398565b506103bf92915061040f565b5090565b61040c91905b808211156103bf5760006103dd8282610429565b506000600182015560028101805473ffffffffffffffffffffffffffffffffffffffff191690556003016103c9565b90565b61040c91905b808211156103bf5760008155600101610415565b50805460018160011615610100020316600290046000825580601f1061044f575061046d565b601f01602090049060005260206000209081019061046d919061040f565b505600a165627a7a7230582027b05d443771dc2a8e5f0110803db773d3c97209f077acd22015909af84493a90029";

    protected Adoption(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Adoption(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<String> adoptionHouserOwner() {
        Function function = new Function("adoptionHouserOwner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> adopt(BigInteger petId, BigInteger weiValue) {
        Function function = new Function(
                "adopt", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(petId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> addPet(String petName) {
        Function function = new Function(
                "addPet", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(petName)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<String, BigInteger, String>> pets(BigInteger param0) {
        final Function function = new Function("pets", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        return new RemoteCall<Tuple3<String, BigInteger, String>>(
                new Callable<Tuple3<String, BigInteger, String>>() {
                    @Override
                    public Tuple3<String, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple3<String, BigInteger, String>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    public static RemoteCall<Adoption> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Adoption.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Adoption> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Adoption.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Adoption load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Adoption(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Adoption load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Adoption(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
