package com.sda.finalbackend.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Utils {

    private static Utils instance;

    private Utils()
    {

    }

    public static Utils getInstance()
    {
        if(instance==null)
        {
            instance=new Utils();
        }

        return  instance;
    }

    /**
     * This method it is used to encrypt the password before save it in database
     *
     * @param password - clear password
     * @return - encrypted password
     */
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String bdPassword) {
        return BCrypt.checkpw(password, bdPassword);
    }
}
