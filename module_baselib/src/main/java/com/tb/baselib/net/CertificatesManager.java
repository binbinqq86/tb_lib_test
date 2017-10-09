package com.tb.baselib.net;

import java.io.InputStream;

import okio.Buffer;

/**
 *
 */
public class CertificatesManager {
    private static final String CER_PAY = "-----BEGIN CERTIFICATE-----\n" +
            "-----END CERTIFICATE-----";
    
    public static InputStream getPayCerInputStream(){
        return new Buffer().writeUtf8(CER_PAY).inputStream();
    }
}
