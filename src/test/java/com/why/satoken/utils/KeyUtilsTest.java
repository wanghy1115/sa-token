package com.why.satoken.utils;

import cn.dev33.satoken.secure.SaSecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KeyUtilsTest {

    @Test
    void genKey() throws Exception {
        HashMap<String, String> stringStringHashMap = SaSecureUtil.rsaGenerateKeyPair();

        System.out.println(stringStringHashMap.get("private"));
        System.out.println(stringStringHashMap.get("public"));
    }
}