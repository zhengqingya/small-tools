<template>
  <base-wraper>
    <base-header>
      <el-input v-model="listQuery.name" style="width: 200px" clearable @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleCreateProject">新建一个项目</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTable" api="cg_project.listPage" :params="listQuery">
      <el-table-column label="项目ID" prop="id" align="center" />
      <el-table-column label="项目名称" prop="name" align="center" />
      <el-table-column label="所属用户" prop="username" align="center" />
      <el-table-column align="center" :label="操作">
        <template v-slot="scope">
          <el-button link plain @click="handleUpdateProject(scope.row)">
            编辑
          </el-button>
          <el-button link plain @click="showPackageTree(scope.row)">编辑项目包</el-button>
          <base-delete-btn @ok="handleDeleteProject(scope.row)" />
        </template>
      </el-table-column>
    </base-table-p>

    <!-- 添加、修改项目 -->
    <base-dialog v-model="dialogProjectVisible" :title="titleMap[dialogStatus]" @close="handleDialogClose">
      <el-form ref="dataForm" :model="projectForm" :rules="rules" label-width="100px">
        <el-form-item label="项目名称:" prop="name">
          <el-input v-model="projectForm.name" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogProjectVisible = false">取消</el-button>
        <el-button type="primary" @click="submitProjectForm">确定</el-button>
      </template>
    </base-dialog>

    <!-- 树形包 -->
    <base-dialog v-model="dialogPackageTreeVisible" :title="titleMap[dialogStatus]" @close="handleDialogClose">
      <base-wraper>
        <div>
          <el-button type="primary" @click="getTree(projectForm.id)">刷新</el-button>
        </div>
        <el-row style="margin-top: 10px" :gutter="20">
          <el-col :span="16">
            <div>
              <el-card shadow="never">
                <div slot="header">
                  <span>
                    <label> {{ projectForm.name }} </label>
                  </span>
                </div>
                <div>
                  <el-tree :data="treeData" node-key="id" default-expand-all :expand-on-click-node="false">
                    <span slot-scope="{ node, data }" class="custom-tree-node">
                      <span>{{ node.label }}</span>
                      <span style="margin-left: 50px">
                        <el-button link size="mini" icon="el-icon-plus" @click="
                          () => handleCreatePackage(data, projectForm.name)
                        " />
                        <el-button link icon="el-icon-edit" @click="() => handleUpdatePackage(data)" />
                        <el-button link icon="el-icon-delete" @click="() => handleDeletePackage(node, data)" />
                      </span>
                    </span>
                  </el-tree>
                </div>
              </el-card>
            </div>
          </el-col>
        </el-row>
      </base-wraper>
    </base-dialog>

    <!-- 添加、修改项目包 -->
    <base-dialog v-model="dialogPackageVisible" :title="titleMap[dialogStatus]" @close="handleDialogClose">
      <el-form ref="dataForm" :model="packageForm" :rules="rulesPackage" label-width="100px">
        <el-form-item label="包名:" prop="name">
          <el-input v-model="packageForm.name" />
        </el-form-item>
        <el-form-item v-show="showPackageParent" label="父包:" prop="parentId">
          <el-select v-model="packageForm.parentId" placeholder="请选择父包" style="width: 260px">
            <el-option v-for="item in   packageList" :key="item.id" :value="item.id" :label="item.name" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogPackageVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPackageForm">确定</el-button>
      </template>
    </base-dialog>
  </base-wraper>
</template>

