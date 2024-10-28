package com.yiduo.maven.plugin.sftp;

import com.jcraft.jsch.Logger;

public class SftpProgressMonitor implements com.jcraft.jsch.SftpProgressMonitor {
    private Logger logger;

    public SftpProgressMonitor(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void init(int op, String src, String dest, long max) {
        logger.log(Logger.INFO, "文件传输开始......");
        logger.log(Logger.INFO, src + " >>>> " + dest);
    }

    @Override
    public boolean count(long count) {
        return false;
    }

    @Override
    public void end() {
        logger.log(Logger.INFO, "文件传输完成......");
    }
}
