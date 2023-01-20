package src.wrapperutil.aes;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

import src.wrapperutil.utilities.FileUtils;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class DecrptBelowMto18 {
    private static String TAG = DecrptBelowMto18.class.getSimpleName();

    private String ENCRYPTED_KEY = "encrypt_key_share_blm";
    private String KEY_ALIAS = "secret_alias_blm";


    private final String AndroidKeyStore = "AndroidKeyStore";
    private final String RSA_MODE = "RSA/ECB/PKCS1Padding";
    private static final String AES_MODE = "AES/ECB/PKCS7Padding";

    KeyStore keyStore;


    public boolean isKeyExist() {
        String enryptedKeyB64 = ENCRYPTED_KEY;
      //  Log.e(TAG,"isKeyExist"+enryptedKeyB64);
        return !FileUtils.INSTANCE.isEmptyString(enryptedKeyB64);


    }

    public String crypto( boolean decrypt, String inString) {
        try {


            byte[] inputByte = inString.getBytes(StandardCharsets.UTF_8);

            if (decrypt) {

                Cipher c = Cipher.getInstance(AES_MODE, "BC");
                c.init(Cipher.DECRYPT_MODE, getSecretKey());

               // byte[] decodedBytes = c.doFinal(inputByte);
                String value=new String(c.doFinal(Base64.decode(inputByte, Base64.DEFAULT)));
             //   Log.e(TAG,"if decrypt"+ value);
                return value;

            } else {

                Cipher c = Cipher.getInstance(AES_MODE, "BC");
               // Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                c.init(Cipher.ENCRYPT_MODE, getSecretKey());
               // byte[] encodedBytes = c.doFinal(inputByte);

                String value=new String(Base64.encode(c.doFinal(inputByte), Base64.DEFAULT));

               // Log.e(TAG,"else decrypt"+ value);

                return value;
            }
        } catch (Exception ex) {
            Log.e(TAG, "crypto:ex " , ex);
            return inString;
        }

    }


    public DecrptBelowMto18(Context context) {

        try {
            keyStore = KeyStore.getInstance(AndroidKeyStore);
            keyStore.load(null);

            // Generate the RSA key pairs
            if (!keyStore.containsAlias(KEY_ALIAS)) {

                // Generate a key pair for encryption
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 30);
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                        .setAlias(KEY_ALIAS)
                        .setSubject(new X500Principal("CN=" + KEY_ALIAS))
                        .setSerialNumber(BigInteger.TEN)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
                KeyPairGenerator kpg = KeyPairGenerator.getInstance( "RSA", AndroidKeyStore);
                kpg.initialize(spec);
                kpg.generateKeyPair();


            }

            // Generate and Store the AES Key
            String enryptedKeyB64 = ENCRYPTED_KEY;
           // Log.e(TAG,"DecrptBelowMto18"+enryptedKeyB64);
            if (enryptedKeyB64==null ||  enryptedKeyB64.isEmpty()) {
                byte[] key = new byte[16];
                SecureRandom secureRandom = new SecureRandom();
                secureRandom.nextBytes(key);
                byte[] encryptedKey = rsaEncrypt(key);
                enryptedKeyB64 = Base64.encodeToString(encryptedKey, Base64.NO_WRAP);
               // Log.e(TAG,"DecrptBelowMto18 if"+enryptedKeyB64);
                ENCRYPTED_KEY = enryptedKeyB64;
            }

        } catch (Exception ex) {
            Log.e(TAG, "DecrptBelowM:ex ", ex);
        }
    }


    private byte[] rsaEncrypt(byte[] secret) throws Exception {
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
        // Encrypt the text
        Cipher inputCipher = Cipher.getInstance(RSA_MODE, "AndroidOpenSSL");
        inputCipher.init(Cipher.ENCRYPT_MODE, privateKeyEntry.getCertificate().getPublicKey());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, inputCipher);
        cipherOutputStream.write(secret);
        cipherOutputStream.close();

        byte[] vals = outputStream.toByteArray();
        return vals;
    }


    private byte[] rsaDecrypt(byte[] encrypted) throws Exception {

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
        Cipher output = Cipher.getInstance(RSA_MODE, "AndroidOpenSSL");
        output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());
        CipherInputStream cipherInputStream = new CipherInputStream(
                new ByteArrayInputStream(encrypted), output);
        ArrayList<Byte> values = new ArrayList<>();
        int nextByte;
        while ((nextByte = cipherInputStream.read()) != -1) {
            values.add((byte) nextByte);
        }

        byte[] bytes = new byte[values.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = values.get(i).byteValue();
        }
        return bytes;
    }


    private Key getSecretKey() throws Exception {

        String enryptedKeyB64 = ENCRYPTED_KEY;
        // need to check null, omitted here
        byte[] encryptedKey = Base64.decode(enryptedKeyB64, Base64.DEFAULT);
        byte[] key = rsaDecrypt(encryptedKey);
        return new SecretKeySpec(key, "AES");

    }


    //Step 1- Encrypt key using rsa and save it to shared prefrencess
    //Step 2-Get Encrypted key from Shared Prefrencess and Decrypt it
    //Step 3-Generate Secret Key using decryted key
    //Step 4-Encrypt data using this key


}
