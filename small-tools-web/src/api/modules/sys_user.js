import request from '@/utils/request'

const BASE_API = '/system/web/api/user'

export default {
  login(username, password) {
    return request({
      url: 'http://127.0.0.1:1218/auth/oauth/token?client_id=web&client_secret=123456&code=666&grant_type=password',
      method: 'get',
      params: {
        username: username,
        password: password,
      },
      headers: {
        Authorization: 'Basic d2ViOjEyMzQ1Ng==',
      },
    })
  },
  getUserPerm() {
    return request({
      url: BASE_API + '/getUserPerm',
      method: 'get',
      // params: { systemSource: 0 }
    })
  },
  logout() {
    return request({
      url: 'http://127.0.0.1:1218/auth/oauth/logout',
      method: 'DELETE',
    })
  },
  // ------------------------ 上面为用户登录所需 -----------------------------
  listPage(query, headers) {
    return request({
      url: BASE_API + '/listPage',
      method: 'get',
      params: query,
      headers,
    })
  },
  // list(query) {
  //   return request({
  //     url: BASE_API + "/list",
  //     method: "get",
  //     params: query
  //   });
  // },
  add(data) {
    return request({
      url: BASE_API,
      method: 'post',
      data,
    })
  },
  update(data) {
    return request({
      url: BASE_API,
      method: 'put',
      data,
    })
  },
  delete(id) {
    return request({
      url: BASE_API,
      method: 'delete',
      params: { userId: id },
    })
  },
  updateStatus(id, status) {
    return request({
      url: BASE_API + '/updateStatus',
      method: 'post',
      data: { userId: id, status: status },
    })
  },
  resetPassword(userId) {
    return request({
      url: BASE_API + '/resetPassword',
      method: 'get',
      params: { userId: userId },
    })
  },
  getUserInfoById(userId) {
    return request({
      url: BASE_API + '',
      method: 'get',
      params: {
        userId: userId,
      },
    })
  },
  // 保存用户角色
  saveRoleIds(data) {
    return request({
      url: BASE_API + '/saveRoleIds',
      method: 'post',
      data: data,
    })
  },
  // 修改密码
  updatePassword(data) {
    return request({
      url: BASE_API + '/updatePassword',
      method: 'put',
      data: data,
    })
  },
}
