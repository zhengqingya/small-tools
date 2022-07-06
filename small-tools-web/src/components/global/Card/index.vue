<template>
  <div class="card">
    <div class="card-header">
      <slot name="header" />
    </div>
    <div class="card-body">
      <slot />
    </div>
  </div>
</template>
<script>
export default {
  name: 'Card',
  data() {
    return {}
  },
  methods: {},
}
</script>
<style lang="scss" scoped>
.card {
  background: rgba(255, 255, 255, 1);
  box-shadow: 0px 1px 6px 0px rgba(0, 0, 0, 0.02);
  border-radius: 2px;
  padding: 0 15px 15px;
  .card-header {
    padding: 10px 0;
    line-height: 28px;
    border-bottom: 1px solid #ebeef5;
  }
}
</style>
