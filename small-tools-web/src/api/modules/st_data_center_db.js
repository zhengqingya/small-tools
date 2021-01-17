import request from "@/utils/request";

const BASE_API = "/tool/web/api/data-center";

export default {
  list(query) {
    return request({
      url: BASE_API + "/list",
      method: "get",
      params: query
    });
  },
  tableList(query) {
    return request({
      url: BASE_API + "/tableList",
      method: "get",
      params: query
    });
  },
  columnList(query) {
    return request({
      url: BASE_API + "/columnList",
      method: "put",
      data: query
    });
  },
  saveTable(tableInfo) {
    return request({
      url: BASE_API + "/saveTable",
      method: "post",
      data: tableInfo
    });
  }
};
