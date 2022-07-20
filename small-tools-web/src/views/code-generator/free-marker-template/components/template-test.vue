<template>
  <base-wraper>
    <base-dialog v-model="dialogVisible" title="测试模板数据" width="60%">
      <el-row :gutter="1">
        <el-col :span="12">
          <base-title-card title="模板数据：">
            <el-input v-model="templateContent" style="height: 400px;overflow-y:auto;overflow-x:hidden;" type="textarea"
              :rows="20" />
          </base-title-card>
        </el-col>
        <el-col :span="12">
          <base-title-card title="生成数据：">
            <el-input v-model="templateData" style="height: 400px;overflow-y:auto;overflow-x:hidden;" type="textarea"
              :rows="20" />
          </base-title-card>
        </el-col>
      </el-row>
      <template #footer>
        <el-button @click="dialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="submitForm">提交数据</el-button>
      </template>
    </base-dialog>
  </base-wraper>
</template>

<script>
export default {
  name: 'TemplateTest',
  data() {
    return {
      dialogVisible: false,
      templateContent: '',
      templateData: '',
    };
  },
  created() { },
  methods: {
    show() {
      this.dialogVisible = true;
    },
    async submitForm() {
      let res = await this.$api.cg_free_marker_template.testTemplateData({
        templateContent: this.templateContent,
      });
      this.submitOk(res.message);
      this.templateData = res.data;
      // this.dialogVisible = false;
    },
  },
};
</script>
<style scoped>
</style>
