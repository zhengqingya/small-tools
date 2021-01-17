import request from "@/utils/request";

const BASE_API = "/tool/web/api/crawler/articleInfo";

export default {
  listPage(query, headers) {
    return request({
      url: BASE_API + "/listPage",
      method: "get",
      params: query,
      headers
    });
  },
  // 导出数据
  exportData(params) {
    return request({
      url: BASE_API + "/exportData",
      method: "get",
      params: params
    });
  },
  // 根据网站导出所有爬虫文章数据
  exportAllDataByWebsiteId(id) {
    return request({
      url: BASE_API + "/exportAllDataByWebsiteId",
      method: "get",
      params: { websiteId: id }
    });
  },
  // 根据网站导出爬虫文章数据Excel
  exportExcelByWebsiteId(id) {
    return request({
      url: BASE_API + "/exportExcelByWebsiteId",
      method: "get",
      params: { websiteId: id }
    });
  },
  // 导入数据
  importData(data) {
    return request({
      url: BASE_API + "/importData",
      method: "post",
      data: data,
      headers: {
        "Content-Type": "multipart/form-data; charset=utf-8"
      }
    });
  }
};
