<template>
  <my-base-wraper>
    <base-header>
      <el-input
        v-model="listQuery.name"
        clearable
        placeholder="角色名称"
        style="width: 200px"
        @clear="refreshTableData"
      />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="add">添加</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTable" api="sys_role.listPage" :params="listQuery">
      <el-table-column prop="name" label="角色名" />
      <el-table-column prop="code" label="角色编码" />
      <el-table-column label="操作" align="center" width="150">
        <template slot-scope="scope">
          <el-button type="text" @click="update(scope.row)">编辑</el-button>
          <router-link
            :to="{ path: '/system/roleForm', query: { id: scope.row.roleId } }"
          >
            <el-button type="text">权限</el-button>
          </router-link>
          <my-base-delete-btn @ok="deleteData(scope.row.roleId)" />
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog
      :visible.sync="dialogVisible"
      :title="textMap[dialogStatus]"
      width="20%"
      @close="handleDialogClose"
    >
      <el-form
        ref="roleForm"
        :model="roleForm"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="角色名：" prop="name">
          <el-input v-model="roleForm.name" placeholder="请输入角色名" />
        </el-form-item>
        <el-form-item label="角色编码：" prop="code">
          <el-input v-model="roleForm.code" placeholder="请输入角色编码" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveForm">确 定</el-button>
      </div>
    </base-dialog>
  </my-base-wraper>
</template>
<script>
export default {
  data() {
    return {
      roleForm: {
        roleId: undefined, // 角色id
        name: "", // 角色名称
        code: "", // 角色编号
        status: "", // 状态
        type: 1 // 代表是编辑或添加 不代表权限设置
      },
      dialogVisible: false,
      list: [], // 用户信息
      listLoading: false,
      listQuery: {
        name: undefined // 角色名称
      },
      total: 0,
      rules: {
        code: [{ required: true, message: "请输入角色编码", trigger: "blur" }],
        name: [{ required: true, message: "请输入角色名称", trigger: "blur" }]
      },
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "添加"
      }
    };
  },
  mounted() {},
  methods: {
    async refreshTableData() {
      this.$refs.baseTable.refresh();
    },
    saveForm() {
      this.$refs.roleForm.validate(async valid => {
        if (valid) {
          let res = await this.$api.sys_role[
            this.roleForm.roleId ? "update" : "add"
          ](this.roleForm);
          this.submitOk(res.msg);
          this.refreshTableData();
          this.dialogVisible = false;
        }
      });
    },
    update(row) {
      this.roleForm = Object.assign({}, row);
      this.dialogVisible = true;
      this.dialogStatus = "update";
    },
    add() {
      this.dialogVisible = true;
      this.dialogStatus = "create";
      this.roleForm.roleId = "";
      this.roleForm.name = "";
      this.roleForm.code = "";
    },
    async deleteData(id) {
      let res = await this.$api.sys_role.delete(id);
      this.submitOk(res.msg);
      this.refreshTableData();
    }
  }
};
</script>
<style scoped></style>
