import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';							// 引入ElementUI组件库
import 'element-ui/lib/theme-chalk/index.css';	// 引入ElementUI全部样式
import VueRouter from 'vue-router'	// 引入VueRouter
import router from './router/index.js'				// 引入路由器
import store from './store/index.js'	// 引入store
import axios from 'axios'
import VueAxios from 'vue-axios'

// 配置请求的根路径
axios.defaults.baseURL= 'http://localhost:14514'
Vue.prototype.$http = axios

Vue.use(ElementUI)	// 使用ElementUI
Vue.use(VueRouter)  // 使用vue router
Vue.use(VueAxios,axios)  

//通过axios请求拦截器添加token，保证拥有获取数据的权限 课程p33
axios.interceptors.request.use(config=>{
  // config是请求对象
  console.log(config)
  config.headers.Authorization = window.sessionStorage.getItem('token')
  return config
})


Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
  store,
  beforeCreate() {
		Vue.prototype.$bus = this
	}
}).$mount('#app')
