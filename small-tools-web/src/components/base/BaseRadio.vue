<!--
 * @Description: 
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-01-10 23:11:00
 * @LastEditors: yanxin
 * @LastEditTime: 2020-04-07 10:55:35
 -->
<template>
  <el-radio-group v-bind="$attrs" v-on="$listeners">
    <el-radio v-for="(item, index) in options" :key="index" :label="item.id">
      {{ item.label }}
    </el-radio>
  </el-radio-group>
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
