<!--
 * @Description:
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-01-08 10:07:56
 * @LastEditors: yanxin
 * @LastEditTime: 2020-08-14 09:54:01
 -->
<template>
  <div>
    <el-image :src="url" style="width: 100px; height: 100px" />
    <el-upload
      :action="uploadUrl"
      :show-file-list="false"
      :headers="dataToken"
      :before-upload="beforeUpload"
      :on-success="onSuccess"
    >
      <el-button size="small">点击上传</el-button>
    </el-upload>
  </div>
</template>
<script>
import { getToken } from '@/utils/auth'
export default {
  name: 'BaseUpload',
  model: {
    event: 'change',
  },
  props: {
    url: {
      type: String,
      default: '',
    },
  },
  data() {
    const upload_url = this.$store.state.app.uploadFileUrl
    return {
      uploadUrl: upload_url,
      dataToken: { Authorization: getToken() },
      fileList: [],
    }
  },
  computed: {},
  methods: {
    beforeUpload(file) {
      const patt = /^[a-z0-9A-Z\u4e00-\u9fa5|\.]+$/
      if (!patt.test(file.name)) {
        this.$message({
          type: 'error',
          message:
            '上传失败：文件名只能为中文，数字或大小写字母，请修改文件名或重新上传文件!',
          duration: 5000,
        })
        return false
      }
      return true
    },
    async onSuccess(res, file) {
      const url = res.data
      this.url = url
      // this.submitOk("成功");
      this.$emit('saveSucc', url)
    },
  },
}
</script>
<style lang="scss" scoped></style>
