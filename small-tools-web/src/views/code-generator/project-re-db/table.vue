<template>
  <base-wraper>
    <base-header>
      <el-input v-model="listQuery.tableName" placeholder="表名称" clearable style="width: 200px" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button @click="handleBack()">返回</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTable" :is-page="false" api="cg_project_re_db.tableList" :params="listQuery">
      <el-table-column align="center" label="ID" width="80" type="index" />
      <el-table-column width="250" label="表名" header-align="center" align="center" prop="tableName" />
      <el-table-column label="注释" header-align="center" align="center" prop="tableComment" />
      <el-table-column align="center" label="操作">
        <template v-slot="scope">
          <router-link
            :to="{
              path: '/codeGenerator/project-re-db/column',
              query: {
                projectReDbDataSourceId: listQuery.projectReDbDataSourceId,
                tableName: scope.row.tableName,
              },
            }"
          >
            <el-button link plain>查看表信息</el-button>
          </router-link>
        </template>
      </el-table-column>
    </base-table-p>
  </base-wraper>
</template>

<script>
export default {
  name: 'Table',
  data() {
    return {
      listQuery: {
        projectReDbDataSourceId: parseInt(this.$route.query.projectReDbDataSourceId),
        tableName: '',
      },
    }
  },
  created() {},
  methods: {
    async refreshTableData() {
      this.$refs.baseTable.refresh()
    },
    handleBack() {
      this.$router.go(-1)
    },
  },
}
</script>
<style scoped></style>
