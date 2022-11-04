package club.anims.jnotes2.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class BcryptService {
    private final BCrypt.Hasher hasher;
    private final BCrypt.Verifyer verifyer;

    public BcryptService() {
        hasher = BCrypt.withDefaults();
        verifyer = BCrypt.verifyer();
    }

    public String hashPassword(String password) {
        return hasher.hashToString(12, password.toCharArray());
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return verifyer.verify(password.toCharArray(), hashedPassword).verified;
    }
}