<script>
export default {
  name: 'Project',
  data() {
    return {
      dialogPackageTreeVisible: false, // 树形包弹出框
      dialogProjectVisible: false, // 项目form表单弹出框
      dialogPackageVisible: false, // 包弹出框
      // checkShow: false, // 树形包: 添加、修改、删除 按钮默认不显示
      showPackageParent: false, // 是否显示包上级选择栏
      treeData: [], // 树形包数据
      packageList: [], // 包列表
      statusList: [
        { key: 1, display_name: '启用' },
        { key: 0, display_name: '停用' },
      ],
      projectId: undefined, // 树包关联项目ID
      listQuery: {
        name: '',
      },
      projectForm: {
        id: undefined, // 项目ID
        name: undefined, // 项目名称
        username: undefined, // 所属用户名
      },
      packageForm: {
        id: undefined, // 包ID
        name: undefined, // 包名
        parentId: undefined, // 父包ID
        projectId: undefined, // 包关联项目ID
      },
      dialogStatus: '',
      titleMap: {
        updateProject: '编辑项目',
        createProject: '新建项目',
        treePackage: '项目树包',
        updatePackage: '编辑包',
        createPackage: '新建包',
      },
      rules: {
        name: [{ required: true, message: '请输入项目名称', trigger: 'blur' }],
      },
      rulesPackage: {
        name: [{ required: true, message: '请输入包名', trigger: 'blur' }],
      },
    };
  },
  created() { },
  methods: {
    refreshTableData() {
      this.$refs.baseTable.refresh();
    },
    // 项目树形包管理
    async getTree(id) {
      let response = await this.$api.cg_project_package.tree(id);
      var records = response.data;
      // 替换后端返回对象数据中的属性名为前端所要的属性名
      records = JSON.parse(JSON.stringify(records).replace(/name/g, 'label'));
      this.treeData = records;
    },
    showPackageTree(row) {
      this.getTree(row.id);
      this.projectId = row.id;
      this.projectForm = Object.assign({}, row);
      this.dialogStatus = 'treePackage';
      this.dialogPackageTreeVisible = true;
    },
    async getPackageList(id) {
      let res = await this.$api.cg_project_package.list(id);
      this.packageList = res.data;
    },
    handleCreatePackage(data, projectName) {
      this.resetForm();
      this.projectForm.id = data.projectId;
      this.projectForm.name = projectName;
      this.packageForm.parentId = data.id;
      this.packageForm.projectId = data.projectId;
      this.dialogStatus = 'createPackage';
      this.dialogPackageVisible = true;
    },
    handleUpdatePackage(data) {
      this.showPackageParent = true;
      // 如果是顶级包则不显示
      if (data.parentId === 0) {
        this.showPackageParent = false;
      }
      this.getPackageList(data.projectId);
      this.packageForm.id = data.id;
      this.packageForm.name = data.label;
      this.packageForm.parentId = data.parentId;
      this.packageForm.projectId = data.projectId;
      this.dialogStatus = 'updatePackage';
      this.dialogPackageVisible = true;
    },
    handleDeletePackage(node, data) {
      this.$confirm('此操作将永久删除该包, 是否继续?', '提示', {
        confirmButtonText: '确定',
        type: 'warning',
      }).then(async () => {
        await this.$api.cg_project_package.delete(data.id);
        this.getTree(this.projectId);
      });
    },
    submitPackageForm() {
      this.$refs.dataForm.validate(async (valid) => {
        if (valid) {
          let response = await this.$api.cg_project_package[
            this.packageForm.id ? 'update' : 'add'
          ](this.packageForm);
          this.getTree(response.data);
          this.submitOk(response.msg);
          this.dialogPackageVisible = false;
        }
      });
    },

    // ======================================================================

    // 项目管理
    handleCreateProject() {
      this.resetForm();
      this.dialogStatus = 'createProject';
      this.dialogProjectVisible = true;
    },
    handleUpdateProject(row) {
      this.projectForm = Object.assign({}, row);
      this.dialogStatus = 'updateProject';
      this.dialogProjectVisible = true;
    },
    async handleDeleteProject(row) {
      let response = await this.$api.cg_project.delete(row.id);
      this.refreshTableData();
      this.submitOk(response.msg);
    },
    submitProjectForm() {
      this.$refs.dataForm.validate(async (valid) => {
        if (valid) {
          let response = await this.$api.cg_project[
            this.projectForm.id ? 'update' : 'add'
          ](this.projectForm);
          this.refreshTableData();
          this.submitOk(response.msg);
          this.dialogProjectVisible = false;
        }
      });
    },
    resetForm() {
      this.projectForm = {
        id: undefined, // 项目ID
        name: undefined, // 项目名称
        username: undefined, // 所属用户名
      };
      this.packageForm = {
        id: undefined, // 包ID
        name: undefined, // 包名
        parentId: undefined, // 父包ID
        projectId: undefined, // 包关联项目ID
      };
    },
    // 监听dialog关闭时的处理事件
    handleDialogClose() {
      if (this.$refs['dataForm']) {
        this.$refs['dataForm'].clearValidate(); // 清除整个表单的校验
      }
      // 清空树包数据
      // this.treeData = [];
      // 隐藏上级包选择栏
      this.showPackageParent = false;
    },
  },
};
</script>
<style scoped>
</style>
