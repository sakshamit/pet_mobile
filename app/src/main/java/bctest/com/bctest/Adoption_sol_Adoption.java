package bctest.com.bctest;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.StaticArray16;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class Adoption_sol_Adoption extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b61024c8061001e6000396000f3006060604052600436106100565763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416633de4eb17811461005b57806343ae80d3146100a75780638588b2c5146100e6575b600080fd5b341561006657600080fd5b61006e61010e565b604051808261020080838360005b8381101561009457808201518382015260200161007c565b5050505090500191505060405180910390f35b34156100b257600080fd5b6100bd600435610164565b60405173ffffffffffffffffffffffffffffffffffffffff909116815260200160405180910390f35b34156100f157600080fd5b6100fc60043561018e565b60405190815260200160405180910390f35b6101166101f7565b600060106102006040519081016040529190610200830182845b815473ffffffffffffffffffffffffffffffffffffffff168152600190910190602001808311610130575050505050905090565b6000816010811061017157fe5b015473ffffffffffffffffffffffffffffffffffffffff16905081565b60008082101580156101a15750600f8211155b15156101ac57600080fd5b33600083601081106101ba57fe5b01805473ffffffffffffffffffffffffffffffffffffffff191673ffffffffffffffffffffffffffffffffffffffff929092169190911790555090565b6102006040519081016040526010815b60008152600019909101906020018161020757905050905600a165627a7a723058206b4b4a1002adee326863562a5cb4a9e543ca827bfab8a3addc7ab6c057b00dd90029";

    protected Adoption_sol_Adoption(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Adoption_sol_Adoption(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    /*public RemoteCall<List<String>> getAdopters() {
        Function function = new Function("getAdopters",
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray16<Address>>() {}));
        //return executeRemoteCallSingleValueReturn(function, List<String>.class);
        return executeRemoteCallSingleValueReturn(function, );
        //return executeRemoteCallSingleValueReturn()
    }*/

    public RemoteCall<String> adopters(BigInteger param0) {
        Function function = new Function("adopters", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> adopt(BigInteger petId) {
        Function function = new Function(
                "adopt", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(petId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Adoption_sol_Adoption> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Adoption_sol_Adoption.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Adoption_sol_Adoption> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Adoption_sol_Adoption.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Adoption_sol_Adoption load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Adoption_sol_Adoption(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Adoption_sol_Adoption load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Adoption_sol_Adoption(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
