package com.why.satoken.utils;

import cn.dev33.satoken.secure.SaSecureUtil;

public class KeyUtils {
    public void genKey() throws Exception {
        System.out.println(SaSecureUtil.rsaGenerateKeyPair());
        return;
    }
}
