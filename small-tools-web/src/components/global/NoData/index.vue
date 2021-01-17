<template>
  <div class="no-data" :class="{ 'no-data-dark': App.isDarkTheme }">
    <slot>暂无数据</slot>
  </div>
</template>
<script>
export default {
  name: "NoData",
  inject: ["App"],
  data() {
    return {};
  },
  methods: {}
};
</script>
<style lang="scss" scoped>
.no-data {
  padding: 100px 0;
  background: #fff;
  font-size: 28px;
  color: #999;
  text-align: center;
  &.no-data-dark {
    background: $dark_main_color;
    color: #fff;
  }
}
</style>
