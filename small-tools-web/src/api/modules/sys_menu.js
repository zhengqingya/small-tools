import request from '@/utils/request'

const BASE_API = '/system/web/api/menu'

export default {
  menuTree() {
    return request({
      url: BASE_API + '/menuTree',
      method: 'get',
      params: { systemSource: 0 },
    })
  },
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
  delete(menuId) {
    return request({
      url: BASE_API,
      method: 'delete',
      params: {
        menuId: menuId,
      },
    })
  },
  /**
   * 保存页面中的按钮
   * @param {arr} data
   */
  saveMenuBtnIds(data) {
    return request({
      url: BASE_API + '/saveMenuBtnIds',
      method: 'post',
      data,
    })
  },
  /**
   * 获取页面中的按钮ids
   * @param {arr} data
   */
  getBtnIdsByMenuId(id) {
    return request({
      url: BASE_API + '/getBtnIdsByMenuId',
      method: 'get',
      params: {
        menuId: id,
      },
    })
  },
}
