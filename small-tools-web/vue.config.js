// const name = defaultSettings.title || 'Small Tools' // page title
// const port = process.env.port || process.env.npm_config_port || 80 // dev port

module.exports = {
  publicPath: "",
  outputDir: "dist",
  assetsDir: "static",
  lintOnSave: false,
  productionSourceMap: false,
  runtimeCompiler: true,
  devServer: {
    proxy: {
      "/": {
        target: "http://www.zhengqingya.com:1218", // 代理地址，这里设置的地址会代替axios中设置的baseURL
        ws: true, //如果要代理 websockets，配置这个参数
        secure: false, // 如果是https接口，需要配置这个参数
        changeOrigin: true, //是否跨域
        // pathRewrite方法重写url
        pathRewrite: {
          "^/": "/"
        }
      }
    }
  },
  configureWebpack: {
    // 关闭 webpack 的性能提示
    performance: {
      hints: false
    }
  },
  css: {
    loaderOptions: {
      sass: {
        additionalData: `@import "@/styles/dark_theme.scss";`
      }
    }
  }
};
