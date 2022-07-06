<!--
 * @Description:
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-01-08 10:07:56
 * @LastEditors  : yanxin
 * @LastEditTime : 2020-02-12 15:35:29
 -->
<template>
  <el-switch
    v-bind="$attrs"
    :active-value="activeValue"
    :inactive-value="inactiveValue"
    v-on="$listeners"
  ></el-switch>
</template>
<script>
export default {
  name: 'BaseSwitch',
  props: {
    activeValue: {
      type: [Boolean, String, Number],
      default: 1,
    },
    inactiveValue: {
      type: [Boolean, String, Number],
      default: 0,
    },
  },
  data() {
    return {}
  },
}
</script>
