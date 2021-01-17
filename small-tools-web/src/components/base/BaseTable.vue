<!--
 * @Description:
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2019-12-25 09:51:13
 * @LastEditors: yanxin
 * @LastEditTime: 2020-03-06 17:47:44
 -->
<template>
  <el-table v-bind="$attrs" v-on="$listeners">
    <template v-for="(item, index) in columns">
      <el-table-column v-if="item.slotName" :key="index" v-bind="item">
        <template slot-scope="scope">
          <slot :name="item.slotName" v-bind="scope"></slot>
        </template>
      </el-table-column>
      <el-table-column v-else :key="index" v-bind="item"> </el-table-column>
    </template>
  </el-table>
</template>
<script>
export default {
  name: "BaseTable",
  props: {
    columns: {
      type: Array,
      default() {
        return [];
      }
    }
  },
  data() {
    return {};
  },
  created() {}
};
</script>
