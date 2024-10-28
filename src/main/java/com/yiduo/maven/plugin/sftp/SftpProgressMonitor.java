package com.yiduo.maven.plugin.sftp;

import com.jcraft.jsch.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicLong;

public class SftpProgressMonitor implements com.jcraft.jsch.SftpProgressMonitor {
    private Logger logger;
    private long max = 0l;
    private AtomicLong atomicLong;

    public SftpProgressMonitor(Logger logger) {
        this.logger = logger;
        atomicLong = new AtomicLong(0L);
    }

    @Override
    public void init(int op, String src, String dest, long max) {
        logger.log(Logger.INFO, "文件传输开始......" + max);
        logger.log(Logger.INFO, src + " >>>> " + dest);
        this.max = max;
    }

    @Override
    public boolean count(long count) {
        long ftpCount = atomicLong.addAndGet(count);
        BigDecimal percent = new BigDecimal(ftpCount).multiply(new BigDecimal(100)).divide(new BigDecimal(this.max), RoundingMode.HALF_UP);
        logger.log(Logger.INFO, "文件传输......(" + ftpCount + "/" + this.max + ")......" + percent + "%");
        return count < this.max;
    }

    @Override
    public void end() {
        logger.log(Logger.INFO, "文件传输完成......");
    }
}
