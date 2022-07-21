<template>
  <base-wraper>
    <base-header>
      <el-select v-model="listQuery.projectId" placeholder="项目" filterable style="width: 200px" @change="handleProjectChooseChange()">
        <el-option value="" label="全部" />
        <el-option v-for="item in projectList" :key="item.id" :value="item.id" :label="item.name" />
      </el-select>
      <el-button type="success" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" icon="el-icon-plus" @click="handleCreate">添加</el-button>
        <el-button type="warning" icon="el-icon-search" @click="getVelocityContextList"> 参考模板数据源配置信息 </el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTable" api="cg_project_template.listPage" :params="listQuery">
      <el-table-column align="center" label="ID" width="50" prop="projectTemplateId" />
      <el-table-column label="展开" align="center" type="expand">
        <template v-slot="scope">
          <div v-highlight>
            <pre><code v-html="scope.row.content" /></pre>
          </div>
        </template>
      </el-table-column>
      <el-table-column width="100" label="项目名称" align="center" prop="projectName" />
      <el-table-column width="80" label="类型" align="center" prop="packageName" />
      <el-table-column width="150" label="文件名" align="center" prop="fileName" />
      <el-table-column width="80" label="文件后缀名" align="center" prop="fileSuffix" />
      <el-table-column label="内容" align="center" prop="content" show-overflow-tooltip />
      <el-table-column align="center" width="250" label="操作">
        <template v-slot="scope">
          <el-button link plain @click="handleUpdate(scope.row)">编辑</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)" />
        </template>
      </el-table-column>
    </base-table-p>

    <!-- 模板弹出框 -->
    <base-dialog v-model="dialogFormVisible" :title="textMap[dialogStatus]" top="50px" width="80%" @close="handleDialogClose">
      <el-form ref="dataForm" class="small-space" :model="form" label-position="left" label-width="120px" :rules="rules" size="small">
        <el-row :gutter="1">
          <el-col :span="12">
            <el-form-item label="归属项目:" prop="projectId">
              <el-select v-model="form.projectId" filterable placeholder="归属项目" @change="handleProjectChange(form.projectId)">
                <el-option v-for="item in projectList" :key="item.id" :value="item.id" :label="item.name" />
              </el-select>
            </el-form-item>
            <el-form-item label="生成文件名:" prop="fileName">
              <el-input v-model="form.fileName" style="width: 200px" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="模板类型:" prop="type">
              <el-select v-model="form.projectPackageId" filterable placeholder="模板类型">
                <el-option v-for="item in templateTypeList" :key="item.id" :value="item.id" :label="item.name" />
              </el-select>
            </el-form-item>
            <el-form-item label="生成文件后缀名:" prop="fileSuffix">
              <el-select v-model="form.fileSuffix" filterable placeholder="ex:   .java  或  .html">
                <el-option v-for="item in fileSuffixList" :key="item.value" :value="item.value" :label="item.name" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="1">
          <el-col :span="11">
            <base-title-card title="模板内容：">
              <el-input
                v-model="form.content"
                style="height: 400px; overflow-y: auto; overflow-x: hidden"
                type="textarea"
                :rows="20"
                placeholder="请输入内容"
              />
            </base-title-card>
          </el-col>
          <el-col :span="2" style="height: 400px; text-align: center">
            <el-button type="primary" style="margin-top: 200px" @click="handleTestTemplateData">测试生成数据</el-button>
          </el-col>
          <el-col :span="11">
            <base-title-card title="生成数据：">
              <el-input v-model="templateData" style="height: 400px; overflow-y: auto; overflow-x: hidden" type="textarea" :rows="20" disabled>
              </el-input>
            </base-title-card>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button size="small" @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" size="small" @click="submitForm">确定</el-button>
      </template>
    </base-dialog>

    <!-- 模板数据源弹出框 -->
    <base-dialog v-model="dialogVelocityContextVisible" :title="textMap[dialogStatus]" @close="handleDialogClose">
      <base-table-p ref="projectVelocityContextTable" api="cg_project_template.listPageCodeProjectVelocityContext" :params="listQueryVelocityContext">
        <el-table-column type="index" label="序号" width="50" />
        <el-table-column label="展开" align="center" type="expand">
          <template v-slot="scope">
            <div v-highlight>
              <pre><code v-html="scope.row.contextJsonObject" /></pre>
            </div>
          </template>
        </el-table-column>
        <el-table-column width="140" label="模板数据源" align="center">
          <template v-slot="scope">
            <span> ${ {{ scope.row.velocity }} } </span>
          </template>
        </el-table-column>
        <el-table-column label="内容" align="center" prop="context" show-overflow-tooltip />
      </base-table-p>
    </base-dialog>
  </base-wraper>
