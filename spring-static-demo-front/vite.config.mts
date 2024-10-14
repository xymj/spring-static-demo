import react from "@vitejs/plugin-react-swc";
import dotenv from "dotenv";
import path from "path";
import { defineConfig } from "vite";
import svgr from "vite-plugin-svgr";
import tsconfigPaths from "vite-tsconfig-paths";

export default defineConfig(({ mode }) => {
  dotenv.config({ path: path.resolve(__dirname, "../../.env") });

  const apiRoutes = ["^/api/v1/", "/health"];

  // Use environment variable to determine the target.
  const target = process.env.VITE_PROXY_TARGET || "http://127.0.0.1:8080";

  // Use environment variable to determine the UI server port
  const port = Number(process.env.VITE_PORT) || 3000;

  const proxyTargets = apiRoutes.reduce((proxyObj, route) => {
    proxyObj[route] = {
      target: target,
      changeOrigin: true,
      secure: false,
      ws: true,
    };
    return proxyObj;
  }, {});

  return {
    build: {
      outDir: "build",
    },
    define: {
      "process.env.BACKEND_URL": JSON.stringify(process.env.BACKEND_URL),
    },
    plugins: [react(), svgr(), tsconfigPaths()],
    server: {
      port: port,
      proxy: {
        ...proxyTargets,
      },
    },
  };
});

/**
 * 这段 TypeScript 代码是 Vite 构建工具的配置文件，用于设置项目构建、开发服务器以及代理规则等。下面逐行解析：

首先导入了多个模块：@vitejs/plugin-react-swc 是一个插件，用于支持 React 和 SWC 编译；dotenv 用来加载环境变量；path 是 Node.js 的内置模块，用于处理路径；defineConfig 函数来自 vite 包，用于定义 Vite 的配置对象；svgr 和 vite-tsconfig-paths 分别是 Vite 插件，前者用于处理 SVG 图标，后者用于解决 TypeScript 路径映射。

接下来导出了默认配置函数 defineConfig，它接收一个参数 {mode}，表示当前运行模式（如 'development' 或 'production'）。

使用 dotenv.config() 加载 .env 文件中的环境变量，路径为相对此文件的上两级目录下的 .env 文件。

定义了一个数组 apiRoutes，包含了需要被代理到后端服务的 API 路由前缀。

根据环境变量确定目标代理地址和前端服务器端口，默认分别为 'http://127.0.0.1:7860' 和 3000。

使用 reduce 方法创建一个代理配置对象 proxyTargets，其中键为 apiRoutes 中的路由前缀，值为对应的代理配置对象，包括目标 URL (target)、是否改变源信息 (changeOrigin)、是否启用安全连接 (secure) 和是否支持 WebSocket (ws)。

最终返回的配置对象包含以下属性：

build: 指定输出目录为 "build"。
define: 允许在编译时注入全局变量，这里将 process.env.BACKEND_URL 注入到全局环境中。
plugins: 插件列表，包括 React 支持、SVG 处理和 TypeScript 路径映射。
server: 开发服务器配置，指定端口号并应用上述创建的代理规则。
整个配置文件的核心在于根据环境变量动态生成代理配置，并整合各种插件以优化开发体验和构建过程。
 */