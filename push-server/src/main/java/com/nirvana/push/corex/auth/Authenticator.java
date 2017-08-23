package com.nirvana.push.corex.auth;


/**
 *  身份校验
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public interface Authenticator {

    boolean checkValid(String username, String password);
}
