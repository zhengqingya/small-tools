<template>
  <base-wraper>
    <base-header>
      <el-select v-model="listQuery.websiteId" placeholder="网站" style="width: 200px" clearable @change="handleChangeWebsite(listQuery.websiteId)">
        <el-option value="" label="全部" />
        <el-option v-for="item in websiteList" :key="item.websiteId" :value="item.websiteId" :label="item.name" />
      </el-select>
      <el-input v-model="listQuery.title" placeholder="请输入文章标题" style="width: 200px" clearable @clear="refreshTableData" />
      <el-date-picker v-model="listQuery.startTime" type="date" value-format="yyyy-MM-dd HH:mm:ss" placeholder="开始时间" />
      <el-date-picker v-model="listQuery.endTime" type="date" value-format="yyyy-MM-dd 23:59:59" placeholder="结束时间" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <el-button type="warning" @click="restartFilter">重置</el-button>
      <template #right>
        <base-upload-button
          v-if="showExportOrImportDataButton"
          api="st_crawler_article_info.importData"
          :params="listQuery"
          :show-file-list="false"
          @success="handleSuccess"
        >
          <el-button type="danger">导入数据</el-button>
        </base-upload-button>
        <el-button v-if="showExportOrImportDataButton" type="success" @click="handleExportExcelData(listQuery.websiteId)">导出Excel</el-button>
        <el-button v-if="showExportOrImportDataButton" type="warning" @click="handleExportAllData(listQuery.websiteId)"> 导出所有数据</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTable" api="st_crawler_article_info.listPage" :params="listQuery">
      <el-table-column label="ID" prop="articleInfoId" width="100px" align="center" />
      <el-table-column label="数据来源网站" prop="websiteName" width="150px" align="center" />
      <el-table-column label="分类" prop="category" width="200px" align="left" />
      <el-table-column label="标题" prop="title" align="left" />
      <el-table-column label="文章url" show-overflow-tooltip align="center">
        <template v-slot="scope">
          <a :href="scope.row.url" target="_blank" style="color: #317bec">{{ scope.row.url }}</a>
        </template>
      </el-table-column>
      <el-table-column label="发布时间" prop="publishTime" align="center">
        <template v-slot="scope">
          <span>{{ scope.row.publishTime }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="150">
        <template v-slot="scope">
          <el-button link @click="handleDetail(scope.row)">详情</el-button>
          <el-button link @click="showExportData(scope.row)">导出</el-button>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogFormVisible" :title="titleMap[dialogStatus]" width="50%">
      <el-form :model="form" label-width="100px">
        <base-table-cell>
          <base-cell-item label="ID">{{ form.articleInfoId }}</base-cell-item>
        </base-table-cell>
        <base-table-cell>
          <base-cell-item label="数据来源网站">{{ form.websiteName }}</base-cell-item>
        </base-table-cell>
        <base-table-cell>
          <base-cell-item label="分类">{{ form.category }}</base-cell-item>
        </base-table-cell>
        <base-table-cell>
          <base-cell-item label="标题">{{ form.title }}</base-cell-item>
        </base-table-cell>
        <base-table-cell>
          <base-cell-item label="元数据URL"
            ><a :href="form.url" target="_blank" style="color: #317bec">{{ form.url }}</a></base-cell-item
          >
        </base-table-cell>
        <base-table-cell>
          <base-cell-item label="内容" style="height: 300px; overflow-y: auto; overflow-x: hidden">
            <p v-html="form.content" />
          </base-cell-item>
        </base-table-cell>
        <base-table-cell>
          <base-cell-item label="发布时间">{{ form.publishTime }}</base-cell-item>
        </base-table-cell>
      </el-form>
    </base-dialog>

    <base-dialog v-model="exportDialogFormVisible" :title="titleMap[dialogStatus]" width="30%">
      <el-form :model="exportForm" label-width="100px">
        <el-form-item label="文章标题">
          <el-input v-model="exportForm.title" disabled />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="exportForm.type" placeholder="请选择类型" style="width: 200px">
            <el-option v-for="item in exportTypeList" :key="item.value" :value="item.value" :label="item.name" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="exportDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleExportData">确 定</el-button>
      </template>
    </base-dialog>
  </base-wraper>
</template>

<script>
function export_raw(name, data) {
  var urlObject = window.URL || window.webkitURL || window
  var export_blob = new Blob([data])
  var save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a')
  save_link.href = urlObject.createObjectURL(export_blob)
  save_link.download = name
  save_link.click()
}

export default {
  name: 'ArticleInfo',
  data() {
    return {
      uploadUrl: import.meta.env.VITE_APP_BASE_API + '/api/smallTools/crawler/articleInfo/importData', //http://127.0.0.1:5000
      dialogFormVisible: false,
      exportDialogFormVisible: false,
      showExportOrImportDataButton: false,
      websiteList: [],
      exportTypeList: [],
      listQuery: {
        websiteId: '',
        title: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      form: {},
      exportForm: {
        articleInfoId: undefined,
        title: undefined,
        type: '1',
      },
      dialogStatus: '',
      titleMap: {
        update: '编辑',
        create: '新增',
        detail: '详情',
        export: '导出文章数据',
      },
    }
  },
  created() {
    this.getWebsiteList()
    this.getExportType()
  },
  methods: {
    async refreshTableData() {
      this.$refs.baseTable.refresh()
    },
    async getWebsiteList() {
      let res = await this.$api.st_crawler_website.list()
      this.websiteList = res.data
    },
    async getExportType() {
      let res = await this.$api.sys_dict.listFromCacheByCode('st_crawler_csdn_export_data_type')
      this.exportTypeList = res.data
    },
    showExportData(row) {
      this.exportForm.articleInfoId = row.articleInfoId
      this.exportForm.title = row.title
      this.dialogStatus = 'export'
      this.exportDialogFormVisible = true
    },
    async handleExportData() {
      let res = await this.$api.st_crawler_article_info.exportData(this.exportForm)
      document.location.href = res.data
      // export_raw("xx.html", res.data);
      this.submitOk(res.msg)
      this.exportDialogFormVisible = false
    },
    handleChangeWebsite(websiteId) {
      if (websiteId) {
        this.showExportOrImportDataButton = true
      } else {
        this.showExportOrImportDataButton = false
      }
      this.refreshTableData()
    },
    async handleExportAllData(id) {
      let res = await this.$api.st_crawler_article_info.exportAllDataByWebsiteId(id)
      this.submitOk(res.msg)
      document.location.href = res.data
    },
    async handleExportExcelData(id) {
      let res = await this.$api.st_crawler_article_info.exportExcelByWebsiteId(id)
      this.submitOk(res.msg)
      document.location.href = res.data
    },
    async handleImportData() {
      let res = await this.$api.st_crawler_article_info.importData(1)
      this.submitOk(res.msg)
    },
    restartFilter() {
      this.listQuery.websiteId = ''
      this.listQuery.title = undefined
      this.listQuery.startTime = undefined
      this.listQuery.endTime = undefined
      this.refreshTableData()
      this.showExportOrImportDataButton = false
    },
    handleDetail(row) {
      this.form = Object.assign({}, row)
      this.dialogStatus = 'detail'
      this.dialogFormVisible = true
    },
    // 文件处理 ==============
    handleSuccess(res, fileInfo) {
      this.submitOk(res.msg)
      this.refreshTableData()
    },
  },
}
</script>
<style lang="scss" scoped></style>
