package org.example.common.utils;

import java.util.UUID;

import org.example.common.message.Album;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilsTest {

    @Test
    public void testCase1() {

        Album album = new Album();
        album.setAlbumId(UUID.randomUUID().toString());
        album.setAlbumTitle("专辑");
        log.info(JsonUtils.toJsonPretty(album));
        log.info(XmlUtils.toXml(album));

    }

}
