/*
 * Copyright (c)  2016-2019 https://www.thecoderscorner.com (Nutricherry LTD).
 * This product is licensed under an Apache license, see the LICENSE file in the top-level directory.
 *
 */

package com.thecoderscorner.menu.pluginapi;

import java.util.Objects;

/**
 * Defines an embedded platform. For example boardId of ARDUINO defines the 8 bit AVR based Arduino. Whereas
 * ARDUINO32 defines 32 bit SAMD variants. These embedded platforms can be loaded up at runtime and the list
 * below should not be relied upon to be the full set.
 */
public class EmbeddedPlatform {


    /** Defines the AVR based 8 bit platform */
    public final static EmbeddedPlatform ARDUINO_AVR = new EmbeddedPlatform("Arduino AVR/Uno/Mega", "ARDUINO", true);
    /** Defines the 32 bit arduino platform */
    public final static EmbeddedPlatform ARDUINO32 = new EmbeddedPlatform("Arduino SAMD", "ARDUINO32", false);
    /** Defines the 32 bit arduino platform */
    public final static EmbeddedPlatform ARDUINO_ESP8266 = new EmbeddedPlatform("Arduino ESP8266", "ARDUINO_ESP8266", true);
    public final static EmbeddedPlatform ARDUINO_ESP32 = new EmbeddedPlatform("Arduino ESP32", "ARDUINO_ESP32", true);


    private final String friendlyName;
    private final String boardId;
    private final boolean usesProgmem;

    public EmbeddedPlatform(String friendlyName, String boardId, boolean usesProgmem) {
        this.friendlyName = friendlyName;
        this.boardId = boardId;
        this.usesProgmem = usesProgmem;
    }

    @Override
    public String toString() {
        return friendlyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmbeddedPlatform that = (EmbeddedPlatform) o;
        return Objects.equals(boardId, that.boardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId);
    }

    public String getBoardId() {
        return boardId;
    }

    public boolean isUsesProgmem() {
        return usesProgmem;
    }
}
