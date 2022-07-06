import request from '@/utils/request'

const BASE_API = '/system/web/api/oauth'

export default {
  thirdpartOauth(oauthType) {
    return request({
      url: BASE_API + '/' + oauthType,
      method: 'get',
    })
  },
  bindThirdPart(data) {
    return request({
      url: BASE_API + '/bindThirdPart',
      method: 'post',
      data,
    })
  },
  getOauthDataList(params) {
    return request({
      url: BASE_API + '/getOauthDataList',
      method: 'get',
      params: params,
    })
  },
  removeBind(data) {
    return request({
      url: BASE_API + '/removeBind',
      method: 'post',
      data,
    })
  },
}
