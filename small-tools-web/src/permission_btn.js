import Vue from "vue";

Vue.directive("has", {
  inserted: function(el, binding, vnode) {
    // 获取按钮权限
    let btnPermissions = [];
    btnPermissions = vnode.context.$route.meta.btnPermissions;
    if (!Vue.prototype.$_has(btnPermissions, binding.value)) {
      el.parentNode.removeChild(el);
    }
  }
});

// 权限检查方法
Vue.prototype.$_has = function(btnPermissions, value) {
  let isExist = false;
  if (btnPermissions === undefined || btnPermissions == null) {
    return false;
  }
  if (btnPermissions.indexOf(value) > -1) {
    isExist = true;
  }
  return isExist;
};
