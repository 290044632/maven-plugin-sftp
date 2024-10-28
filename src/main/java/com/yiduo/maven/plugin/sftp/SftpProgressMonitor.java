package com.yiduo.maven.plugin.sftp;

import com.jcraft.jsch.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SftpProgressMonitor implements com.jcraft.jsch.SftpProgressMonitor {
    private static final int PERCENT_STEP = 5;
    private Logger logger;
    private long max;
    private long processed;
    private int nextPercent;
    private boolean firstStep;

    public SftpProgressMonitor(Logger logger) {
        this.logger = logger;
        firstStep = true;
    }

    @Override
    public void init(int op, String src, String dest, long max) {
        logger.log(Logger.INFO, "文件传输开始......");
        logger.log(Logger.INFO, src + " >>>> " + dest);
        this.max = max;
    }

    @Override
    public boolean count(long count) {
        processed += count;
        int percent = new BigDecimal(processed).multiply(new BigDecimal(100))
                .divide(new BigDecimal(this.max), RoundingMode.HALF_UP)
                .intValue();
        if ((percent == 1 && firstStep) || (percent > 1 && percent >= nextPercent)) {
            firstStep = false;
            nextPercent += PERCENT_STEP;
            logger.log(Logger.INFO, "文件传输......(" + processed + "/" + this.max + ")......" + percent + "%");
        }
        return count < this.max;
    }

    @Override
    public void end() {
        logger.log(Logger.INFO, "文件传输完成......");
    }
}
