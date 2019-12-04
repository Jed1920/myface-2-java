package uk.co.techswitch.myface.services;


import org.springframework.stereotype.Service;
import uk.co.techswitch.myface.models.api.password.CreatePassword;
import uk.co.techswitch.myface.models.database.Password;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Service
public class PasswordService extends DatabaseService {

    private String getHash(String password) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private String randomSalt(){
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    public Password getById(long id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM passwords WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Password.class)
                        .one()
        );
    }

    public Password getByUserId(long id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM passwords WHERE user_id = :id")
                        .bind("id", id)
                        .mapToBean(Password.class)
                        .one()
        );
    }

    public Password createPassword(CreatePassword createPassword) throws NoSuchAlgorithmException {
        String salt = randomSalt();

        String hashPassword = getHash(createPassword.getPassword()+salt);

        long id = jdbi.withHandle(handle -> {
                    handle.createUpdate(
                            "INSERT INTO passwords " +
                                    "(user_id, hash_password, salt) " +
                                    "VALUES " +
                                    "(:user_id, :hash_password, :salt)")
                            .bind("user_id", createPassword.getUserId())
                            .bind("hash_password", hashPassword)
                            .bind("salt", salt)
                            .execute();

                    return handle.createQuery("SELECT LAST_INSERT_ID()")
                            .mapTo(Long.class)
                            .one();
                }
        );
        return getById(id);
    }

    public boolean checkPassword(CreatePassword createPassword) throws NoSuchAlgorithmException {
        Password password = getByUserId(createPassword.getUserId());
        String hashPassword = getHash(createPassword.getPassword() + password.getSalt());

        return password.getHashPassword().equals(hashPassword);

    }

}


