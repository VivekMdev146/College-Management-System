package college.management.system;
public class ValidationUtils {
    
    // Phone number validation (10 digits)
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }
    
    // Email validation
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    // Aadhar number validation (12 digits)
    public static boolean isValidAadhar(String aadhar) {
        return aadhar != null && aadhar.matches("\\d{12}");
    }
    
    // Get validation error message
    public static String getValidationMessage(String phone, String email, String aadhar) {
        StringBuilder message = new StringBuilder();
        
        if (!isValidPhone(phone)) {
            message.append("Invalid phone number. Must be 10 digits.\n");
        }
        if (!isValidEmail(email)) {
            message.append("Invalid email address.\n");
        }
        if (!isValidAadhar(aadhar)) {
            message.append("Invalid Aadhar number. Must be 12 digits.\n");
        }
        
        return message.toString();
    }
} 