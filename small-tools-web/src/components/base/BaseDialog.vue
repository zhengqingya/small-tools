<!--
 * @Description: 
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-01-08 10:07:56
 * @LastEditors: yanxin
 * @LastEditTime: 2020-08-20 15:38:11
 -->
<template>
  <el-dialog
    v-bind="$attrs"
    :destroy-on-close="false"
    :modal-append-to-body="false"
    :close-on-click-modal="false"
    v-on="$listeners"
  >
    <slot></slot>
    <template #title>
      <slot name="title"></slot>
    </template>
    <template #footer>
      <slot name="footer"></slot>
    </template>
  </el-dialog>
</template>
<script>
export default {
  name: 'BaseDialog',
  data() {
    return {}
  },
}
</script>
<style lang="scss" scoped>
/deep/ .el-dialog {
  border-radius: 4px;
}
/deep/ .el-dialog__body {
  padding: 20px;
}
/deep/ .el-dialog__title {
  font-size: 16px;
}
</style>
