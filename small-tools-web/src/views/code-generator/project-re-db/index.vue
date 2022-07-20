<template>
  <base-wraper>
    <base-header>
      <el-select v-model="listQuery.projectId" placeholder="请选择项目" @change="refreshTableData">
        <el-option :value="undefined" label="全部" />
        <el-option v-for="item in projectList" :key="item.id" :value="item.id" :label="item.name" />
      </el-select>
      <el-select v-model="listQuery.dbDataSourceId" placeholder="请选择数据源" @change="refreshTableData">
        <el-option :value="undefined" label="全部" />
        <el-option v-for="item in dataSourceList" :key="item.id" :value="item.id" :label="item.name" />
      </el-select>
      <el-button type="primary" @click="refreshTableData">
        查询
      </el-button>
      <template #right>
        <router-link to="/db/data-source">
          <el-button type="warning">查看数据库信息</el-button>
        </router-link>
        <el-button type="primary" @click="handleAdd">
          添加
        </el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTable" api="cg_project_re_db.listPage" :params="listQuery">
      <el-table-column label="ID" prop="projectReDbDataSourceId" align="center" />
      <el-table-column label="项目名称" prop="projectName" align="center" />
      <el-table-column label="数据源名称" prop="dbDataSourceName" align="center" />
      <el-table-column label="数据库名称" prop="dbName" align="center" />
      <el-table-column label="备注" prop="remark" align="center" />
      <el-table-column align="center" label="操作">
        <template v-slot="scope">
          <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)" />
          <el-button link plain @click="handleConnectTest(scope.row)">
            连接测试
          </el-button>
          <router-link :to="{
            path: '/codeGenerator/project-re-db/table',
            query: {
              projectReDbDataSourceId: scope.row.projectReDbDataSourceId,
            },
          }">
            <el-button link plain>查看表</el-button>
          </router-link>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogVisible" :title="titleMap[dialogStatus]" width="30%">
      <el-form ref="dataForm" :model="form" :rules="rules" label-width="100px">
        <el-form-item v-if="dialogStatus === 'update'" label="ID:" prop="projectReDbDataSourceId">
          <el-input v-model="form.projectReDbDataSourceId" disabled />
        </el-form-item>
        <el-form-item label="所属项目ID:" prop="projectId">
          <el-select v-model="form.projectId" placeholder="请选择项目">
            <el-option v-for="item in projectList" :key="item.id" :value="item.id" :label="item.name" />
          </el-select>
        </el-form-item>
        <el-form-item label="数据源id:" prop="dbDataSourceId">
          <el-select v-model="form.dbDataSourceId" placeholder="请选择数据源">
            <el-option v-for="item in dataSourceList" :key="item.id" :value="item.id" :label="item.name" />
          </el-select>
        </el-form-item>
        <el-form-item label="数据库名称:" prop="dbName">
          <el-input v-model="form.dbName" />
        </el-form-item>
        <el-form-item label="备注:" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="5" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </base-dialog>
  </base-wraper>
</template>

<script>
export default {
  name: 'CgProjectReDb',
  data() {
    return {
      dialogVisible: false,
      projectList: [],
      dataSourceList: [], // 数据源列表
      listQuery: {
        projectId: undefined, // 所属项目ID
        dbDataSourceId: undefined, // 数据源id
      },
      form: {
        projectReDbDataSourceId: undefined, // 数据库ID
        projectId: undefined, // 所属项目ID
        dbDataSourceId: undefined, // 数据源id
        dbName: '', // 数据库名称
        remark: '', // 备注
      },
      dialogStatus: '',
      titleMap: {
        add: '添加',
        update: '编辑',
        detail: '详情',
      },
      rules: {},
    };
  },
  created() {
    this.getProjectList();
    this.getDataSourceList();
  },
  methods: {
    refreshTableData() {
      this.$refs.baseTable.refresh();
    },
    async getProjectList() {
      let res = await this.$api.cg_project.list({});
      this.projectList = res.data;
    },
    async getDataSourceList() {
      let res = await this.$api.st_db_data_source.list();
      this.dataSourceList = res.data;
    },
    async handleConnectTest(row) {
      let res = await this.$api.st_db_operate.connectTest(
        row.dbDataSourceId,
        row.dbName
      );
      this.submitOk(res.msg);
    },
    handleAdd() {
      this.form = Object.assign({}, {});
      this.dialogStatus = 'add';
      this.dialogVisible = true;
    },
    handleUpdate(row) {
      this.form = Object.assign({}, row);
      this.dialogStatus = 'update';
      this.dialogVisible = true;
    },
    async handleDelete(row) {
      const res = await this.$api.cg_project_re_db.delete(
        row.projectReDbDataSourceId
      );
      this.refreshTableData();
      this.submitOk(res.message);
    },
    submitForm() {
      this.$refs.dataForm.validate(async (valid) => {
        if (valid) {
          const res = await this.$api.cg_project_re_db[
            this.form.projectReDbDataSourceId ? 'update' : 'add'
          ](this.form);
          this.refreshTableData();
          this.submitOk(res.message);
          this.dialogVisible = false;
        }
      });
    },
  },
};
</script>
<style scoped>
</style>
