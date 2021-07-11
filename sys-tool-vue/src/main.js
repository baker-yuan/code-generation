import Vue from 'vue'
import App from './App.vue'
import httpRequest from './utils/httpRequest'

import router from './router'

import 'element-ui/lib/theme-chalk/index.css' // element-ui
import ElementUI from 'element-ui'

Vue.prototype.$http = httpRequest
Vue.config.productionTip = false
Vue.use(ElementUI)

new Vue({
  render: h => h(App),
  router: router
}).$mount('#app')
