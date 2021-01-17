<template>
  <my-base-wraper>
    <base-header>
      <el-button
        type="success"
        icon="el-icon-setting"
        @click="handleGenerateCode"
        >生成代码</el-button
      >
      <template #right>
        <el-button type="primary" @click="refreshTableData">刷新</el-button>
        <el-button @click="handleBack()">返回</el-button>
      </template>
    </base-header>

    <el-card>
      <div slot="header">
        <el-row>
          <el-col :span="16">
            <my-base-table-cell>
              <my-base-cell-item label="表备注" label-width="100px">{{
                tableInfo.tableComment
              }}</my-base-cell-item>
            </my-base-table-cell>
            <my-base-table-cell>
              <my-base-cell-item label="可检索字段" label-width="100px">
                <el-tag
                  v-for="item in generateCodeParams.queryColumnList"
                  :key="item"
                  >{{ item }}</el-tag
                >
              </my-base-cell-item>
            </my-base-table-cell>
          </el-col>
          <el-col :span="8">
            <my-base-table-cell>
              <my-base-cell-item label="父包名" label-width="80px">
                <el-input
                  v-model="generateCodeParams.packageName"
                  placeholder="父包名"
                />
              </my-base-cell-item>
            </my-base-table-cell>
            <my-base-table-cell>
              <my-base-cell-item label="模块名" label-width="80px">
                <el-input
                  v-model="generateCodeParams.moduleName"
                  placeholder="模块名"
                />
              </my-base-cell-item>
            </my-base-table-cell>
          </el-col>
          <el-col :span="24">
            <my-base-table-cell>
              <my-base-cell-item label="数据类型" label-width="100px">
                <el-select
                  v-model="generateCodeParams.dataType"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in dataTypeList"
                    :key="item.key"
                    :label="item.display_name"
                    :value="item.key"
                  /> </el-select
              ></my-base-cell-item>
            </my-base-table-cell>
          </el-col>
        </el-row>
      </div>
      <div>
        <el-table
          ref="colTable"
          v-loading.body="listLoading"
          :data="tableInfo.columnInfoList"
          border
          fit
          highlight-current-row
          size="small"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column align="center" label="ID" width="100" type="index" />
          <el-table-column
            width="200"
            label="字段名"
            header-align="center"
            align="center"
            prop="columnName"
          />
          <el-table-column
            label="类型"
            width="200"
            header-align="center"
            align="center"
            prop="columnType"
          />
          <el-table-column
            label="备注"
            header-align="center"
            prop="columnComment"
            align="left"
          />
        </el-table>
      </div>
    </el-card>
  </my-base-wraper>
</template>

<script>
export default {
  name: "ColumenInfo",
  data() {
    return {
      listLoading: false,
      listQuery: {
        projectReDbDataSourceId: parseInt(
          this.$route.query.projectReDbDataSourceId
        ),
        tableName: this.$route.query.tableName
      },
      dataTypeList: [
        { key: 1, display_name: "默认数据" },
        { key: 2, display_name: "测试模板生成配置数据" }
      ],
      tableInfo: {
        packageName: "", // 包名称
        tableComment: "", // 表注释
        columnInfoList: [], // 数据库表字段
        queryColumnList: [] // 可检索字段
      },
      // 代码生成提交参数
      generateCodeParams: {
        projectReDbDataSourceId: this.$route.query.projectReDbDataSourceId,
        tableName: this.$route.query.tableName,
        queryColumnList: [],
        packageName: "",
        moduleName: "",
        dataType: 2
      }
    };
  },
  created() {
    this.refreshTableData();
  },
  methods: {
    async refreshTableData() {
      this.listLoading = true;
      let res = await this.$api.cg_project_re_db.tableInfo(this.listQuery);
      let tableInfo = res.data;
      this.tableInfo = tableInfo;
      this.generateCodeParams.packageName = tableInfo.packageName;
      this.generateCodeParams.queryColumnList = tableInfo.queryColumnList;
      if (tableInfo.queryColumnList && tableInfo.queryColumnList.length > 0) {
        setTimeout(() => {
          tableInfo.columnInfoList.forEach(columnItem => {
            tableInfo.queryColumnList.forEach(queryColumnName => {
              if (columnItem.columnName === queryColumnName) {
                this.$refs.colTable.toggleRowSelection(columnItem, true);
              }
            });
          });
        }, 500);
      }
      this.listLoading = false;
    },
    // 选中时改变可检索字段
    handleSelectionChange(val) {
      if (val) {
        this.generateCodeParams.queryColumnList = val.map(item => {
          return item.columnName;
        });
      }
    },
    async handleGenerateCode() {
      let res = await this.$api.cg_project.generateCode(
        this.generateCodeParams
      );
      if (res.data) {
        document.location.href = res.data;
      }
    },
    handleBack() {
      this.$router.go(-1);
    }
  }
};
</script>
<style scoped></style>
