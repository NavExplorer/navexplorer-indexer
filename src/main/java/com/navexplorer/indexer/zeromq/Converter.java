package com.navexplorer.indexer.zeromq;

import java.util.Formatter;

class Converter {
    static String bin2hex(byte[] bytes) {
        try (Formatter f = new Formatter()) {
            for (byte c : bytes) {
                f.format("%02X", c);
            }
            return f.toString().toLowerCase();
        }
    }
}
