<template>
  <base-wraper>
    <base-header>
      <el-input v-model="listQuery.templateKey" placeholder="请输入键" style="width: 200px" clearable @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="success" @click="showTest">测试</el-button>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTable" api="cg_free_marker_template.listPage" :params="listQuery">
      <el-table-column label="ID" prop="freeMarkerTemplateId" align="center"></el-table-column>
      <el-table-column label="键" prop="templateKey" align="center"></el-table-column>
      <el-table-column label="值" prop="templateValue" align="center"></el-table-column>
      <el-table-column label="数据类型" align="center">
        <template v-slot="scope">
          <span :style="scope.row.isCommon === 1 ? 'background-color: #00FFFF' : 'background-color: #C0C0C0'">{{ scope.row.isCommonName }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template v-slot="scope">
          <el-button v-if="scope.row.templateReUserId === currentUserId" link @click="handleUpdate(scope.row)">编辑 </el-button>
          <base-delete-btn v-if="scope.row.templateReUserId === currentUserId" @ok="handleDelete(scope.row)"> </base-delete-btn>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogVisible" :title="titleMap[dialogStatus]" width="30%">
      <el-form ref="dataForm" :model="form" :rules="rules" label-width="80px">
        <el-form-item v-if="dialogStatus !== 'add'" label="ID:" prop="freeMarkerTemplateId">
          <el-input v-model="form.freeMarkerTemplateId" disabled></el-input>
        </el-form-item>
        <el-form-item label="键:" prop="templateKey">
          <el-input v-model="form.templateKey"></el-input>
        </el-form-item>
        <el-form-item label="值:" prop="templateValue">
          <el-input v-model="form.templateValue"></el-input>
        </el-form-item>
        <el-form-item v-if="currentUserId === 1" label="数据类型:">
          <el-select v-model="form.isCommon" placeholder="请选择" style="width: 280px">
            <el-option v-for="item in commonTypeList" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </base-dialog>

    <template-test ref="templateTest"></template-test>
  </base-wraper>
</template>

<script>
import TemplateTest from './components/template-test.vue'
import useStore from '@/store'
export default {
  name: 'CodeFreeMarkerTemplate',
  components: { TemplateTest },
  data() {
    return {
      currentUserId: useStore().user.userId,
      dialogVisible: false,
      listQuery: { templateKey: '' },
      form: {
        freeMarkerTemplateId: undefined, //主键ID
        templateKey: undefined, //键
        templateValue: undefined, //值
        isCommon: undefined, // 数据类型
      },
      commonTypeList: [
        { key: 1, display_name: '共用数据' },
        { key: 0, display_name: '个人数据' },
      ],
      dialogStatus: '',
      titleMap: {
        add: '添加',
        update: '编辑',
        detail: '详情',
      },
      rules: {},
    }
  },
  created() {},
  methods: {
    refreshTableData() {
      this.$refs.baseTable.refresh()
    },
    handleAdd() {
      this.form = Object.assign({}, {})
      this.dialogStatus = 'add'
      this.dialogVisible = true
    },
    showTest() {
      this.$refs.templateTest.show()
    },
    handleUpdate(row) {
      this.form = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogVisible = true
    },
    async handleDelete(row) {
      let res = await this.$api.cg_free_marker_template.delete(row.freeMarkerTemplateId)
      this.refreshTableData()
      this.submitOk(res.message)
    },
    submitForm() {
      this.$refs.dataForm.validate(async (valid) => {
        if (valid) {
          let res = await this.$api.cg_free_marker_template[this.form.freeMarkerTemplateId ? 'update' : 'add'](this.form)
          this.refreshTableData()
          this.submitOk(res.message)
          this.dialogVisible = false
        }
      })
    },
  },
}
</script>
<style scoped></style>
