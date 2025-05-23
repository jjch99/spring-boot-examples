
Mac 安装 protoc
```bash
brew install protobuf
brew install autoconf automake libtool
```

手动编译 .proto 文件
```bash
protoc --version
protoc Demo.proto --python_out=./ --java_out=./
```

maven编译 .proto 文件，见`pom.xml`
