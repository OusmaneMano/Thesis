package defaut;

import java.security.SecureRandom;
import java.sql.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.*;

import javax.swing.JOptionPane;

public class otpDuration {
	PreparedStatement prestm;

    private static final int EXPIRATION_DURATION_MINUTES = 3;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public void generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(9000) + 1000;

        try {
            Connect conn = new Connect();
            String sql = "INSERT INTO otps(otp, creation_time) VALUES (?, ?)";
            prestm = conn.connectio.prepareStatement(sql);
            prestm.setInt(1, otp);
            prestm.setTimestamp(2, Timestamp.from(Instant.now()));

            int rowAffected = prestm.executeUpdate();

            if (rowAffected > 0) {
                System.out.println("OTP generated successfully: " + otp);
                scheduleOtpCleanup();
            } else {
                System.out.println("Failed to generate OTP");
            }
        } catch (Exception gE) {
            gE.printStackTrace();
        }
    }

    private void scheduleOtpCleanup() {
        executorService.schedule(() -> deleteExpiredOtps(), EXPIRATION_DURATION_MINUTES, TimeUnit.MINUTES);
    }

    private void deleteExpiredOtps() {
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

    public void secondAuth() {
        Connect conn = new Connect();
    	passwordPage passwordPage = new passwordPage();
    	//passwordPage.setVisible(false);

        String userN = passwordPage.usernameField.getText();
        String pass = passwordPage.passField.getText();

        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            prestm = conn.connectio.prepareStatement(sql);

            prestm.setString(1, userN);
            prestm.setString(2, pass);

            ResultSet rs = prestm.executeQuery();

            if (rs.next()) {
                generateOtp();
                receiveOTPPage receiveOTPPage = new receiveOTPPage();
                LoginOTP loginOTP = new LoginOTP();
                loginOTP.usernameField.setText(passwordPage.usernameField.getText());
                //loginOTP.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Credentials");
            }
        } catch (Exception SeE) {
            SeE.printStackTrace();
        }
    }
}
