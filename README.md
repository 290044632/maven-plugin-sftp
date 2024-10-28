# maven-plugin-sftp

一个基于Jsch第三方库实现的支持sftp方式上传文件到远程主机的maven插件

## 三方依赖

### jsch

一个基于Java实现的SSH库。

官方地址：[http://www.jcraft.com/jsch/](http://www.jcraft.com/jsch/)

Github地址：[https://github.com/mwiede/jsch](https://github.com/mwiede/jsch)

maven依赖：

```xml

<dependency>
    <groupId>com.github.mwiede</groupId>
    <artifactId>jsch</artifactId>
    <version>0.2.20</version>
</dependency>
```
## 配置参数

| 参数名称     | 参数格式                 | 是否必填 | 默认值                                                         | 描述                              | 示例值                                                       |
|----------|----------------------|------|-------------------------------------------------------------|---------------------------------|-----------------------------------------------------------|
| host     | [username@]ip[:port] |      | 127.0.0.1                                                   | ssh目标主机地址                       | zs@192.168.0.1:2222                                       |
| destPath | string               |      | .                                                           | 目标目录，默认当前目录                     | /usr/local/servers                                        |
| srcPath  | string               |      | ${project.dir}/${module.dir}/target/${finalName}.${packing} | 被上传的原始文件，默认值当前项目下当前模块的packing文件 | example-parent/example-intf/target/example-intf.1.0.0.jar |
| config   | Map<String,String>   |      |                                                             | 其他ssh连接相关配置项                    | StrictHostKeyChecking：no                                  ||


## 安装方法

```xml

<build>
    <plugins>
        <plugin>
            <groupId>com.yiduo.maven.plugin</groupId>
            <artifactId>maven-plugin-sftp</artifactId>
            <version>${version:1.0.0.20241028}</version>
            <!-- 配置项参考配置参数列表 -->
            <configuration>
                <host>test@192.168.0.180:2222</host>
                <destPath>test/rsrlogin/rsrlogin</destPath>
                <config>
                    <StrictHostKeyChecking>no</StrictHostKeyChecking>
                    <server_host_key>ssh-rsa</server_host_key>
                    <PubkeyAcceptedAlgorithms>ssh-rsa</PubkeyAcceptedAlgorithms>
                </config>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## 历史版本

### v1.0.0.20241028

- 新增：支持sftp方式上传文件
- 新增：支持跳板机