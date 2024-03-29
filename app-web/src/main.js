import './assets/main.scss'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'vant/lib/index.css'
import jsInterfaceAsyncMethodCallbackUtils from '@/utils/androidJsInterfaces/asyncSupport/callback'

jsInterfaceAsyncMethodCallbackUtils.exposeToGlobal()

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)

app.mount('#app')
