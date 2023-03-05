const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  publicPath: "./",
  transpileDependencies: true,
  devServer: {
    proxy: {
      "/apk": {
        target: "后端接口请求地址 例如windows本地：http://localhost:8889/admin/ 服务器：https://域名:8889/admin/ 或 http://ip地址:8889/admin/",
        changeOrigin: true,
        pathRewrite: {
          "^/apk": "",
        },
      },
    },
  },
  configureWebpack: (config) => {
    // 为生产环境修改配置...
    if (process.env.NODE_ENV === "production") {
      config.mode = "production";
      // 打包文件大小配置
      config.performance = {
        maxEntrypointSize: 10000000,
        maxAssetSize: 30000000,
      };
    }
  },
});
