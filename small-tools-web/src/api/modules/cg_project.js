import request from "@/utils/request";

const BASE_API = "/tool/web/api/generator/project";

export default {
  listPage(query, headers) {
    return request({
      url: BASE_API + "/listPage",
      method: "get",
      params: query,
      headers
    });
  },
  list(query) {
    return request({
      url: BASE_API + "/list",
      method: "get",
      params: query
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
  },
  // 上：项目管理 下：项目包管理 =================================

  generate(tableInfo, packageConfig) {
    return request({
      url: BASE_API + "/generate",
      method: "post",
      data: {
        tableInfo: tableInfo,
        packageConfig: packageConfig
      }
    });
  },
  generateCode(data) {
    return request({
      url: BASE_API + "/generateCode",
      method: "post",
      data
    });
  }
};
