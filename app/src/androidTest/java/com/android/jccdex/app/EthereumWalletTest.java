package com.android.jccdex.app;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.jccdex.app.base.JCallback;
import com.android.jccdex.app.ethereum.EthereumWallet;
import com.android.jccdex.app.util.JCCJson;

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
    public void testgetEncryptionPublicKey(){
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
    public void testSign() {
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