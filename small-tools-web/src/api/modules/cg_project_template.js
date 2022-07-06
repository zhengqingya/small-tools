import request from '@/utils/request'

const BASE_API = '/tool/web/api/generator/projectTemplate'

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
  delete(projectTemplateId) {
    return request({
      url: BASE_API,
      method: 'delete',
      params: {
        projectTemplateId,
      },
    })
  },
  // generateTemplate(projectId) {
  //   return request({
  //     url: BASE_API + "/generateTemplate",
  //     method: "get",
  //     data: {
  //       projectId: projectId
  //     }
  //   });
  // },
  listPageCodeProjectVelocityContext(query, headers) {
    return request({
      url: BASE_API + '/listPageCodeProjectVelocityContext',
      method: 'get',
      params: query,
      headers,
    })
  },
  testTemplateData(form) {
    return request({
      url: BASE_API + '/testTemplateData',
      method: 'post',
      data: form,
    })
  },
}
