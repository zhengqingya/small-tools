import request from "@/utils/request";

const BASE_API = "/tool/web/api/generator/freeMarkerTemplate";

export default {
  listPage(query, headers) {
    return request({
      url: BASE_API + "/listPage",
      method: "get",
      params: query,
      headers
    });
  },
  add(form) {
    return request({
      url: BASE_API,
      method: "post",
      data: form
    });
  },
  update(form) {
    return request({
      url: BASE_API,
      method: "put",
      data: form
    });
  },
  testTemplateData(form) {
    return request({
      url: BASE_API + "/testTemplateData",
      method: "post",
      data: form
    });
  },
  delete(id) {
    return request({
      url: BASE_API,
      method: "delete",
      params: { freeMarkerTemplateId: id }
    });
  }
};
