import request from '@/utils/request'

const BASE_API = '/tool/web/api/crawler/website'

export default {
  listPage(query, headers) {
    return request({
      url: BASE_API + '/listPage',
      method: 'get',
      params: query,
      headers,
    })
  },
  refreshData(params) {
    return request({
      url: BASE_API + '/refreshData',
      method: 'put',
      data: params,
    })
  },
  list(query) {
    return request({
      url: BASE_API + '/list',
      method: 'get',
      params: query,
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
  delete(id) {
    return request({
      url: BASE_API,
      method: 'delete',
      params: { websiteId: id },
    })
  },
}
