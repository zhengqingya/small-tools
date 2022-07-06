<!--
 * @Description: 
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-08-03 15:32:02
 * @LastEditors: yanxin
 * @LastEditTime: 2020-08-04 15:43:47
-->
<template>
  <fragment>
    <th class="description-item-label description-item-colon">{{ label }}</th>
    <td :colspan="span" class="description-item-content">
      <slot></slot>
    </td>
  </fragment>
</template>
<script>
import { Fragment } from 'vue-fragment'
export default {
  components: {
    Fragment,
  },
  props: {
    span: {
      type: Number,
      default: 1,
    },
    label: {
      type: String,
      default: '名称',
    },
  },
}
</script>
<style lang="scss" scoped>
.description-item-label {
  color: rgba(0, 0, 0, 0.85);
  font-weight: 400;
  font-size: 14px;
  line-height: 1.5;
  text-align: inherit;
}

.description-item-label:after {
  position: relative;
  top: -0.5px;
  margin: 0 8px 0 2px;
  content: ' ';
}
.description-item-colon:after {
  content: ':';
}
.description-border .description-item-colon:after {
  content: '';
}
.description-item-content {
  color: rgba(0, 0, 0, 0.65);
  font-size: 14px;
  line-height: 1.5;
}
.description-border {
  .description-item-label {
    background-color: #fafafa;
  }
  .description-item-label,
  .description-item-content {
    padding: 16px 24px;
    border-right: 1px solid #e8e8e8;
  }
}
</style>
