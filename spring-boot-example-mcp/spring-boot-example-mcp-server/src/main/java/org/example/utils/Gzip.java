package org.example.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class Gzip {

    public static byte[] gunzip(byte[] compressed) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(compressed);
                GZIPInputStream gis = new GZIPInputStream(bais);
                ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            byte[] tmp = new byte[4096];
            int len;
            while ((len = gis.read(tmp)) > 0) {
                buffer.write(tmp, 0, len);
            }
            return buffer.toByteArray();
        }
    }

}
