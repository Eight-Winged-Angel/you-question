const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  publicPath: '/admin/',
  assetsDir: 'static',
  productionSourceMap: false,
  transpileDependencies: true,
  lintOnSave: false,//关闭语法检查
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:14514',
        pathRewrite: { '^/api': '' },
        changeOrigin: true,
        ws: true,
      }
    }
  },
})
