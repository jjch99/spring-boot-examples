#!/usr/bin/env bash

##############################################

CA_CN=kafka-ca
CA_KS=kafka-ca
CA_KS_PASS=ca1234567

# 生成CA密钥对
keytool -genkeypair \
        -keystore ${CA_KS}.jks -storepass ${CA_KS_PASS} -storetype pkcs12 \
        -alias ${CA_CN} -validity 3650 -dname CN=${CA_CN},C=CN -ext bc:c

# 导出CA证书
keytool -exportcert -keystore ${CA_KS}.jks -storepass ${CA_KS_PASS} -alias ${CA_CN} -rfc -file ${CA_CN}.cer

# 查看CA证书
keytool -list -keystore ${CA_KS}.jks -storepass ${CA_KS_PASS}
keytool -printcert -file ${CA_CN}.cer

##############################################

SERVER_CN=kafka-server
SERVER_KS=kafka-server-ks
SERVER_TS=kafka-server-ts
SERVER_KS_PASS=server1234567

# 生成服务器密钥对
keytool -genkeypair \
        -keystore ${SERVER_KS}.jks -storepass ${SERVER_KS_PASS} -storetype pkcs12 \
        -alias ${SERVER_CN} -keypass ${SERVER_KS_PASS} \
        -validity 3650 -dname CN=${SERVER_CN},C=CN

# 生成证书请求
keytool -certreq \
        -keystore ${SERVER_KS}.jks -storepass ${SERVER_KS_PASS} \
        -alias ${SERVER_CN} -keypass ${SERVER_KS_PASS} -file ${SERVER_CN}.csr

# CA签名
keytool -gencert \
        -keystore ${CA_KS}.jks -storepass ${CA_KS_PASS} -alias ${CA_CN} -keypass ${CA_KS_PASS} \
        -validity 365 -infile ${SERVER_CN}.csr -outfile ${SERVER_CN}.cer

# 查看
keytool -printcert -file ${SERVER_CN}.cer

# 导入CA证书到truststore
keytool -importcert -keystore ${SERVER_TS}.jks -storepass ${SERVER_KS_PASS} -alias ${CA_CN} -file ${CA_CN}.cer -noprompt

# 导入CA证书到keystore
keytool -importcert -keystore ${SERVER_KS}.jks -storepass ${SERVER_KS_PASS} -alias ${CA_CN} -file ${CA_CN}.cer -noprompt

# 列出keystore有那些密钥证书
keytool -list -keystore ${SERVER_KS}.jks -storepass ${SERVER_KS_PASS}
keytool -list -keystore ${SERVER_TS}.jks -storepass ${SERVER_KS_PASS}

##############################################

CLIENT_CN=kafka-client
CLIENT_KS=kafka-client-ks
CLIENT_TS=kafka-client-ts
CLIENT_KS_PASS=client1234567

# 生成客户端密钥对
keytool -genkeypair \
        -keystore ${CLIENT_KS}.jks -storepass ${CLIENT_KS_PASS} -storetype pkcs12 \
        -alias ${CLIENT_CN} -keypass ${CLIENT_KS_PASS} -validity 3650 -dname CN=${CLIENT_CN},C=CN

# 生成证书请求
keytool -certreq \
        -keystore ${CLIENT_KS}.jks -storepass ${CLIENT_KS_PASS} \
        -alias ${CLIENT_CN} -keypass ${CLIENT_KS_PASS} -file ${CLIENT_CN}.csr

# CA签名
keytool -gencert \
        -keystore ${CA_KS}.jks -storepass ${CA_KS_PASS} -alias ${CA_CN} -keypass ${CA_KS_PASS} \
        -validity 365 -infile ${CLIENT_CN}.csr -outfile ${CLIENT_CN}.cer

# 查看证书
keytool -printcert -file ${CLIENT_CN}.cer

# 导入CA证书到truststore
keytool -importcert -keystore ${CLIENT_TS}.jks -storepass ${CLIENT_KS_PASS} -alias ${CA_CN} -file ${CA_CN}.cer -noprompt

# 导入CA证书到keystore
keytool -importcert -keystore ${CLIENT_KS}.jks -storepass ${CLIENT_KS_PASS} -alias ${CA_CN} -file ${CA_CN}.cer -noprompt

# 列出keystore有那些密钥证书
keytool -list -keystore ${CLIENT_KS}.jks -storepass ${CLIENT_KS_PASS}
keytool -list -keystore ${CLIENT_TS}.jks -storepass ${CLIENT_KS_PASS}

##############################################

# 导入Server证书到Client的truststore
keytool -importcert -keystore ${CLIENT_TS}.jks -storepass ${CLIENT_KS_PASS} -alias ${SERVER_CN} -file ${SERVER_CN}.cer -noprompt
keytool -list -keystore ${CLIENT_TS}.jks -storepass ${CLIENT_KS_PASS}

# 导入Client证书到Server的truststore
keytool -importcert -keystore ${SERVER_TS}.jks -storepass ${SERVER_KS_PASS} -alias ${CLIENT_CN} -file ${CLIENT_CN}.cer -noprompt
keytool -list -keystore ${SERVER_TS}.jks -storepass ${SERVER_KS_PASS}
