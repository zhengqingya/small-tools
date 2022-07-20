<template>
  <base-wraper>
    <base-header>
      <el-input v-model="listQuery.name" style="width: 200px" placeholder="请输入数据源名称" clearable
        @clear="refreshTableData">
      </el-input>
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleCreate">添加</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTable" api="st_db_data_source.listPage" :params="listQuery">
      <el-table-column align="center" label="ID" width="80" prop="id" />
      <el-table-column label="数据源名称" align="center" prop="name" />
      <el-table-column label="ip地址" align="center" prop="ipAddress" />
      <el-table-column label="端口" align="center" prop="port" />
      <el-table-column label="用户名" align="center" prop="username" />
      <el-table-column label="密码" align="center" prop="password" />
      <el-table-column label="类型" align="center" prop="typeName" />
      <el-table-column label="备注" show-overflow-tooltip align="center" prop="remark" />
      <el-table-column align="center" label="操作">
        <template v-slot="scope">
          <el-button link plain @click="handleConnectTest(scope.row)">
            连接测试
          </el-button>
          <el-button link plain @click="handleUpdate(scope.row)">
            编辑
          </el-button>
          <base-delete-btn @ok="handleDelete(scope.row)" />
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogFormVisible" :title="textMap[dialogStatus]" width="30%">
      <el-form ref="dataForm" :model="form" label-width="90px" :rules="rules">
        <el-form-item label="数据源名称:" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="ip地址:" prop="ipAddress">
          <el-input v-model="form.ipAddress" />
        </el-form-item>
        <el-form-item label="端口:" prop="port">
          <el-input v-model="form.port" />
        </el-form-item>
        <el-form-item label="用户名:" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码:" prop="password">
          <el-input v-model="form.password" />
        </el-form-item>
        <el-form-item label="类型:" prop="type">
          <el-select v-model="form.type" placeholder="请选择">
            <el-option v-for="item in dataSourceTypeList" :key="item.value" :value="item.value" :label="item.name" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注:" prop="remark">
          <el-input v-model="form.remark" :rows="3" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </base-dialog>
  </base-wraper>
</template>

<script>
export default {
  name: 'StDbDataSource',
  data() {
    return {
      dataSourceTypeList: [],
      listQuery: {
        name: undefined,
      },
      form: {
        id: undefined,
        name: undefined,
        ipAddress: undefined,
        port: undefined,
        username: undefined,
        password: undefined,
        type: '',
        remark: undefined,
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建',
      },
      rules: {},
    };
  },
  created() {
    this.getDataSourceTypeList();
  },
  methods: {
    refreshTableData() {
      this.$refs.baseTable.refresh();
    },
    async getDataSourceTypeList() {
      let res = await this.$api.sys_dict.listFromCacheByCode(
        'st_db_data_source_type'
      );
      this.dataSourceTypeList = res.data;
    },
    async handleConnectTest(row) {
      let res = await this.$api.st_db_operate.connectTest(row.id);
      this.submitOk(res.msg);
    },
    handleCreate() {
      this.form = Object.assign({}, {});
      this.dialogStatus = 'create';
      this.dialogFormVisible = true;
    },
    handleUpdate(row) {
      this.form = Object.assign({}, row);
      this.form.project = undefined;
      this.dialogStatus = 'update';
      this.dialogFormVisible = true;
    },
    async handleDelete(row) {
      let res = await this.$api.st_db_data_source.delete(row.id);
      this.refreshTableData();
      this.submitOk(res.msg);
    },
    submitForm() {
      this.$refs.dataForm.validate(async (valid) => {
        if (valid) {
          let res = await this.$api.st_db_data_source[
            this.form.id ? 'update' : 'add'
          ](this.form);
          this.refreshTableData();
          this.submitOk(res.msg);
          this.dialogFormVisible = false;
        }
      });
    },
  },
};
</script>
<style scoped>
</style>
