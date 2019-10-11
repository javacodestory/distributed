FROM java:8
COPY ./target/research-eureka-server-1.0.0.jar /app/research-eureka-server-1.0.0.jar
EXPOSE 8761
ENTRYPOINT [ "java","-jar","/app/research-eureka-server-1.0.0.jar" ]
MAINTAINER liaojunyong
# 构建方法
# docker build -t eureka:test .
# -t 表示使用指定的 image 名称