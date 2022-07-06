<template>
  <div class="wraper" :class="{ 'is-full-height': fullHeight }">
    <slot />
  </div>
</template>
<script>
export default {
  name: 'Wraper',
  inject: ['App'],
  props: {
    fullHeight: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {}
  },
  methods: {},
}
</script>
<style lang="scss" scoped>
.wraper {
  padding: 0px;
  &.is-full-height {
    min-height: 100%;
  }
  background: #fff;
  border-radius: 2px;
}
</style>
