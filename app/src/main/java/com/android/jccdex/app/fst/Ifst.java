package com.android.jccdex.app.fst;

import android.content.Context;

import com.android.jccdex.app.base.JCallback;
import com.android.jccdex.app.util.JCCJson;
/**
 * @ClassName Ifst
 * @Authur name
 * @Date 21-3-13
 * Description
 */
public interface Ifst {
    void init(Context context, String node, JCallback callback);

    void initContract(String contract,String address,String node);

    void createWallet(JCallback callback);

    void isValidAddress(String address, JCallback callback);

    void isValidSecret(String secret, JCallback callback);

    void importSecret(String secret,String password, JCallback callback);

    void importWords(String words, String password, JCallback callback);

    void toIban(String address, JCallback callback);

    void fromIban(String iban, JCallback callback);

    void getBalance(String address, JCallback callback);

    void sendErc20Transaction(JCCJson data, JCallback callback);

    void sendTransaction(JCCJson data, JCallback callback);

    void getErc20Balance(String Contract, String address, JCallback callback);

    void getGasPrice(JCallback wCallback);

    void getTransactionDetail(String hash, JCallback wCallback );

    void getTransactionReceipt(String hash, JCallback wCallback );

    void SignTransaction(JCCJson data, JCallback wCallback );
}
