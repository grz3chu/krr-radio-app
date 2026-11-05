package com.devnull.radio.players.exoplayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExoPlayerWrapperTest {

    @Test
    void extensionForContentType_matchesStreamCodec() {
        assertEquals("mp3", ExoPlayerWrapper.extensionForContentType("audio/mpeg"));
        assertEquals("aac", ExoPlayerWrapper.extensionForContentType("audio/aac"));
        assertEquals("aac", ExoPlayerWrapper.extensionForContentType("audio/aacp"));
        assertEquals("aac", ExoPlayerWrapper.extensionForContentType("audio/x-aac"));
        assertEquals("ogg", ExoPlayerWrapper.extensionForContentType("application/ogg"));
        assertEquals("flac", ExoPlayerWrapper.extensionForContentType("audio/flac"));
    }

    @Test
    void extensionForContentType_ignoresParametersAndCase() {
        assertEquals("aac", ExoPlayerWrapper.extensionForContentType("Audio/AACP; charset=UTF-8"));
    }

    @Test
    void extensionForContentType_defaultsToMp3() {
        assertEquals("mp3", ExoPlayerWrapper.extensionForContentType(null));
        assertEquals("mp3", ExoPlayerWrapper.extensionForContentType("application/octet-stream"));
    }
}
