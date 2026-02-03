package ec.edu.monster.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilidad para hash y verificación de contraseñas usando SHA-256
 * Compatible con JDK 17+
 * 
 * @author EurekaBank
 */
public class PasswordUtils {

    private static final Logger LOGGER = Logger.getLogger(PasswordUtils.class.getName());
    private static final String ALGORITHM = "SHA-256";

    /**
     * Genera un hash SHA-256 de la contraseña proporcionada
     * 
     * @param password Contraseña en texto plano
     * @return Hash hexadecimal de 64 caracteres, o null si hay error
     */
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

    /**
     * Verifica si una contraseña en texto plano coincide con un hash almacenado
     * 
     * @param passwordPlano  Contraseña ingresada por el usuario
     * @param hashAlmacenado Hash almacenado en la base de datos
     * @return true si la contraseña es correcta, false en caso contrario
     */
    public static boolean verificarPassword(String passwordPlano, String hashAlmacenado) {
        if (passwordPlano == null || hashAlmacenado == null) {
            return false;
        }

        String hashCalculado = hashPassword(passwordPlano);
        return hashCalculado.equalsIgnoreCase(hashAlmacenado);
    }

    /**
     * Convierte un array de bytes a su representación hexadecimal
     * 
     * @param bytes Array de bytes a convertir
     * @return String hexadecimal
     */
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

    // Constructor privado para evitar instanciación
    private PasswordUtils() {
        throw new AssertionError("No se puede instanciar esta clase");
    }
}
