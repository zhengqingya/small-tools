import request from "@/utils/request";

const BASE_API = "/tool/web/api/generator/projectPackage";

export default {
  list(projectId) {
    return request({
      url: BASE_API + "/list",
      method: "get",
      params: { projectId: projectId }
    });
  },
  tree(projectId) {
    return request({
      url: BASE_API + "/tree",
      method: "get",
      params: { projectId: projectId }
    });
  },
  add(data) {
    return request({
      url: BASE_API,
      method: "post",
      data
    });
  },
  update(data) {
    return request({
      url: BASE_API,
      method: "put",
      data
    });
  },
  delete(id) {
    return request({
      url: BASE_API,
      method: "delete",
      params: {
        id: id
      }
    });
  }
};
