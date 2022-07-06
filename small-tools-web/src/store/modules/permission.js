import { asyncRoutes, constantRoutes } from '@/router'
import Layout from '@/components/Layout'
import parentView from '@/components/Layout/parentView'
/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some((role) => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach((route) => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}
/**
 * 根据后台返回的侧边栏权限路由表生成新的路由表
 * @param {arr} roleMenus
 */
function createRouter(roleMenus) {
  const accessedRoutes = []
  roleMenus.length &&
    roleMenus.forEach((menu) => {
      var component = null
      if (menu.component === 'Layout') {
        component = Layout
      } else if (menu.component === 'parentView') {
        component = parentView
      } else if (menu.component) {
        try {
          component = require('@/views/' + menu.component + '.vue').default
        } catch (err) {
          console.log(err)
          component = null
        }
      } else {
        component = null
      }
      if (component != null) {
        var childRouter = []
        if (menu.children.length > 0) {
          childRouter = createRouter(menu.children)
        }
        if (childRouter.length) {
          accessedRoutes.push({
            path: menu.path,
            component: component,
            redirect: menu.redirect || false,
            name: menu.name,
            meta: {
              title: menu.title,
              icon: menu.icon, // 菜单左侧的icon图标
              breadcrumb: menu.breadcrumb, // boolen
              btnPermissions: menu.meta.btnPermissions,
            },
            children: childRouter,
            hidden: menu.hidden,
            alwaysShow: menu.alwaysShow,
          })
        } else {
          accessedRoutes.push({
            path: menu.path,
            component: component,
            redirect: menu.redirect || false,
            name: menu.name,
            meta: {
              title: menu.title,
              icon: menu.icon,
              breadcrumb: menu.breadcrumb, // boolen
              btnPermissions: menu.meta.btnPermissions,
            },
            // children: childRouter,
            hidden: menu.hidden,
            alwaysShow: menu.alwaysShow,
          })
        }
      }
    })
  return accessedRoutes
}

const state = {
  routes: [],
  addRoutes: [],
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  },
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise((resolve) => {
      const accessedRoutes = createRouter(roles).concat(asyncRoutes)
      // console.log(accessedRoutes)
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
}
