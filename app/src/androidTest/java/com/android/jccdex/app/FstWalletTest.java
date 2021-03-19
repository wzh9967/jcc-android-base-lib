package com.android.jccdex.app;

import android.support.test.rule.ActivityTestRule;

import com.android.jccdex.app.base.JCallback;
import com.android.jccdex.app.fst.FstWallet;
import com.android.jccdex.app.util.JCCJson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName FstWalletTest
 * @Authur name
 * @Date 21-3-19
 * Description
 */
public class FstWalletTest {

    private FstWallet walletUtil;
    final private static String node = "http://101.200.174.239:7545";
    private static final String hash = "0x2bbf4c21249467f8541689c2bde773d2729da1d4d1daa08bf0c38e3524ef7c93";
    final private static String address = "0x1e99e9720409355B64A7c9582975d2a73f594e83";
    final private static String contract ="0xc19323c4c4298673b41c6847ba937b5e6d9d77db";
    final private static String secret = "0x6defd9e9359bfcfd3c13266378b15b299e8ff6ec2cf25d948f78ec2d65887b88";
    final private static String password = "Qq123456";
    final private static String IBAN = "XE703KOMUB7RABL33RE06B2925QKY3B63K3";
    final private static String words = "follow horror traffic pipe ladder relief glare emotion thumb equip script tornado";
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        walletUtil = mActivityRule.getActivity().getmFstWallet();
    }

    @Test
    public void testCreateFstWallet() {
        final CountDownLatch sigal = new CountDownLatch(1);
        walletUtil.createWallet( new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Assert.assertNotNull(json.getString("address"));
                Assert.assertNotNull(json.getString("secret"));
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
        final CountDownLatch latch = new CountDownLatch(1);
        walletUtil.isValidAddress(address, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Assert.assertEquals(json.getString("isAddress"),"true");
                latch.countDown();
            }
        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsValidSecret() {
        final CountDownLatch latch = new CountDownLatch(1);
        walletUtil.isValidSecret(secret, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Assert.assertEquals(json.getString("isSecret"),"true");
                latch.countDown();
            }
        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testImportSecret() {
        final CountDownLatch latch = new CountDownLatch(1);
        walletUtil.importSecret(secret,password, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Assert.assertEquals(address,json.getString("address"));
                Assert.assertEquals(secret,json.getString("secret"));
                latch.countDown();
            }
        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testImportWords() {
        final CountDownLatch latch = new CountDownLatch(1);
        walletUtil.importWords(words,password, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Assert.assertEquals(address,json.getString("address"));
                Assert.assertEquals(secret,json.getString("secret"));
                latch.countDown();
            }
        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testToIban() {
        final CountDownLatch latch = new CountDownLatch(1);
        walletUtil.toIban(address, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String iban = json.getString("Iban");
                Assert.assertEquals(IBAN, iban);
                latch.countDown();
            }
        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFromIban() {
        final CountDownLatch latch = new CountDownLatch(1);
        walletUtil.fromIban(IBAN, new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String address1 = json.getString("address");
                Assert.assertEquals(address.toLowerCase(), address1.toLowerCase());
                latch.countDown();
            }
        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetBalance() {
        final CountDownLatch latch = new CountDownLatch(1);
        walletUtil.getBalance(address,new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String balance = json.getString("balance");
                Assert.assertNotEquals(balance,"");
                latch.countDown();
            }

        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendErc20Transaction() {
        final CountDownLatch latch = new CountDownLatch(1);
        JCCJson data2 = new JCCJson("{}");
        data2.put("address","0x981d4bc976c221b3b42270be6dcab72d37d2e0cd");
        data2.put("to",address);
        data2.put("secret","0x1a0ad31a04ed4dbcec91a8a54c0d87187b50ab60e03139f404533332e9b31917");
        data2.put("value","10000000000000000000");//0.1
        data2.put("gasLimit","700000");
        data2.put("gasPrice","10000000000");
        data2.put("data","");
        data2.put("contract",contract);
        walletUtil.sendErc20Transaction(data2,new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String hash = json.getString("hash");
                Assert.assertNotEquals(hash,"");
                latch.countDown();
            }

        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSendTransaction() {
        final CountDownLatch latch = new CountDownLatch(1);
        JCCJson data = new JCCJson("{}");
        data.put("address","0x981d4bc976c221b3b42270be6dcab72d37d2e0cd");
        data.put("to",address);
        data.put("secret","0x1a0ad31a04ed4dbcec91a8a54c0d87187b50ab60e03139f404533332e9b31917");
        data.put("value","1000000000000000");//0.1
        data.put("gasLimit","22000");
        data.put("gasPrice","10000000000");
        data.put("data","");
        walletUtil.sendTransaction(data,new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String hash = json.getString("hash");
                Assert.assertNotEquals(hash,"");
                latch.countDown();
            }
        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSignTransaction() {
        final CountDownLatch latch = new CountDownLatch(1);
        JCCJson data = new JCCJson("{}");
        data.put("address","0x981d4bc976c221b3b42270be6dcab72d37d2e0cd");
        data.put("to",address);
        data.put("secret","0x1a0ad31a04ed4dbcec91a8a54c0d87187b50ab60e03139f404533332e9b31917");
        data.put("value","1000000000000000");//0.1
        data.put("gasLimit","22000");
        data.put("gasPrice","10000000000.0");
        data.put("data","");
        walletUtil.SignTransaction(data,new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String raw = json.getString("raw");
                Assert.assertNotEquals(raw,"");
                latch.countDown();
            }
        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetErc20Balance(){
        final CountDownLatch latch = new CountDownLatch(1);
        walletUtil.getErc20Balance(contract,address,new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Assert.assertNotEquals(json.getString("balance"),"");
                latch.countDown();
            }
        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetGasPrice() {
        final CountDownLatch latch = new CountDownLatch(1);
        walletUtil.getGasPrice(new JCallback() {
            @Override
            public void completion(JCCJson json) {
                String GasPrice = json.getString("GasPrice");
                Assert.assertNotEquals(GasPrice,"");
                latch.countDown();
            }
        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetTransactionDetail() {
        final CountDownLatch latch = new CountDownLatch(1);
        walletUtil.getTransactionDetail(hash,new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Assert.assertNotNull(json.toString());
                latch.countDown();
            }
        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetTransactionReceipt() {
        final CountDownLatch latch = new CountDownLatch(1);
        walletUtil.getTransactionReceipt(hash,new JCallback() {
            @Override
            public void completion(JCCJson json) {
                Assert.assertNotNull(json.toString());
                latch.countDown();
            }
        });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
