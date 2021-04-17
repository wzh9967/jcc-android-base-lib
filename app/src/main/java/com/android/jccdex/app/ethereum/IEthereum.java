package com.android.jccdex.app.ethereum;

import com.android.jccdex.app.base.JCallback;

import org.json.JSONObject;

public interface IEthereum {
    void createWallet(JCallback callback);

    void isValidAddress(String address, JCallback callback);

    void isValidSecret(String secret, JCallback callback);

    void importSecret(String secret, JCallback callback);

    void importWords(String words, JCallback callback);

    void toIban(String address, JCallback callback);

    void fromIban(String iban, JCallback callback);

    void signTransaction(JSONObject transaction, String secret, JCallback callback);

    void sendSignedTransaction(String signedTransaction, JCallback callback);

    void gasPrice(JCallback callback);

    void getBalance(String address, JCallback callback);

    //公钥签名
    void sign(JSONObject data, JCallback callback);

    void getEncryptionPublicKey(String secret, JCallback callback);

    void signTypedData(JSONObject data,String secret, JCallback callback);

    void signTypedData_v3(JSONObject data,String secret, JCallback callback);

    void signTypedData_v4(JSONObject data, String secret,JCallback callback);

    void personalSign(JSONObject data, String secret,JCallback callback);


}
