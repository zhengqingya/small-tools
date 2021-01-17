import * as filters from "@/filters/index";
import api from "@/api";
import * as utils from "@/utils";
import myComponent from "@/components/index";

import "./components/global"; // 全局组件 删除按钮等
import "./permission_btn";

export default {
  install: Vue => {
    Object.keys(filters).forEach(key => {
      Vue.filter(key, filters[key]);
    });

    Object.keys(myComponent).forEach(key => {
      Vue.component(key, myComponent[key]);
    });

    Vue.prototype.$api = api;
    Vue.prototype.$utils = utils;
  }
};
