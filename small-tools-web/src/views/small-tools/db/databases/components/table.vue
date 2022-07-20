<template>
  <base-wraper>
    <base-title-card v-if="isShow" title="表信息">
      <template #append>
        <el-button type="warning" icon="el-icon-edit-outline" @click="handleTableInfoToWord()">生成word</el-button>
      </template>
      <base-table-p v-if="tableList && tableList.length > 0" ref="baseTable" v-loading="tableLoading" height="600px"
        :index-code="true" :is-page="false" :data="tableList">
        <el-table-column label="表名" align="left">
          <template v-slot="scope">
            <span style="font-weight: bold; color: #409eff" @click="showTableColumnList(scope.row.tableName)">
              {{ scope.row.tableName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="表类型" prop="engine" align="center" />
        <el-table-column label="行" align="center">
          <template v-slot="scope">
            <span>{{ scope.row.tableRows }}</span>
          </template>
        </el-table-column>
        <el-table-column label="数据长度" align="center">
          <template v-slot="scope">
            <span>{{ scope.row.dataLength }}</span>
          </template>
        </el-table-column>
        <el-table-column label="自动递增值" align="center">
          <template v-slot="scope">
            <span>{{ scope.row.autoIncrement }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="创建时间" prop="createTime" align="center" />
              <el-table-column label="修改时间" prop="updateTime" align="center" /> -->
        <el-table-column label="排序规则" prop="tableCollation" align="left" />
        <el-table-column label="注释" prop="tableComment" align="left" />
      </base-table-p>
    </base-title-card>
  </base-wraper>
</template>
<script>
export default {
  name: 'MyTable',
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
  },
  data() {
    return {
      isShow: false,
      tableLoading: true,
      tableList: [], // 数据表列表
    };
  },
  created() { },
  methods: {
    // 刷新表格数据
    open(tableList) {
      this.isShow = true;
      this.tableLoading = true;
      this.tableList = tableList;
      this.tableLoading = false;
    },
    showTableColumnList(tableName) {
      this.$emit('showTableColumnList', tableName);
    },
    async handleTableInfoToWord() {
      let res =
        await this.$api.st_db_operate.tableInfoToWordByDataSourceIdAndDbName(
          this.dataSourceId,
          this.dbName
        );
      this.submitOk(res.msg);
      document.location.href = res.data;
    },
    close() {
      this.isShow = false;
    },
  },
};
</script>
<style lang="scss" scoped>
</style>
