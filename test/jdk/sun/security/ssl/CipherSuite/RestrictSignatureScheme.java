/*
 * Copyright (c) 2019, 2023, Oracle and/or its affiliates. All rights reserved.
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

/*
 * @test
 * @bug 8226374
 * @library /javax/net/ssl/templates
 * @summary Restrict signature algorithms and named groups
 * @run main/othervm RestrictSignatureScheme
 */

import java.security.Security;
import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLException;

public class RestrictSignatureScheme extends SSLSocketTemplate {

    private static volatile int index;
    private static final String[][][] protocols = {
        {{"TLSv1.3"}, {"TLSv1.3"}},
        {{"TLSv1.3", "TLSv1.2"}, {"TLSv1.2"}},
        {{"TLSv1.3", "TLSv1.2"}, {"TLSv1.2"}},
        {{"TLSv1.2"}, {"TLSv1.3", "TLSv1.2"}},
        {{"TLSv1.2"}, {"TLSv1.2"}}
    };

    private final SSLContext context;
    RestrictSignatureScheme() throws Exception {
        this.context = createSSLContext(
                new Cert[]{Cert.EE_RSASSA_PSS},
                new Cert[]{Cert.EE_RSASSA_PSS},
                new ContextParameters("TLS", "PKIX", "NewSunX509")
        );
    }

    @Override
    public SSLContext createClientSSLContext() throws Exception {
        return context;
    }

    @Override
    public SSLContext createServerSSLContext() throws Exception {
        return context;
    }

    // Servers are configured before clients, increment test case after.
    @Override
    protected void configureClientSocket(SSLSocket socket) {
        String[] ps = protocols[index][0];

        System.out.print("Setting client protocol(s): ");
        Arrays.stream(ps).forEachOrdered(System.out::print);
        System.out.println();

        socket.setEnabledProtocols(ps);
        socket.setEnabledCipherSuites(new String[] {
            "TLS_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256"});
    }

    @Override
    protected void configureServerSocket(SSLServerSocket serverSocket) {
        String[] ps = protocols[index][1];

        System.out.print("Setting server protocol(s): ");
        Arrays.stream(ps).forEachOrdered(System.out::print);
        System.out.println();

        serverSocket.setEnabledProtocols(ps);
        serverSocket.setEnabledCipherSuites(new String[] {
            "TLS_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256"});
    }

    /*
     * Run the test case.
     */
    public static void main(String[] args) throws Exception {
        Security.setProperty("jdk.tls.disabledAlgorithms", "RSASSA-PSS");

        for (index = 0; index < protocols.length; index++) {
            try {
                (new RestrictSignatureScheme()).run();
            } catch (SSLException | IllegalStateException ssle) {
                // The named group should be restricted.
                continue;
            }

            throw new Exception("The test case should be disabled");
        }
    }
}
