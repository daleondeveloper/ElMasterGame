package com.daleondeveloper.Exception;

import java.io.IOException;

public class NoPushBlocks extends IOException {

    public NoPushBlocks() {
        super("No blocks to push");
    }
}
