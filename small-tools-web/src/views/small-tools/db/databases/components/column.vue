<template>
  <base-wraper>
    <base-title-card v-if="isShow" title="表字段信息">
      <template #append>
        <router-link :to="{
          path: '/db/update-column-info',
          query: {
            dataSourceId: dataSourceId,
            dbName: dbName,
            tableName: tableName,
          },
        }">
          <el-button type="warning" icon="el-icon-edit-outline">设计表</el-button>
        </router-link>
      </template>
      <base-table-p v-if="tableColumnList && tableColumnList.length > 0" ref="baseTableColumn"
        v-loading="tableColumnLoading" height="600" :index-code="true" :is-page="false" :data="tableColumnList">
        <el-table-column label="字段名" align="left">
          <template v-slot="scope">
            <span>{{ scope.row.columnName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="数据类型" prop="columnType" align="center" />
        <el-table-column label="是否允许空值" width="100px" align="center">
          <template v-slot="scope">
            <span v-if="scope.row.ifNullAble" style="font-weight: bold; color: #f56c6c">是</span>
            <span v-else>否</span>
          </template>
        </el-table-column>
        <el-table-column label="是否是主键" width="100px" align="center">
          <template v-slot="scope">
            <span v-if="scope.row.ifPrimaryKey" style="font-weight: bold; color: #f56c6c">是</span>
            <span v-else>否</span>
          </template>
        </el-table-column>
        <el-table-column label="是否自增长" width="100px" align="center">
          <template v-slot="scope">
            <span v-if="scope.row.ifAutoIncrement" style="font-weight: bold; color: #f56c6c">是</span>
            <span v-else>否</span>
          </template>
        </el-table-column>
        <el-table-column label="默认值" prop="columnDefaultValue" align="center" />
        <el-table-column label="注释" prop="columnComment" align="left" />
      </base-table-p>
    </base-title-card>
  </base-wraper>
</template>
<script>
export default {
  name: 'MyColumn',
  props: {
    // 数据源id
    dataSourceId: {
      type: Number,
      default: undefined,
    },
    // 数据库名
    dbName: {
      type: String,
      default: '',
    },
    // 表名
    tableName: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      isShow: false,
      tableColumnLoading: true,
      tableColumnList: [], // 表字段信息
    };
  },
  created() { },
  methods: {
    open(tableName) {
      this.isShow = true;
      this.refreshTableColumnList(tableName);
    },
    // 刷新表字段数据
    async refreshTableColumnList(tableName) {
      this.tableColumnLoading = true;
      let res =
        await this.$api.st_db_operate.getAllColumnsByDataSourceIdAndDbNameAndTableName(
          this.dataSourceId,
          this.dbName,
          tableName
        );
      let resData = res.data;
      this.tableColumnList = resData.columnInfoList;
      this.tableColumnLoading = false;
    },
    close() {
      this.isShow = false;
    },
  },
};
</script>
<style lang="scss" scoped>
</style>
