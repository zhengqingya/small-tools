import request from '@/utils/request'

const BASE_API = '/tool/web/api/generator/projectReDb'

export default {
  listPage(query, headers) {
    return request({
      url: BASE_API + '/listPage',
      method: 'get',
      params: query,
      headers,
    })
  },
  tableList(query) {
    return request({
      url: BASE_API + '/tableList',
      method: 'get',
      params: query,
    })
  },
  tableInfo(query) {
    return request({
      url: BASE_API + '/tableInfo',
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
  delete(projectReDbDataSourceId) {
    return request({
      url: BASE_API,
      method: 'delete',
      params: { projectReDbDataSourceId: projectReDbDataSourceId },
    })
  },
}
