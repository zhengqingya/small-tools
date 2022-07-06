import request from '@/utils/request'

const BASE_API = '/tool/web/api/generator/database'

export default {
  listPage(query, headers) {
    return request({
      url: BASE_API + '/listPage',
      method: 'get',
      params: query,
      headers,
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
      params: {
        id: id,
      },
    })
  },
  tableList(query) {
    return request({
      url: BASE_API + '/tableList',
      method: 'get',
      params: query,
    })
  },
  columnList(query) {
    return request({
      url: BASE_API + '/columnList',
      method: 'get',
      params: query,
    })
  },
}
