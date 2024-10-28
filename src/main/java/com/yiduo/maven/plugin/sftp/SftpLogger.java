package com.yiduo.maven.plugin.sftp;

import com.jcraft.jsch.Logger;
import org.apache.maven.plugin.logging.Log;

public class SftpLogger implements Logger {
    private Log logger;

    public SftpLogger(Log logger) {
        this.logger = logger;
    }

    @Override
    public boolean isEnabled(int level) {
        return true;
    }

    @Override
    public void log(int level, String message) {
        String l = "";
        switch (level) {
            case DEBUG:
                l = "DEBUG";
                logger.debug(message);
                break;
            case INFO:
                l = "INFO";
                logger.info(message);
                break;
            case WARN:
                l = "WARN";
                logger.warn(message);
                break;
            case ERROR:
                l = "ERROR";
                logger.error(message);
                break;
        }
    }
}
