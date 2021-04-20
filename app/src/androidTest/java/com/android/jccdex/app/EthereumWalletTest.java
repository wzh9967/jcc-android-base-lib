package com.android.jccdex.app;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.jccdex.app.base.JCallback;
import com.android.jccdex.app.ethereum.EthereumWallet;
import com.android.jccdex.app.util.JCCJson;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class EthereumWalletTest {

    private EthereumWallet manager;

    private final static String ADDRESS = "0xd6ae77be193ec7f47b3a09ff7cd6330f77788669";
    private final static String SECRET = "0xa52ea5ee0ec191f772cebc62101d651e360eda9777a362b0d7d63b66288486af";
    private final static String IBAN = "XE37P2S1N2RK1JVVPCMHEVVA0UI8LAF7JOP";
    private final static String PUBLIC_KEY = "C3YDepQyOU7VwlN1APt6IYmi6jHL0prOzQwFrncUTQo=";
    private final static String STRING = "";
    private final static String TYPEDATA_V1 = "{\"type\":\"string\",\"name\":\"message\",\"value\":\"Hi, Alice!\"}";
    private final static String TYPEDATA_V3 = "{\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"},{\"name\":\"chainId\",\"type\":\"uint256\"},{\"name\":\"verifyingContract\",\"type\":\"address\"}],\"Person\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"wallet\",\"type\":\"address\"}],\"Mail\":[{\"name\":\"from\",\"type\":\"Person\"},{\"name\":\"to\",\"type\":\"Person\"},{\"name\":\"contents\",\"type\":\"string\"}]},\"primaryType\":\"Mail\",\"domain\":{\"name\":\"Ether Mail\",\"version\":\"1\",\"chainId\":1,\"verifyingContract\":\"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC\"},\"message\":{\"from\":{\"name\":\"Cow\",\"wallet\":\"0xCD2a3d9F938E13CD947Ec05AbC7FE734Df8DD826\"},\"to\":{\"name\":\"Bob\",\"wallet\":\"0xbBbBBBBbbBBBbbbBbbBbbbbBBbBbbbbBbBbbBBbB\"},\"contents\":\"Hello, Bob!\"}}";
    private final static String TYPEDATA_V4 = "{\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"},{\"name\":\"chainId\",\"type\":\"uint256\"},{\"name\":\"verifyingContract\",\"type\":\"address\"}],\"Person\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"wallet\",\"type\":\"address\"}],\"Mail\":[{\"name\":\"from\",\"type\":\"Person\"},{\"name\":\"to\",\"type\":\"Person\"},{\"name\":\"contents\",\"type\":\"string\"}]},\"primaryType\":\"Mail\",\"domain\":{\"name\":\"Ether Mail\",\"version\":\"1\",\"chainId\":1,\"verifyingContract\":\"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC\"},\"message\":{\"from\":{\"name\":\"Cow\",\"wallet\":\"0xCD2a3d9F938E13CD947Ec05AbC7FE734Df8DD826\"},\"to\":{\"name\":\"Bob\",\"wallet\":\"0xbBbBBBBbbBBBbbbBbbBbbbbBBbBbbbbBbBbbBBbB\"},\"contents\":\"Hello, Bob!\"}}";
    private final static String TYPEDATA = "{\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"},{\"name\":\"chainId\",\"type\":\"uint256\"},{\"name\":\"verifyingContract\",\"type\":\"address\"}],\"Person\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"wallet\",\"type\":\"address\"}],\"Mail\":[{\"name\":\"from\",\"type\":\"Person\"},{\"name\":\"to\",\"type\":\"Person\"},{\"name\":\"contents\",\"type\":\"string\"}]},\"primaryType\":\"Mail\",\"domain\":{\"name\":\"Ether Mail\",\"version\":\"1\",\"chainId\":1,\"verifyingContract\":\"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC\"},\"message\":{\"from\":{\"name\":\"Cow\",\"wallet\":\"0xCD2a3d9F938E13CD947Ec05AbC7FE734Df8DD826\"},\"to\":{\"name\":\"Bob\",\"wallet\":\"0xbBbBBBBbbBBBbbbBbbBbbbbBBbBbbbbBbBbbBBbB\"},\"contents\":\"Hello, Bob!\"}}";
    private final static String PERSONALDATA = "0xead3fe9e4d57be5adadc9ef329f7c645a18288c8"; //任意字符串
    private final static String SIGNTYPE_RESULT = "0x4749c0aba33473f72f57576375772c9839f51b818f96478a4638bb31411df1bb3af38116e59dd11457cf2f623a4b50e7d3cb327d4017ed3bb5fc320884d128a81b";
    private final static String SIGNTYPE_RESULT_V3 = "0x50d9d0096c5afbb68c9b8877a1a04a0a1f1b3ea1febae68ad97e6c518bf2410910e2a1804b74821fb217f6a9e9a182fa60731a86393582d5a306c277505573e51b";
    private final static String SIGNTYPE_RESULT_V4 = "0x50d9d0096c5afbb68c9b8877a1a04a0a1f1b3ea1febae68ad97e6c518bf2410910e2a1804b74821fb217f6a9e9a182fa60731a86393582d5a306c277505573e51b";
    private final static String PERSONALDATA_RESULT = "0x04d8cde9b5f698a6f84140fc96986995d72bbf16fee7e1480fe54cf5e40435e2500f7d05fbcf96ba91008c76201a7141f91285bc544747c9af1863e2f3c959ab1c";
    private final static String STRING_RESULT = "";
    private final static String PASSWORD = "Example password";
    private final static String DECRYPT_PARAMS = "0x7b2276657273696f6e223a227832353531392d7873616c736132302d706f6c7931333035222c226e6f6e6365223a2257554e393250744b514d42422b433933673856785466566c7075516c32725450222c22657068656d5075626c69634b6579223a224a4871793361474f454c656d484971554243554258493575577361545857587158774f734d3367333941673d222c2263697068657274657874223a22614a494c3739355a5a627a4172565677676146426568714c6f62486e4e434a616437437764673d3d227d";
    private final static String DECRYPT_RESULT = "Hello world!";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        manager = mActivityRule.getActivity().getmEthereumWallet();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateWallet() {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.createWallet(new JCallback() {
            @Override
            public void completion(JCCJson json) {
                final String address = json.getString("address");
                final String secret = json.getString("secret");
                String words = json.getString("words");
                manager.importWords(words, new JCallback() {
                    @Override
                    public void completion(JCCJson json) {
                        Assert.assertEquals(address, json.getString("address"));
                        Assert.assertEquals(secret, json.getString("secret"));
                        sigal.countDown();
                    }
                });
            }
        });
        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }

    @Test
    public void testImportSecret() {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.importSecret(SECRET, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Assert.assertEquals(ADDRESS.toLowerCase(), json.getString("address").toLowerCase());
                Assert.assertEquals(SECRET.toLowerCase(), json.getString("secret").toLowerCase());
                sigal.countDown();
            }
        });

        manager.importSecret("aaaaa", new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Assert.assertNull(json.getString("address"));
                Assert.assertNull(json.getString("secret"));
                sigal.countDown();
            }
        });

        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }


    @Test
    public void testIsValidSecret() {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.isValidSecret(SECRET, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Boolean isValid = json.getBoolean("isValid");
                Assert.assertEquals(true, isValid);
                sigal.countDown();
            }
        });

        manager.isValidSecret("aaaaa", new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Boolean isValid = json.getBoolean("isValid");
                Assert.assertEquals(false, isValid);
                sigal.countDown();
            }
        });

        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }

    @Test
    public void testIsValidAddress() {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.isValidAddress(ADDRESS, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Boolean isValid = json.getBoolean("isValid");
                Assert.assertEquals(true, isValid);
                sigal.countDown();
            }
        });

        manager.isValidAddress(ADDRESS + "aaaa", new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Boolean isValid = json.getBoolean("isValid");
                Assert.assertEquals(false, isValid);
                sigal.countDown();
            }
        });

        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }

    @Test
    public void testToIban() {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.toIban(ADDRESS, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String iban = json.getString("iban");
                Assert.assertEquals(IBAN, iban);
                sigal.countDown();
            }
        });

        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }

    @Test
    public void testgetEncryptionPublicKey() {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.getEncryptionPublicKey(SECRET, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String publicKey = json.getString("publicKey");
                Assert.assertEquals(PUBLIC_KEY, publicKey);
                sigal.countDown();
            }
        });
        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }

    @Test
    public void testSignTypedData() throws JSONException {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.signTypedData(TYPEDATA_V1,SECRET, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String result = json.getString("result");
                Assert.assertEquals(SIGNTYPE_RESULT, result);
                sigal.countDown();
            }
        });
        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }



    @Test
    public void testSignTypedData_v3() throws JSONException {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.signTypedData_v3(new JSONObject(TYPEDATA_V3),SECRET, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String result = json.getString("result");
                Assert.assertEquals(SIGNTYPE_RESULT_V3, result);
                sigal.countDown();
            }
        });
        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }



    @Test
    public void testSignTypedData_v4() throws JSONException {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.signTypedData_v4(new JSONObject(TYPEDATA_V4),SECRET, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String result = json.getString("result");
                Assert.assertEquals(SIGNTYPE_RESULT_V3, result);
                sigal.countDown();
            }
        });
        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }



    @Test
    public void testPersonalSign() throws JSONException {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.personalSign(PERSONALDATA,SECRET,PASSWORD, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String result = json.getString("result");
                Assert.assertEquals(PERSONALDATA_RESULT, result);
                sigal.countDown();
            }
        });
        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }


    @Test
    public void testFromIban() {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.fromIban(IBAN, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String address = json.getString("address");
                Assert.assertEquals(ADDRESS.toLowerCase(), address.toLowerCase());
                sigal.countDown();
            }
        });

        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }

    @Test
    public void testGasPrice() {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.gasPrice(new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String gas = json.getString("gasPrice");
                Assert.assertNotNull(gas);
                sigal.countDown();
            }
        });

        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }

    @Test
    public void testGetBalance() {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.getBalance(ADDRESS, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String balance = json.getString("balance");
                Assert.assertEquals("0", balance);
                sigal.countDown();
            }
        });

        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }


    @Test
    public void testDecrypt() throws JSONException {
        final CountDownLatch sigal = new CountDownLatch(1);
        manager.decrypt(DECRYPT_PARAMS,SECRET, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String result = json.getString("result");
                Assert.assertEquals(DECRYPT_RESULT, result);
                sigal.countDown();
            }
        });

        try {
            sigal.await();
        } catch (InterruptedException e) {

        }
    }



    @Test
    public void testSignTransaction() {
        try {
            final CountDownLatch sigal = new CountDownLatch(1);
            JSONObject transaction = new JSONObject();
            transaction.put("from", ADDRESS);
            transaction.put("value", "1");
            transaction.put("to", "0x2995c1376a852e4040caf9dbae2c765e24c37a15");
            transaction.put("gas", "600000");
            transaction.put("gasPrice", "1000000000");
            final String sign = "0xf86480843b9aca00830927c0942995c1376a852e4040caf9dbae2c765e24c37a15018025a0252a5305e97275a547d25c4902fc696d6c9751708f66bba9f61c569e8428d6b4a06325b73c623359e3f2b824efd9dec1bda409d19e02e601cf6d390c45d7509ebb";
            manager.signTransaction(transaction, SECRET, new JCallback() {
                @Override
                public void completion(JCCJson json) {
                    String rawTransaction = json.getString("rawTransaction");
                    Assert.assertEquals(sign, rawTransaction);
                    sigal.countDown();
                }
            });

            manager.signTransaction(new JSONObject(), "aaaa", new JCallback() {
                @Override
                public void completion(JCCJson json) {
                    String errorMessage = json.getString("errorMessage");
                    Assert.assertNotNull(errorMessage);
                    sigal.countDown();
                }
            });
            sigal.await();
        } catch (Throwable e) {

        }
    }

    @Test
    public void testSendSignedTransaction() {
        try {
            final CountDownLatch sigal = new CountDownLatch(1);
            final String sign = "0xf86480843b9aca00830927c0942995c1376a852e4040caf9dbae2c765e24c37a15018025a0252a5305e97275a547d25c4902fc696d6c9751708f66bba9f61c569e8428d6b4a06325b73c623359e3f2b824efd9dec1bda409d19e02e601cf6d390c45d7509ebb";
            manager.sendSignedTransaction(sign, new JCallback() {
                @Override
                public void completion(JCCJson json) {
                    String errorMessage = json.getString("errorMessage");
                    Assert.assertNotNull(errorMessage);
                    sigal.countDown();
                }
            });
            sigal.await();
        } catch (Throwable e) {

        }
    }
}