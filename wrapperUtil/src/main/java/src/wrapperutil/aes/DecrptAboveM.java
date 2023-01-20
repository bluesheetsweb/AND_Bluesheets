package src.wrapperutil.aes;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import androidx.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;


@RequiresApi(api = Build.VERSION_CODES.M)
public class DecrptAboveM {
    private static String TAG = "DecrptAboveM";

    private String AndroidKeyStore = "AndroidKeyStore";
    private String KEY_ALIAS = "secret_alias";
    private String AES_MODE = "AES/GCM/NoPadding";
    private byte[] FIXED_IV = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


    KeyStore  keyStore;


    public String crypto(boolean decrypt, String inString){

        try {
            //Log.e(TAG,"crypto above decrypt"+decrypt);
            //Log.e(TAG,"crypto above inString"+inString);
            byte[] inputByte = inString.getBytes("UTF-8");


          //  SecureRandom r = new SecureRandom();
           // byte[] FIXED_IV = new byte[12];
            //r.nextBytes(FIXED_IV);

           /* if (decrypt) {

                Cipher c = Cipher.getInstance(AES_MODE);
                c.init(Cipher.DECRYPT_MODE, keyStore.getKey(KEY_ALIAS, null));
                return new String(c.doFinal(Base64.decode(inputByte, Base64.DEFAULT)));

            } else {
                Log.e(TAG,"decrypt else called");
                Cipher c = Cipher.getInstance(AES_MODE);
                c.init(Cipher.ENCRYPT_MODE, keyStore.getKey(KEY_ALIAS, null));
                return new String(Base64.encode(c.doFinal(inputByte), Base64.DEFAULT));
            }*/

            if (decrypt) {
              //  Log.e(TAG,"decrypt if called");
                Cipher c = Cipher.getInstance(AES_MODE);
                c.init(Cipher.DECRYPT_MODE, keyStore.getKey(KEY_ALIAS, null), new GCMParameterSpec(128, FIXED_IV));
                return new String(c.doFinal(Base64.decode(inputByte, Base64.DEFAULT)));

            } else {
              //  Log.e(TAG,"decrypt else called");
                Cipher c = Cipher.getInstance(AES_MODE);
                c.init(Cipher.ENCRYPT_MODE, keyStore.getKey(KEY_ALIAS, null), new GCMParameterSpec(128, FIXED_IV));
                return new String(Base64.encode(c.doFinal(inputByte), Base64.DEFAULT));
            }

        }catch (Exception ex){
            Log.e(TAG, "crypto ex ",ex );
            return inString;
        }
        
    }



    public DecrptAboveM(){

        try {
            keyStore = KeyStore.getInstance(AndroidKeyStore);
            keyStore.load(null);

            if (!keyStore.containsAlias(KEY_ALIAS)) {

                KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, AndroidKeyStore);
                keyGenerator.init(
                        new KeyGenParameterSpec.Builder(KEY_ALIAS,
                                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                                .setBlockModes(KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                                .setRandomizedEncryptionRequired(false)
                                .build());
                keyGenerator.generateKey();


            }

        }catch (Exception ex){
            Log.e(TAG, "DecrptAboveM: ",ex);
        }
    }







}
