<template>
  <ul
    class="table-cell"
    :class="[
      App.isDarkTheme ? 'table-cell-dark-theme' : '',
      labelPosition ? 'table-cell__label-' + labelPosition : '',
    ]"
  >
    <slot />
  </ul>
</template>
<script>
export default {
  name: 'TableCell',
  inject: ['App'],
  props: {
    labelPosition: {
      type: String,
      default: 'left',
    },
    labelWidth: {
      type: String,
      default: '200px',
    },
  },
  provide() {
    return {
      tableCell: this,
    }
  },
  data() {
    return {}
  },
  methods: {},
}
</script>
<style lang="scss" scoped>
ul.table-cell {
  line-height: 38px;
  width: 100%;
  background: rgba(249, 249, 249, 0);
  // border-top:1px solid rgba(235, 238, 245, 1);
  // border-right:1px solid rgba(235, 238, 245, 1);
  font-size: 12px;
  &.table-cell-dark-theme {
    // border-top:1px solid $dark_table_border_color;
    // border-right:1px solid $dark_table_border_color;
  }
}
ul.table-cell__label-left {
  .cell-item {
    text-align: left;
  }
}
ul.table-cell__label-right {
  .cell-item {
    text-align: right;
  }
}
</style>
