package com.android.jccdex.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.jccdex.app.base.JCallback;
import com.android.jccdex.app.eos.EosWallet;
import com.android.jccdex.app.ethereum.EthereumWallet;
import com.android.jccdex.app.fst.FstWallet;
import com.android.jccdex.app.jingtum.JingtumWallet;
import com.android.jccdex.app.moac.MoacWallet;
import com.android.jccdex.app.util.JCCJson;

public class MainActivity extends Activity {
    private JingtumWallet mJingtumWallet;
    private EthereumWallet mEthereumWallet;
    private MoacWallet mMoacWallet;
    private EosWallet mEosWallet;
    private FstWallet mFstWallet;

    private JingtumWallet instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJingtumWallet = JingtumWallet.getInstance();
        mEthereumWallet = EthereumWallet.getInstance();
        mMoacWallet = MoacWallet.getInstance();
        instance = JingtumWallet.getInstance();
        mEosWallet = EosWallet.getInstance();
        mFstWallet = FstWallet.getInstance();
        instance.init(this);

        mEthereumWallet.init(this);
        mEosWallet.init(this);
        mEosWallet.initEosProvider("aca376f206b8fc25a6ed44dbdc66547c36c6c33e3a119ffbeaef943642f0e906", "http://openapi.eos.ren");
        mEthereumWallet.initWeb3Provider("https://eth626892d.jccdex.cn");
        mMoacWallet.init(this);
        mMoacWallet.initChain3Provider("https://moac1ma17f1.jccdex.cn");
        mFstWallet.init(this, "http://101.200.174.239:8545", new JCallback() {
            @Override
            public void completion(JCCJson json) {

            }
        });
        mFstWallet.initContract("0xc19323c4c4298673b41c6847ba937b5e6d9d77db","0x1B5dfdB30AA137cBb852617a93819bC42e2E0d14","http://101.200.174.239:8545");
    }

    public JingtumWallet getJTWalletManager() {
        return mJingtumWallet;
    }

    public JingtumWallet getInstance() {
        return instance;
    }

    public EthereumWallet getmEthereumWallet() {
        return mEthereumWallet;
    }

    public FstWallet getmFstWallet(){
        return mFstWallet;
    }

    public MoacWallet getmMoacWallet() {
        return mMoacWallet;
    }

    public EosWallet getmEosWallet() {
        return mEosWallet;
    }
}
