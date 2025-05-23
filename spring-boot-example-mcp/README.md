# Model Context Protocol (MCP)
- https://modelcontextprotocol.io

# Spring AI MCP
- https://docs.spring.io/spring-ai/reference/api/mcp/mcp-overview.html
- https://github.com/spring-projects/spring-ai-examples/tree/main/model-context-protocol

# 使用 [modelcontextprotocol/inspector](https://github.com/modelcontextprotocol/inspector) 测试 MCP Server

安装 Node.js

<!-- 可以通过 nvm 安装管理多个 node 版本 -->
<!-- https://github.com/nvm-sh/nvm -->

```bash
# 
# https://nodejs.org/en/download
# 
# MacOS Intel CPU
wget https://nodejs.org/dist/v22.15.1/node-v22.15.1-darwin-x64.tar.gz -O node-v22.15.1.tar.gz
# MacOS M series
wget https://nodejs.org/dist/v22.15.1/node-v22.15.1-darwin-arm64.tar.gz -O node-v22.15.1.tar.gz

tar zxvf node-v22.15.1.tar.gz -C $HOME

```

运行 inspector

```bash

NODE_DIR=$(ls -d $HOME/node-v22.15.1-*/ | head -1) export PATH="${NODE_DIR}bin:$PATH"

npx -y @modelcontextprotocol/inspector

```
