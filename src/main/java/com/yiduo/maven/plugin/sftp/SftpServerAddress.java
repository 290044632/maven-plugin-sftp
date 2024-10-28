package com.yiduo.maven.plugin.sftp;

public class SftpServerAddress {
    private String host;
    private String username;
    private int port;

    public SftpServerAddress() {
        this("127.0.0.1");
    }

    private SftpServerAddress(String host) {
        this(host, null);
    }

    private SftpServerAddress(String host, int port) {
        this(host, null, port);
    }

    private SftpServerAddress(String host, String username) {
        this(host, username, 22);
    }

    private SftpServerAddress(String host, String username, int port) {
        this.host = host;
        this.username = username;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public int getPort() {
        return port;
    }

    public static SftpServerAddress of(String host) {
        if (null == host || host.isEmpty()) {
            return new SftpServerAddress();
        }
        int hostIndex = host.indexOf("@");
        if (hostIndex == -1) {
            int portIndex = host.indexOf(":");
            if (portIndex == -1) {
                return new SftpServerAddress(host);
            }
            return new SftpServerAddress(host.substring(0, portIndex), Integer.parseInt(host.substring(portIndex + 1)));
        }
        String username = host.substring(0, hostIndex);
        String _host = host.substring(hostIndex + 1);
        int portIndex = _host.indexOf(":");
        if (portIndex == -1) {
            return new SftpServerAddress(_host, username);
        }
        return new SftpServerAddress(_host.substring(0, portIndex), username, Integer.parseInt(_host.substring(portIndex + 1)));
    }
}
