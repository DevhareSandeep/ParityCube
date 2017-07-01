package demo.paritycube.com.deals.security;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;

import java.math.BigInteger;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

public class KeyStoreEncryptor
{
  private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
  private static final String KEY_ALIAS = "encryptDecrypt";

  public static String encrypt (Context context, String input)
  {
    if (   input == null
        || input.trim().isEmpty())
    {
      return input;
    }

    String result = null;
    try
    {
      final KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
      keyStore.load(null);

      if (!keyStore.containsAlias(KEY_ALIAS))
      {
        Calendar cal = Calendar.getInstance();
        Date startDate = cal.getTime();

        cal.add(Calendar.YEAR, 1);
        Date endDate = cal.getTime();

        KeyPairGeneratorSpec keySpec = new KeyPairGeneratorSpec.Builder(context)
            .setAlias(KEY_ALIAS)
            .setSubject(new X500Principal("CN=AndroidKeyEncryptor, O=Android Authority"))
            .setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()))
            .setStartDate(startDate)
            .setEndDate(endDate)
            .build();

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", ANDROID_KEY_STORE);
        keyGen.initialize(keySpec);
        keyGen.generateKeyPair();
      }

      KeyStore.PrivateKeyEntry privateKey =
          (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
      PublicKey publicKey = privateKey.getCertificate().getPublicKey();

      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] encryptedBytes = cipher.doFinal(input.getBytes());

      result = Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }

  public static String decrypt (String input)
  {
    if (   input == null
        || input.trim().isEmpty())
    {
      return input;
    }

    String result = null;
    try
    {
      final KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
      keyStore.load(null);

      KeyStore.PrivateKeyEntry privateKey =
          (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS, null);

      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
      cipher.init(Cipher.DECRYPT_MODE, privateKey.getPrivateKey());
      byte[] decryptedBytes = cipher.doFinal(Base64.decode(input, Base64.DEFAULT));
      result = new String(decryptedBytes);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
}
