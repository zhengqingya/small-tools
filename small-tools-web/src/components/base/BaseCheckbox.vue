<!--
 * @Description: 
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-01-10 23:11:00
 * @LastEditors: yanxin
 * @LastEditTime: 2020-04-07 10:55:08
 -->
<template>
  <el-checkbox-group v-bind="$attrs" v-on="$listeners">
    <el-checkbox
      v-for="(item, index) in options"
      :key="index"
      :label="item.id"
      >{{ item.label }}</el-checkbox
    >
  </el-checkbox-group>
</template>
<script>
export default {
  name: 'BaseCheckbox',
  props: {
    options: {
      type: Array,
      default() {
        return []
      },
    },
  },
  data() {
    return {}
  },
}
</script>
