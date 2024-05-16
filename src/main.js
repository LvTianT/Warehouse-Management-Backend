import Vue from 'vue'
import App from './App.vue'
import * as echarts from 'echarts';
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/global.css'
import axios from 'axios'
import VueRouter from 'vue-router'
import router from './router'
import store from './store'
Vue.prototype.$axios = axios
Vue.prototype.$httpUrl = 'http://8.130.208.243:8090'
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(ElementUI, { size: 'small' })
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

