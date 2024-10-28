package com.yiduo.maven.plugin.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

@Mojo(name = "sftp")
public class SftpMojo extends AbstractMojo {
    @Parameter(defaultValue = "127.0.0.1")
    private String host;
    @Parameter(defaultValue = ".")
    private String destPath;
    @Parameter
    private String srcPath;
    @Parameter
    private String password;
    @Parameter
    private Hashtable<String, String> config;
    @Component
    private MavenSession mavenSession;

    public SftpMojo() {
        this.config = new Hashtable<>();
//        Properties config = new Properties();
//        config.put("StrictHostKeyChecking", "no");
//        JSch.setConfig("server_host_key", JSch.getConfig("server_host_key") + ",ssh-rsa");
//        JSch.setConfig("PubkeyAcceptedAlgorithms", JSch.getConfig("PubkeyAcceptedAlgorithms") + ",ssh-rsa");

    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Log log = getLog();

            JSch jSch = new JSch();

            SftpServerAddress serverAddress = SftpServerAddress.of(this.host);
            Session session = jSch.getSession(serverAddress.getUsername(), serverAddress.getHost(), serverAddress.getPort());
            session.setConfig(this.config);
            if (StringUtils.isNotBlank(password)) {
                session.setPassword(password);
            }
            session.setUserInfo(new SftpUIKeyboardInteractive());
            SftpLogger logger = new SftpLogger(log);
            session.setLogger(logger);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.cd(destPath);
            sftpChannel.put(this.getSourcePath(this.srcPath, mavenSession.getCurrentProject()), ".", new SftpProgressMonitor(logger));
            sftpChannel.exit();
            sftpChannel.disconnect();

            session.disconnect();
        } catch (Exception e) {
            throw new MojoExecutionException(e);
        }
    }

    private String getSourcePath(String srcPath, MavenProject currentProject) {
        return StringUtils.isBlank(srcPath) ? currentProject.getBuild().getDirectory() + File.separator + currentProject.getBuild().getFinalName() + "." + currentProject.getPackaging() : srcPath;
    }
}
