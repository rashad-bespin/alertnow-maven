package com.bespinglobal.alertnow.client;

import org.apache.http.conn.DnsResolver;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TimeoutDnsResolver implements DnsResolver {
    private int timeout;

    public TimeoutDnsResolver(int timeout) {
        this.timeout = timeout;
    }

    public InetAddress[] resolve(String host) throws UnknownHostException {
        DNSResolver dnsRes = new DNSResolver(host);
        Thread t = new Thread(dnsRes);

        try {
            t.start();
            t.join((long)this.timeout);
        } catch (InterruptedException var5) {
            throw new UnknownHostException("DNS resolution of \"" + host + "\" has timed out");
        }

        if (dnsRes.get() == null) {
            throw new UnknownHostException("Unknown host \"" + host + "\"");
        } else {
            return new InetAddress[]{dnsRes.get()};
        }
    }

    public class DNSResolver implements Runnable {
        private String domain;
        private InetAddress inetAddr;

        public DNSResolver(String domain) {
            this.domain = domain;
        }

        public void run() {
            try {
                InetAddress addr = InetAddress.getByName(this.domain);
                this.set(addr);
            } catch (UnknownHostException var2) {
                Thread.currentThread().interrupt();
            }
        }

        private synchronized void set(InetAddress inetAddr) {
            this.inetAddr = inetAddr;
        }

        public synchronized InetAddress get() {
            return this.inetAddr;
        }
    }
}
