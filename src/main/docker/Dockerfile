# 基础镜像使用java
FROM java:8

# 作者
MAINTAINER zhangxq <285471906@qq.com>

# VOLUME 指定了临时文件目录为/tmp。其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp

# 将jar包添加到容器中并更名为app.jar
ADD zxq-cloud-admin-0.0.1-SNAPSHOT app.jar

# 设置 JAVA_OPS
ENV JAVA_OPTS=""

# 容器运行命令
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]

# 端口暴露
EXPOSE 8090