package ec.edu.monster.ms_auth.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilidad paro hash y verificación de contraseñas usando SHA-256
 * Portado del monolito para compatibilidad.
 */
public class PasswordUtils {

    private static final Logger LOGGER = Logger.getLogger(PasswordUtils.class.getName());
    private static final String ALGORITHM = "SHA-256";

    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Algoritmo de hash no disponible: " + ALGORITHM, e);
            throw new RuntimeException("Error al generar hash de contraseña", e);
        }
    }

    public static boolean verificarPassword(String passwordPlano, String hashAlmacenado) {
        if (passwordPlano == null || hashAlmacenado == null) {
            return false;
        }

        String hashCalculado = hashPassword(passwordPlano);
        return hashCalculado.equalsIgnoreCase(hashAlmacenado);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private PasswordUtils() {
        throw new AssertionError("No se puede instanciar esta clase");
    }
}
