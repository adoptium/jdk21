/*
 * Copyright (c) 2004, 2023, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package pkg;

/**
 * A class to test sorting of index items.
 *
 * <h2>C</h2>
 *
 * Section "C" should appear right before language elements with the same name.
 *
 * <h3>C/D</h3>
 *
 * Section "C/D" should appear after items named "C" in the index.
 *
 * {@index c/d should appear before the section above}
 */
public class C {

    /**
     * Empty constructor.
     */
    public C() {}

    /**
     * Constructor with a parameter.
     * @param i an int
     */
    public C(int i) {}

    /**
     * Lower case "c" method should appear before upper case "C" elements and sections in index.
     */
    public void c() {}

    /**
     * Should appear after all items named "c" or "C".
     */
    public void c_() {}

    // Test that Java appears before JDK in the index.  The fact
    // that 'D' is uppercase and 'a' is lowercase should make no difference
    // in ordering.
    public static final String JDK = "1.5";
    public static final String Java = "1.5";

}
