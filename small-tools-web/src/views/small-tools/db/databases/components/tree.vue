<template>
  <my-base-wraper>
    <my-base-title-card title="数据库" style="padding-top: 0">
      <el-select
        slot="append"
        v-model="dataSourceId"
        placeholder="请选择数据源"
        @change="handleChangeDataSource(dataSourceId)"
      >
        <el-option
          v-for="item in dataSourceList"
          :key="item.id"
          :value="item.id"
          :label="item.name"
        />
      </el-select>
      <el-input
        v-model="filterDbText"
        placeholder="输入关键字进行过滤"
        clearable
      >
      </el-input>
      <el-tree
        ref="tree"
        :data="treeData"
        :props="dbProps"
        :filter-node-method="filterDbNode"
        :accordion="true"
        :highlight-current="true"
        @node-click="handleNodeClick"
      />
      <!-- 下面2个属性为懒加载子节点，暂时不要此功能 -->
      <!-- :load="loadDbNode" -->
      <!-- lazy -->
    </my-base-title-card>
  </my-base-wraper>
</template>

<script>
export default {
  name: 'MyTree',
  props: {
    // 数据源id
    myDataSourceId: {
      type: Number,
      default: undefined,
    },
  },
  data() {
    return {
      dataSourceId: undefined,
      filterDbText: '', // 过滤关键字
      treeData: [], // 树
      dataSourceList: [], // 数据源列表
      dbProps: {
        label: 'name',
        children: 'children',
        isLeaf: 'childNode', // 别名childNode属性=true时 -> 标识为子节点
      },
      // dbName: "", // 数据库名
      tableName: '', // 表名
    }
  },
  watch: {
    // 过滤树
    filterDbText(val) {
      this.$refs.tree.filter(val)
    },
  },
  created() {
    this.dataSourceId = this.myDataSourceId
    this.getDataSourceList()
    if (this.dataSourceId) {
      this.handleChangeDataSource(this.dataSourceId)
    }
  },
  methods: {
    async getDataSourceList() {
      let res = await this.$api.st_db_data_source.list()
      this.dataSourceList = res.data
    },
    async handleChangeDataSource(dataSourceId) {
      let res = await this.$api.st_db_operate.getAllDatabasesByDataSourceId(
        dataSourceId
      )
      let resData = res.data
      let data = []
      if (resData) {
        resData.forEach((e) => {
          data.push({ name: e.name, children: [] })
        })
        this.treeData = data
      }
    },
    filterDbNode(value, data) {
      if (!value) return true
      return data.name.indexOf(value) !== -1
    },
    async handleNodeClick(data) {
      if (data.children) {
        // 情况①：显示所有表信息
        let dbName = data.name
        let res =
          await this.$api.st_db_operate.getAllTablesByDataSourceIdAndDbName({
            dataSourceId: this.dataSourceId,
            dbName: dbName,
          })
        let tableInfoList = res.data
        this.tableList = tableInfoList
        this.treeData.forEach((e) => {
          if (e.name === data.name) {
            let children = []
            tableInfoList.forEach((e) => {
              children.push({ name: e.tableName })
            })
            e.children = children
          }
        })
        this.$emit('showTableList', this.dataSourceId, dbName, this.tableList)
      } else {
        let tableName = data.name
        // 情况②：显示具体表信息
        this.$emit('showTableColumnList', tableName)
      }
      // this.refreshTableData();
    },

    // 此方法暂做保留
    async refreshTableData() {
      switch (this.showStatus) {
        case 'table':
          await this.$refs.baseTable.refresh()
          break
        case 'column':
          await this.$refs.baseTableColumn.refresh()
          break
        default:
          break
      }
    },
    // 懒加载树字节 - 暂时不使用此方式 （问题： 数据只会加载第一次，后面再点击父节点的时候不会再次加载数据！！！）
    async loadDbNode(node, resolve) {
      // 数据库名
      let dbName = node.label
      let tableNameList = []
      // 第二级的时候为表名，不需要再请求了...
      if (dbName && node.level < 2) {
        // 请求api
        let res =
          await this.$api.st_db_operate.getAllTablesByDataSourceIdAndDbName({
            dataSourceId: this.dataSourceId,
            dbName: dbName,
          })
        tableNameList = res.data
      } else {
        return resolve([])
      }
      if (tableNameList) {
        // 如果api返回数据则执行如下
        let tableNameInfoList = []
        tableNameList.forEach((e) => {
          if (e) {
            let tableNameInfo = { name: e.tableName, childNode: true }
            tableNameInfoList.push(tableNameInfo)
          }
        })
        setTimeout(() => {
          this.tableList = tableNameList
          resolve(tableNameInfoList)
        }, 100)
      } else {
        return resolve([])
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
