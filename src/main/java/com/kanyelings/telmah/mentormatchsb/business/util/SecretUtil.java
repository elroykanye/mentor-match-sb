package com.kanyelings.telmah.mentormatchsb.business.util;

import com.kanyelings.telmah.mentormatchsb.config.Constants;

public class SecretUtil {
    public static boolean validSecret(String given) {
        return given.equals(Constants.KANYE_SECRET_KEY);
    }
}