</template>

<script>
export default {
  name: 'ProjectTemplate',
  data() {
    return {
      projectList: [],
      fileSuffixList: [],
      templateTypeList: [],
      totalVelocityContext: 0,
      listQuery: {
        isBasic: 0, // 项目模板数据
        projectId: '',
      },
      listQueryVelocityContext: {
        isBasic: 0, // 项目模板数据
        projectId: 0,
      },
      form: {},
      templateData: '',
      dialogFormVisible: false,
      dialogVelocityContextVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建',
        showVelocityContext: '项目模板数据源',
      },
      rules: {},
    }
  },
  created() {
    this.getFileSuffixList('file_suffix')
    this.getProjectList()
  },
  methods: {
    async refreshTableData() {
      this.$refs.baseTable.refresh()
    },
    async getProjectList() {
      let res = await this.$api.cg_project.list({})
      this.projectList = res.data
    },
    async getPackageList(id) {
      let res = await this.$api.cg_project_package.list(id)
      this.templateTypeList = res.data
    },
    async getFileSuffixList(data) {
      let res = await this.$api.sys_dict.listFromCacheByCode(data)
      this.fileSuffixList = res.data
    },
    async getVelocityContextList() {
      if (!this.listQuery.projectId) {
        this.$message({ message: '请先选择项目!', type: 'warning' })
        return
      }
      this.dialogStatus = 'showVelocityContext'
      this.dialogVelocityContextVisible = true
      this.listQueryVelocityContext.projectId = this.listQuery.projectId
      this.$refs.projectVelocityContextTable.refresh()
    },
    handleProjectChooseChange() {
      this.refreshTableData()
    },
    handleProjectChange(projectId) {
      this.getPackageList(projectId)
    },
    handleCreate() {
      this.templateData = ''
      this.templateTypeList = []
      this.form = Object.assign({}, {})
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    handleUpdate(row) {
      this.templateData = ''
      this.getPackageList(row.projectId)
      this.form = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
    },
    async handleDelete(row) {
      let res = await this.$api.cg_project_template.delete(row.projectTemplateId)
      this.refreshTableData()
      this.submitOk(res.msg)
    },
    async handleTestTemplateData() {
      this.form.isBasic = 0
      let res = await this.$api.cg_project_template.testTemplateData(this.form)
      this.submitOk(res.message)
      this.templateData = res.data
    },
    submitForm() {
      this.form.projectName = undefined
      this.$refs.dataForm.validate(async (valid) => {
        if (valid) {
          this.form.isBasic = 0
          let res = await this.$api.cg_project_template[this.form.projectTemplateId ? 'update' : 'add'](this.form)
          this.refreshTableData()
          this.submitOk(res.msg)
          this.dialogFormVisible = false
        }
      })
    },
    // 监听dialog关闭时的处理事件
    handleDialogClose() {
      if (this.$refs['dataForm']) {
        this.$refs['dataForm'].clearValidate() // 清除整个表单的校验
      }
    },
  },
}
</script>
<style scoped></style>
