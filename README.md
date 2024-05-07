这是一个非常简单的倒计时服务，但项目使用了较为完整的组织结构，加入了全局异常处理和日志管理等功能，可谓麻雀虽小五脏俱全。后期也可能会加入更多新的功能。

另外，本项目基于Java21+Graalvm开发，支持编译为本地二进制文件，由于不支持交叉编译，我采用Docker容器方式为目标平台进行编译，比如创建一个centos7的容器，在容器中进行编译，容器的构建代码如下：

[centos7](docs/dev-ops/Dockerfile-centos7)

[opencloudos](docs/dev-ops/Dockerfile-opencloudos)

> opencloudos是腾讯云研发的操作系统，与centos8兼容，在腾讯云启动服务器时官方推荐使用，
>
> 不推荐centos7的原因是它已经停止维护了。