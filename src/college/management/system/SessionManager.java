package college.management.system;
/**
 * Utility class to manage user session data across the application
 */
public class SessionManager {
    private static String currentUsername = null;
    
    /**
     * Sets the current logged-in username
     * @param username The username to store in session
     */
    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }
    
    /**
     * Gets the current logged-in username
     * @return The username stored in session, or null if not logged in
     */
    public static String getCurrentUsername() {
        return currentUsername;
    }
    
    /**
     * Checks if a user is currently logged in
     * @return true if a user is logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        return currentUsername != null && !currentUsername.trim().isEmpty();
    }
    
    /**
     * Clears the current user session data
     */
    public static void logout() {
        currentUsername = null;
    }
} 