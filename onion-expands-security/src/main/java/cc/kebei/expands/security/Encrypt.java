package cc.kebei.expands.security;

import cc.kebei.expands.security.rsa.RSAEncrypt;

/**
 * @author Kebei
 */
public abstract class Encrypt {

    public static RSAEncrypt rsa() {
        return RSAEncrypt.get();
    }
}
