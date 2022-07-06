import request from '@/utils/request'

const BASE_API = '/tool/web/api/db/operate'

export default {
  connectTest(dataSourceId, dbName) {
    return request({
      url: BASE_API + '/connectTest',
      method: 'get',
      params: { dataSourceId, dbName },
    })
  },
  getAllDatabasesByDataSourceId(id) {
    return request({
      url: BASE_API + '/getAllDatabasesByDataSourceId',
      method: 'get',
      params: { dataSourceId: id },
    })
  },
  getAllTablesByDataSourceIdAndDbName(params) {
    return request({
      url: BASE_API + '/getAllTablesByDataSourceIdAndDbName',
      method: 'get',
      params: params,
    })
  },
  getAllColumnsByDataSourceIdAndDbNameAndTableName(
    dataSourceId,
    dbName,
    tableName
  ) {
    return request({
      url: BASE_API + '/getAllColumnsByDataSourceIdAndDbNameAndTableName',
      method: 'get',
      params: {
        dataSourceId: dataSourceId,
        dbName: dbName,
        tableName: tableName,
      },
    })
  },
  updateColumnInfo(data) {
    return request({
      url: BASE_API + '/updateColumnInfo',
      method: 'put',
      data,
    })
  },
  tableInfoToWordByDataSourceIdAndDbName(dataSourceId, dbName) {
    return request({
      url: BASE_API + '/tableInfoToWordByDataSourceIdAndDbName',
      method: 'get',
      params: { dataSourceId: dataSourceId, dbName: dbName },
    })
  },
}
