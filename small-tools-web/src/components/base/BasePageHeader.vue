<!--
 * @Description: 
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-02-18 17:07:57
 * @LastEditors: yanxin
 * @LastEditTime: 2020-08-03 15:24:47
 -->
<template>
  <div class="page-head">
    <slot name="logo">
      <el-image
        style="width: 20px; height: 20px; margin-right: 20px"
      ></el-image>
    </slot>
    <div class="page-header-main">
      <div class="page-header-row">
        <div class="page-header-title">我是标题</div>
        <div class="page-header-action">
          <el-button type="primary">主操作</el-button>
        </div>
      </div>
      <div class="page-header-row">
        <div class="page-header-content"></div>
      </div>
    </div>
  </div>
</template>
<style lang="scss" scoped>
.page-head {
  display: flex;
  align-items: flex-start;
  .page-header-main {
    width: 100%;
    .page-header-row {
      width: 100%;
      display: flex;
      .page-header-title {
        color: #17233d;
        font-weight: 500;
        font-size: 18px;
        flex: 1;
        margin-bottom: 16px;
      }
      .page-header-action {
        min-width: 266px;
        margin-left: 56px;
        text-align: right;
      }
      .page-header-content {
        margin-bottom: 16px;
        font-size: 14px;
      }
    }
  }
}
</style>
