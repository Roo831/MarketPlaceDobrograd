package com.poptsov.marketplace.util;

import com.poptsov.marketplace.exceptions.AuthorizationException;

import java.util.Objects;

public class AuthorityCheckUtil {

    public static void checkAuthorities(Integer contextId, Integer id) throws AuthorizationException {
        if(!Objects.equals(contextId, id))
        {
            throw new AuthorizationException("Owner id does not match with your id");
        }
    }
}
