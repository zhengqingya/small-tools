<!--
 * @Description: 
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-01-10 23:11:00
 * @LastEditors: yanxin
 * @LastEditTime: 2020-04-07 10:55:52
 -->
<template>
  <el-select v-bind="$attrs" v-on="$listeners">
    <el-option
      v-for="(item, index) in options"
      :key="index"
      :label="item.label"
      :value="item.id"
    ></el-option>
  </el-select>
</template>
<script>
export default {
  name: "BaseSelect",
  props: {
    options: {
      type: Array,
      default() {
        return [];
      }
    }
  },
  data() {
    return {};
  }
};
</script>
<style lang="scss" scoped>
/deep/ .el-input {
  width: 100%;
}
</style>
