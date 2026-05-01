package defaut;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	
	  private SecretKey key;
	    private int key_SIZE = 128;
	    private int T_LEN = 128;
	    private byte [] IV ;

	    public void init() throws Exception {
	        KeyGenerator generator = KeyGenerator.getInstance("AES");
	        generator.init(key_SIZE);
	        key = generator.generateKey();
	    }

	    public String encrypt_OLD(String message) throws Exception {
	        byte[] messageInByte = message.getBytes();
	        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
	       // GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
	        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
	        IV = encryptionCipher.getIV();
	        byte[] encryptedByte = encryptionCipher.doFinal(messageInByte);
	        return encode(encryptedByte);
	    }
	    
	    public String encrypt(String message) throws Exception {
	        byte[] messageInByte = message.getBytes();
	        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
	        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
	        encryptionCipher.init(Cipher.ENCRYPT_MODE, key, spec);
	        //IV = encryptionCipher.getIV();
	        byte[] encryptedByte = encryptionCipher.doFinal(messageInByte);
	        return encode(encryptedByte);
	    }

	    public String decrypt(String encryptedMessage) throws Exception {
	        byte[] messageInByte = decode(encryptedMessage);
	        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
	        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
	        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
	        byte[] decryptedByte = decryptionCipher.doFinal(messageInByte);
	        return new String(decryptedByte);
	    }



	    private String encode(byte[] data) {
	        return Base64.getEncoder().encodeToString(data);
	    }

	    private byte[] decode(String data) {
	        return Base64.getDecoder().decode(data);
	    }
	
	    private void exportKey() {
	    	System.out.println("SecretKey: "+ encode(key.getEncoded()));
	    	System.out.println("IV: "+ encode(IV));
	    }
	    
	    public void initFromString(String secretKey, String IV) {
	    	key = new SecretKeySpec(decode(secretKey), "AES");
	    	this.IV = decode(IV);
	    }
	
	    
	

	public static void main(String[] args) {
		try {
            AES aes = new AES();
            //aes.init();
            aes.initFromString("icBUI0jyviHwBTpcHs0i7A==", "hscaSRL/mJExm2ix");

            //String enc = aes.encrypt("Hello word");
           // String dec = aes.decrypt(enc);

           // System.out.println("Encrypted Message is:  " + enc);
           // System.out.println("Decrypted Message is:  " + dec);
           
            //Generate a Secure OTP
            SecureRandom random = new SecureRandom();
            int otpValue = random.nextInt(9000) + 1000;
            String otp = String.valueOf(otpValue);

            // Decrypted OTP = Original OTP
            String encryptedOTP = aes.encrypt(otp);
            String decryptedOTP = aes.decrypt(encryptedOTP);

            System.out.println("OTP: " + otp);
            System.out.println("Encrypted OTP: " + encryptedOTP);
            System.out.println("Decrypted OTP: " + decryptedOTP);
            aes.exportKey();

            // Encryption and Decryption Time
            long startTime = System.nanoTime(); // Measure encryption time
            aes.encrypt(otp);
            long endTime = System.nanoTime();
            long encryptionTime = endTime - startTime;

            startTime = System.nanoTime(); // Measure decryption time
            aes.decrypt(encryptedOTP);
            endTime = System.nanoTime();
            long decryptionTime = endTime - startTime;

            System.out.println("Encryption Time: " + encryptionTime + " nanoseconds");
            System.out.println("Decryption Time: " + decryptionTime + " nanoseconds");

        } catch (Exception Ig) {
            Ig.printStackTrace();
            System.out.println("Decryption failed with incorrect key or IV: " + Ig.getMessage());
        }
    }

}













