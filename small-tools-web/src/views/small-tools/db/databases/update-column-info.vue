<template>
  <my-base-wraper>
    <base-header>
      <el-button type="primary" @click="getColumnList">刷新</el-button>
      <router-link
        :to="{
          path: '/db/databases',
          query: {
            dataSourceId: dataSourceId,
            dbName: dbName,
            tableName: tableName,
          },
        }"
      >
        <!-- <el-button @click="handleBack()">返回</el-button> -->
        <el-button>返回</el-button>
      </router-link>

      <template #right>
        <el-button
          type="warning"
          icon="el-icon-edit-outline"
          @click="handleUpdateColumnInfo()"
          >更新表</el-button
        >
      </template>
    </base-header>

    <el-card>
      <div slot="header">
        <el-row>
          <el-col :span="12">
            <my-base-table-cell>
              <my-base-cell-item label="表名" label-width="80px">{{
                tableInfo.tableName
              }}</my-base-cell-item>
            </my-base-table-cell>
          </el-col>
          <el-col :span="12">
            <my-base-table-cell>
              <my-base-cell-item label="表注释" label-width="80px"
                ><el-input v-model="tableInfo.tableComment"
              /></my-base-cell-item>
            </my-base-table-cell>
          </el-col>
        </el-row>
      </div>
      <div>
        <base-table-p
          v-loading="loading"
          height="700px"
          :index-code="true"
          :is-page="false"
          :data="tableInfo.columnInfoList"
        >
          <el-table-column label="字段名" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.columnName" disabled />
            </template>
          </el-table-column>
          <el-table-column label="数据类型" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.columnType" />
            </template>
          </el-table-column>
          <el-table-column label="是否允许空值" align="center">
            <template slot-scope="scope">
              <el-select v-model="scope.row.ifNullAble" style="width: 70px">
                <el-option
                  v-for="item in typeList"
                  :key="item.value"
                  :value="item.value"
                  :label="item.label"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="是否为主键" align="center">
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.ifPrimaryKey"
                style="width: 70px"
                :class="{ zq_select: scope.row.ifPrimaryKey }"
                disabled
                @change="handleChangePrimaryKey(scope.row)"
              >
                <el-option
                  v-for="item in typeList"
                  :key="item.value"
                  :value="item.value"
                  :label="item.label"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="是否自增长" align="center">
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.ifAutoIncrement"
                style="width: 70px"
                :class="{ zq_select: scope.row.ifAutoIncrement }"
                disabled
              >
                <el-option
                  v-for="item in typeList"
                  :key="item.value"
                  :value="item.value"
                  :label="item.label"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="默认值" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.columnDefaultValue" />
            </template>
          </el-table-column>
          <el-table-column label="注释" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.columnComment" />
            </template>
          </el-table-column>
        </base-table-p>
      </div>
    </el-card>
  </my-base-wraper>
</template>

<script>
export default {
  name: 'ColumenInfo',
  data() {
    return {
      loading: true,
      dataSourceId: this.$route.query.dataSourceId, // 数据源id
      dbName: this.$route.query.dbName, // 库名
      tableName: this.$route.query.tableName, // 表名
      tableInfo: {
        dataSourceId: this.$route.query.dataSourceId,
        dbName: this.$route.query.dbName,
        tableName: this.$route.query.tableName,
        tableComment: '', // 注释
        columnInfoList: [], // 表字段信息
      },
      typeList: [
        { label: '是', value: true },
        { label: '否', value: false },
      ],
    }
  },
  created() {
    this.getColumnList()
  },
  methods: {
    async getColumnList() {
      this.loading = true
      let res =
        await this.$api.st_db_operate.getAllColumnsByDataSourceIdAndDbNameAndTableName(
          this.dataSourceId,
          this.dbName,
          this.tableName
        )
      this.tableInfo = res.data
      this.loading = false
    },
    handleBack() {
      this.$router.go(-1)
    },
    async handleUpdateColumnInfo() {
      let res = await this.$api.st_db_operate.updateColumnInfo(this.tableInfo)
      this.submitOk(res.msg)
    },
    handleChangePrimaryKey(row) {
      console.log(1, row)
    },
  },
}
</script>
<style lang="scss" scoped>
/deep/ .zq_select .el-input.is-disabled .el-input__inner {
  background: red;
}
</style>
