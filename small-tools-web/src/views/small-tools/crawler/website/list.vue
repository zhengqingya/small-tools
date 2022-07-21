<template>
  <base-wraper>
    <base-header>
      <el-input v-model="listQuery.name" placeholder="请输入网站名称" style="width: 200px" clearable @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleCreate">添加</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTable" api="st_crawler_website.listPage" :params="listQuery">
      <el-table-column label="网站ID" prop="websiteId" width="100px" align="center" />
      <el-table-column label="网站名称" prop="name" align="center" />
      <el-table-column label="网站URL" prop="url" width="400px" align="left">
        <template v-slot="scope">
          <a :href="scope.row.url" target="_blank" style="color: #317bec">{{ scope.row.url }}</a>
        </template>
      </el-table-column>
      <el-table-column label="总文章数" width="100px" prop="articleSum" align="center" />
      <el-table-column align="center" label="操作" width="300">
        <template v-slot="scope">
          <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
          <base-delete-btn @ok="handleDelete(scope.row.websiteId)" />
          <el-button link @click="handleRefreshData(scope.row.url)">刷新爬虫数据</el-button>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogFormVisible" :title="titleMap[dialogStatus]" width="30%" @close="handleDialogClose">
      <el-form ref="dataForm" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="网站名称:" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="网站URL:" prop="url">
          <el-input v-model="form.url" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </base-dialog>
  </base-wraper>
</template>

<script>
export default {
  name: 'Website',
  data() {
    return {
      dialogFormVisible: false,
      listQuery: {
        name: undefined,
      },
      form: {
        websiteId: undefined, // 网站主键ID
        url: undefined, // 网站URL
        name: undefined, // 网站名称
      },
      dialogStatus: '',
      titleMap: {
        update: '编辑',
        create: '新增',
        detail: '详情',
      },
      rules: {
        url: [{ required: true, message: '请填写网站URL', trigger: 'blur' }],
        name: [{ required: true, message: '请填写网站名称', trigger: 'blur' }],
      },
    }
  },
  created() {},
  methods: {
    async refreshTableData() {
      this.$refs.baseTable.refresh()
    },
    handleCreate() {
      this.form = Object.assign({}, {})
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    handleUpdate(row) {
      this.form = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
    },
    async handleDelete(id) {
      let res = await this.$api.st_crawler_website.delete(id)
      this.submitOk(res.msg)
      this.refreshTableData()
    },
    async handleRefreshData(url) {
      let res = await this.$api.st_crawler_website.refreshData({ url: url })
      this.submitOk(res.msg)
      this.refreshTableData()
    },
    submitForm() {
      this.$refs.dataForm.validate(async (valid) => {
        if (valid) {
          let res = await this.$api.st_crawler_website[this.form.websiteId ? 'update' : 'add'](this.form)
          this.submitOk(res.msg)
          this.refreshTableData()
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
<style lang="scss" scoped></style>
