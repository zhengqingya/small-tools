<template>
  <my-base-wraper>
    <base-dialog :visible.sync="dialogVisible" title="测试模板数据" width="60%">
      <el-row :gutter="1">
        <el-col :span="12">
          <my-base-title-card title="模板数据：">
            <m-ace-editor
              v-model="templateContent"
              theme="clouds_midnight"
              height="400"
              :line-number="true"
            ></m-ace-editor>
          </my-base-title-card>
        </el-col>
        <el-col :span="12">
          <my-base-title-card title="生成数据：">
            <m-ace-editor
              v-model="templateData"
              theme="clouds_midnight"
              height="400"
              :line-number="true"
            ></m-ace-editor>
          </my-base-title-card>
        </el-col>
      </el-row>
      <div slot="footer">
        <el-button @click="dialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="submitForm">提交数据</el-button>
      </div>
    </base-dialog>
  </my-base-wraper>
</template>

<script>
export default {
  name: 'TemplateTest',
  data() {
    return {
      dialogVisible: false,
      templateContent: '',
      templateData: '',
    }
  },
  created() {},
  methods: {
    show() {
      this.dialogVisible = true
    },
    async submitForm() {
      let res = await this.$api.cg_free_marker_template.testTemplateData({
        templateContent: this.templateContent,
      })
      this.submitOk(res.message)
      this.templateData = res.data
      // this.dialogVisible = false;
    },
  },
}
</script>
<style scoped></style>
