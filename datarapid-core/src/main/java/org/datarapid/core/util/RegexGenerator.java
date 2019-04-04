/*
 * Copyright 2018 Infosys Ltd.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.datarapid.core.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description This class generates the data from the input regex pattern.
 */
public class RegexGenerator {

    public StringBuilder generateFromRegex(String regex) {
        Random random = new Random();
        StringBuilder b = new StringBuilder();

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher("");

        int[] cypher = new int[95];
        boolean done = false;

        // start from an empty string and grow a solution
        while (!done) {
            // make a cypher to jumble the order letters are tried
            for (int i = 0; i < 95; i++) {
                cypher[i] = i;
            }

            for (int i = 0; i < 95; i++) {
                int n = random.nextInt(95 - i) + i;

                int t = cypher[n];
                cypher[n] = cypher[i];
                cypher[i] = t;
            }

            // try and grow partial solution using an extra letter on the end
            for (int i = 0; i < 95; i++) {
                int n = cypher[i] + 32;
                b.append((char) n);

                String result = b.toString();
                m.reset(result);
                if (m.matches()) { // complete solution found
                    // don't try to expand to a larger solution
                    if (!random.nextBoolean()) {
                        done = true;
                    }

                    break;
                } else if (m.hitEnd()) { // prefix to a solution found, keep
                    // this letter
                    break;
                } else { // dead end found, try a new character at the end
                    b.deleteCharAt(b.length() - 1);

                    // no more possible characters to try and expand with - stop
                    if (i == 94) {
                        done = true;
                    }
                }
            }
        }
        return new StringBuilder(b.toString());
    }

}
