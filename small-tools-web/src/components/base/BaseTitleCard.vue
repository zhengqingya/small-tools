<template>
  <div class="title-card">
    <h2>
      <span>{{ title }}</span>
      <div class="prefix">
        <slot name="prefix" />
      </div>
      <div class="append" style="margin-top: 12px;">
        <slot name="append" />
      </div>
    </h2>
    <div class="content">
      <slot />
    </div>
  </div>
</template>
<script>
export default {
  name: "TitleCard",
  inject: ["App"],
  props: {
    title: {
      type: String,
      default: "标题"
    }
  },
  data() {
    return {};
  },
  methods: {}
};
</script>
<style lang="scss" scoped>
.title-card {
  background: #fff;
  padding: 0px;
  h2 {
    position: relative;
    border-bottom: 1px solid #ebeef5;
    padding-bottom: 13px;
    margin-bottom: 15px;
    span {
      font-size: 14px;
      font-weight: bold;
      color: #303133;
    }
    .prefix {
      display: inline-block;
      margin-left: 20px;
    }
    .append {
      position: absolute;
      right: 0;
      top: -10px;
    }
  }
  .content {
    padding-bottom: 10px;
    position: relative;
  }
  -webkit-box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>
