import Vue from 'vue'
import VueRouter from 'vue-router'
import PREVIEW from '@/views/generator/preview.vue'
import CONFIG from '@/views/generator/config.vue'
import INDEX from '@/views/generator/index.vue'

Vue.use(VueRouter)

export default new VueRouter({
    routes: [
        {
            path: '/',
            redirect: '/generator/index'
        },
        {
            path: '/generator/index',
            component: INDEX,
            name: 'INDEX',
        },
        {
            path: '/generator/preview',
            name: 'PREVIEW',
            component: PREVIEW
        },
        {
            path: '/generator/config',
            name: 'CONFIG',
            component: CONFIG
        }
    ]
})
