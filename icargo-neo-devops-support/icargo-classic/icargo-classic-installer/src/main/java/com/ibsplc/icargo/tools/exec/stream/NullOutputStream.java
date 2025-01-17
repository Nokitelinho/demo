/*
 * Copyright (C) 2019 Ketan Padegaonkar <ketanpadegaonkar@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibsplc.icargo.tools.exec.stream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An OutputStream which ignores everything written to it.
 */
public class NullOutputStream extends OutputStream {

    /**
     * A singleton.
     */
    public static final NullOutputStream NULL_OUTPUT_STREAM = new NullOutputStream();

    public void write(byte[] b, int off, int len) {
        // discard!
    }

    public void write(int b) {
        // discard!
    }

    public void write(byte[] b) throws IOException {
        // discard!
    }
}
