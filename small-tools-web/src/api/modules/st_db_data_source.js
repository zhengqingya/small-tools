import request from '@/utils/request'

const BASE_API = '/tool/web/api/db/dataSource'

export default {
  listPage(query, headers) {
    return request({
      url: BASE_API + '/listPage',
      method: 'get',
      params: query,
      headers,
    })
  },
  list(query) {
    return request({
      url: BASE_API + '/list',
      method: 'get',
      params: query,
    })
  },
  add(form) {
    return request({
      url: BASE_API,
      method: 'post',
      data: form,
    })
  },
  update(form) {
    return request({
      url: BASE_API,
      method: 'put',
      data: form,
    })
  },
  delete(id) {
    return request({
      url: BASE_API,
      method: 'delete',
      params: { id: id },
    })
  },
}
