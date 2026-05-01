package defaut;

import java.security.SecureRandom;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class otpManager {

    private static final long EXPIRATION_DURATION_MINUTES = 3;
    private static ScheduledExecutorService executorService;
    PreparedStatement prestm;
    
    
    otpManager(){
    	executorService = Executors.newScheduledThreadPool(1);
    }

    public static void generateOtp() {
        // Generate OTP and insert into database
    	 SecureRandom random = new SecureRandom();
		 int otp = random.nextInt(9000) + 1000; 	 
		 System.out.println(otp);
		 
		 try {
			 
			 Connect conn = new Connect();
			 String sql = "INSERT INTO otps(otp) VALUES(?)";
			 PreparedStatement prestm = conn.connectio.prepareStatement(sql);
			
			 prestm.setInt(1, otp);
			 
			 int rowAffected = prestm.executeUpdate();
			 
			 if(rowAffected > 0) {
				 System.out.println("otp: "+otp+ " sent successully");
			 }
			 else {
				 System.out.println("No generated otp");
			 }		 
		 }
		 catch(Exception gE) {
			 gE.printStackTrace();
		 }
        // ...
        // Schedule task to delete expired OTPs after 3 minutes
        executorService.schedule(() -> deleteExpiredOtps(), EXPIRATION_DURATION_MINUTES, TimeUnit.MINUTES);
    }

    private static void deleteExpiredOtps() {
    	
    	
        try {
        	Connect conn = new Connect();
            String sql = "DELETE FROM otps WHERE creation_time < ?";
            try (PreparedStatement pstmt = conn.connectio.prepareStatement(sql)) {
                // Calculate expiration time (current time - 3 minutes)
                Instant expirationTime = Instant.now().minusSeconds(TimeUnit.MINUTES.toSeconds(EXPIRATION_DURATION_MINUTES));
                pstmt.setTimestamp(1, Timestamp.from(expirationTime));
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Deleted " + rowsAffected + " expired OTP(s) from the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting expired OTPs from database: " + e.getMessage());
        }
    }

    public static void shutdown() {
        executorService.shutdown();
    }

    public static void main(String[] args) {
        // Schedule task to delete expired OTPs periodically
        executorService.scheduleAtFixedRate(() -> deleteExpiredOtps(), 0, EXPIRATION_DURATION_MINUTES, TimeUnit.MINUTES);
    }
}
