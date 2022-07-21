<template>
  <div class="app-container">
    <el-row :gutter="15" style="height: 100%">
      <el-col :span="6" style="height: 100%">
        <my-tree ref="myTree" :my-data-source-id="dataSourceId" @showTableList="showTableList" @showTableColumnList="showTableColumnList" />
      </el-col>
      <el-col :span="18" style="height: 100%">
        <base-wraper>
          <my-table
            v-if="showStatus === 'table'"
            ref="myTable"
            :data-source-id="dataSourceId"
            :db-name="dbName"
            @showTableColumnList="showTableColumnList"
          />
          <my-column v-else-if="showStatus === 'column'" ref="myColumn" :data-source-id="dataSourceId" :db-name="dbName" :table-name="tableName" />
          <base-title-card v-else title="请先选择数据" />
        </base-wraper>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import MyTree from './components/tree.vue'
import MyTable from './components/table.vue'
import MyColumn from './components/column.vue'

export default {
  name: 'DataBase',
  components: { MyTree, MyTable, MyColumn },
  data() {
    return {
      dataSourceId: this.$route.query.dataSourceId, // 数据源id
      dbName: this.$route.query.dbName, // 数据库名
      tableName: this.$route.query.tableName, // 表名
      showStatus: '', // 标识展示表格为表(table)或字段(column)信息？？
    }
  },
  created() {
    if (this.dataSourceId) {
      this.dataSourceId = parseInt(this.dataSourceId)
    }
    if (this.tableName) {
      this.showTableColumnList(this.tableName)
    }
  },
  methods: {
    async showTableList(dataSourceId, dbName, tableList) {
      this.showStatus = 'table'
      setTimeout(() => {
        this.$refs.myTable.open(tableList)
      }, 10)
      this.dataSourceId = dataSourceId
      this.dbName = dbName
    },
    async showTableColumnList(tableName) {
      this.showStatus = 'column'
      setTimeout(() => {
        this.$refs.myColumn.open(tableName)
      }, 10)
      this.tableName = tableName
    },
  },
}
</script>
<style lang="scss" scoped></style>
