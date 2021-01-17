<!--
 * @Description: 
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-04-15 13:39:38
 * @LastEditors: yanxin
 * @LastEditTime: 2020-06-30 13:40:27
 -->
<template>
  <div
    class="base-header"
    :class="{ border: border }"
    :style="{ paddingTop: top + 'px', paddingBottom: bottom + 'px' }"
  >
    <div class="left">
      <slot></slot>
    </div>
    <slot name="right"></slot>
  </div>
</template>
<script>
export default {
  name: "BaseHeader",
  props: {
    top: {
      type: Number,
      default: 0
    },
    bottom: {
      type: Number,
      default: 10
    },
    border: {
      type: Boolean,
      default: false
    }
  }
};
</script>
<style lang="scss" scoped>
.base-header {
  display: flex;
  align-items: center;
  .left {
    flex: 1;
  }
}
.border {
  border-bottom: 1px solid #eee;
}
</style>
