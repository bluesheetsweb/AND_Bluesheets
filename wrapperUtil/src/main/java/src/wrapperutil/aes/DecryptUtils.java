package src.wrapperutil.aes;

import android.content.Context;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DecryptUtils {

    private static String TAG = "DecryptUtils";

    public static String eventEncrpt(String inString, boolean decrypt) {
        try {
            SecretKeySpec key = new SecretKeySpec("ev3ntd0cq1ty@123".getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] inputByte = inString.getBytes("UTF-8");
            if (decrypt) {
                cipher.init(Cipher.DECRYPT_MODE, key);
                return new String(cipher.doFinal(Base64.decode(inputByte, Base64.DEFAULT)));
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key);
                return new String(Base64.encode(cipher.doFinal(inputByte), Base64.DEFAULT));
            }
        } catch (Exception ex) {
            Log.e(TAG, " ex " + ex.getMessage());
            return "";
        }
    }



    /*** encyption work ***********************/
    public String crypto(Context context, String inString,String key, boolean decrypt) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                return new DecrptAboveM().crypto(decrypt, inString);
            }
            else if (new DecrptBelowMto18(context).isKeyExist()){
                return new DecrptBelowMto18(context).crypto(decrypt, inString);
            }
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                return new DecrptBelowMto18(context).crypto(decrypt, inString);
            }
            else{
                return inString;
            }


        } catch (Exception ex) {
            Log.e(TAG, " ex " , ex);
            return inString;
        }

    }


}
