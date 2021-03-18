package com.android.jccdex.app.fst;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.jccdex.app.base.JCallback;
import com.android.jccdex.app.util.JCCJson;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import org.json.JSONObject;

/**
 * @ClassName FSTWallet
 * @Authur name
 * @Date 21-3-13
 * Description
 */
public class FstWallet implements Ifst{

    private static final String STORM_JS = "file:///android_asset/jccdex_storm3.html";
    private static BridgeWebView mWebview;
    private static FstWallet instance = new FstWallet();

    @Override
    public void init(Context context, String node, JCallback callback) {
        mWebview = new BridgeWebView(context);
        mWebview.loadUrl(STORM_JS);
    }

    public static FstWallet getInstance() {
        return instance;
    }

    @Override
    public void initContract(String contract, String address, String node) {

    }

    @Override
    public void createWallet(@NonNull final JCallback callback) {
        mWebview.callHandler("createWallet",null, new CallBackFunction(){
            @Override
            public void onCallBack(String data) {
                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);
            }
        });
    }

    @Override
    public void isValidAddress(String address, final JCallback callback) {

        mWebview.callHandler("isValidAddress",address, new CallBackFunction(){
            @Override
            public void onCallBack(String data) {
                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);
            }
        });
    }

    @Override
    public void isValidSecret(String secret, final JCallback callback) {
        mWebview.callHandler("isValidSecret",secret, new CallBackFunction(){
            @Override
            public void onCallBack(String data) {
                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);

            }
        });
    }

    @Override
    public void importSecret(String secret, String password, final JCallback callback) {
        JCCJson json = new JCCJson("{}");
        json.put("secret",secret);
        json.put("password",password);
        mWebview.callHandler("importSecret",json.toString(), new CallBackFunction(){
            @Override
            public void onCallBack(String data) {

                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);
            }
        });
    }

    @Override
    public void importWords(String words, String password, final JCallback callback) {
        JCCJson json = new JCCJson("{}");
        json.put("words",words);
        json.put("password",password);
        mWebview.callHandler("importWords",json.toString(), new CallBackFunction(){
            @Override
            public void onCallBack(String data) {
                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);

            }
        });
    }

    @Override
    public void toIban(String address, final JCallback callback) {
        mWebview.callHandler("toIban",address, new CallBackFunction(){
            @Override
            public void onCallBack(String data) {
                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);
            }
        });
    }

    @Override
    public void fromIban(String iban, final JCallback callback) {
        mWebview.callHandler("fromIban",iban, new CallBackFunction(){
            @Override
            public void onCallBack(String data) {
                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);

            }
        });
    }

    @Override
    public void getBalance(String address, final JCallback callback) {
        mWebview.callHandler("getBalance",address, new CallBackFunction(){
            @Override
            public void onCallBack(String data) {
                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);

            }
        });
    }

    @Override
    public void sendErc20Transaction(JCCJson data, final JCallback callback) {
        mWebview.callHandler("sendErc20Transaction",data.toString(), new CallBackFunction(){
            @Override
            public void onCallBack(String data) {
                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);

            }
        });
    }

    @Override
    public void sendTransaction(JCCJson data, final JCallback callback) {
        mWebview.callHandler("sendTransaction",data.toString(), new CallBackFunction(){
            @Override
            public void onCallBack(String data) {
                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);

            }
        });
    }

    @Override
    public void getErc20Balance(String Contract, String address, final JCallback callback) {
        JCCJson json = new JCCJson("{}");
        json.put("contract",Contract);
        json.put("address",address);
        mWebview.callHandler("getErc20Balance",json.toString(), new CallBackFunction(){
            @Override
            public void onCallBack(String data) {

                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);
            }
        });
    }

    @Override
    public void getGasPrice(final JCallback callback) {
        mWebview.callHandler("getGasPrice",null, new CallBackFunction(){
            @Override
            public void onCallBack(String data) {

                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);
            }
        });
    }

    @Override
    public void getTransactionDetail(String hash, final JCallback callback) {
        mWebview.callHandler("getTransactionDetail",hash, new CallBackFunction(){
            @Override
            public void onCallBack(String data) {

                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);
            }
        });
    }

    @Override
    public void getTransactionReceipt(String hash, final JCallback callback) {
        mWebview.callHandler("getTransactionReceipt",hash, new CallBackFunction(){
            @Override
            public void onCallBack(String data) {
                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);

            }
        });
    }

    @Override
    public void SignTransaction(JCCJson data, final JCallback callback) {
        mWebview.callHandler("SignTransaction",data.toString(), new CallBackFunction(){
            @Override
            public void onCallBack(String data) {
                JCCJson jccJson = new JCCJson(data);
                callback.completion(jccJson);
            }
        });
    }
}
