import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'

import '@/components/SvgIcon/icons' // svg图标
// import "./styles/element-variables.scss"; //element样式
//import "./styles/theme/index.css";
import 'normalize.css/normalize.css' //css样式初始化
import '@/styles/index.scss' // 全局 css

import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/cus-dark-theme.scss'
import Vddl from 'vddl'
import moment from 'moment'
import '@/permission' // permission control
import 'video.js/dist/video-js.css' // 引入样式
import videojs from 'video.js'
import video_zhCN from 'video.js/dist/lang/zh-CN.json'

videojs.addLanguage('zh-CN', video_zhCN)

import myMix from './utils/mixin' // 抽取公用的实例 - 操作成功与失败消息提醒内容等
Vue.mixin(myMix)
Vue.use(Vddl)

import inject from './inject'
Vue.use(inject)

Vue.config.productionTip = false

Vue.use(ElementUI, {
  size: 'small',
})

// $t是i18n使用的翻译函数 -> 可实现多语言切换
import i18n from './lang' // Internationalization
import './errorLog' // error log
import './permission' // permission control

Object.defineProperty(Vue.prototype, '$moment', { value: moment })

import _ from 'lodash'

Vue.prototype._ = _

/* ================================= zq: start ==================================== */

import checkBtnPermission from '@/utils/permission' // 权限判断函数 ： this.global_checkBtnPermission()
import waves from '@/directive/waves' // Waves directive
Vue.use(checkBtnPermission)
Vue.use(waves)

// 导入代码高亮文件
import hljs from 'highlight.js'
// 导入代码高亮样式
import 'highlight.js/styles/monokai-sublime.css'
// 自定义一个代码高亮指令
Vue.directive('highlight', function (el) {
  const highlight = el.querySelectorAll('pre code')
  highlight.forEach((block) => {
    hljs.highlightBlock(block)
  })
})

// 引入全局函数方法1 -> 通过this.httpTest() 调用
Vue.prototype.httpTest = function () {
  alert('执行成功 - 全局函数')
}

// 引入全局函数方法2 -> 抽取
import zqTestMethod from '@/utils/globalFunction'

Vue.use(zqTestMethod)

import MAceEditor from 'vue-m-ace-editor'
import 'brace'
import 'brace/mode/markdown'
import 'brace/mode/javascript'
import 'brace/theme/clouds_midnight'
Vue.use(MAceEditor) // 注册 m-ace-editor 组件

/* ================================== zq: end =================================== */

new Vue({
  el: '#app',
  router,
  store,
  i18n,
  render: (h) => h(App),
})
