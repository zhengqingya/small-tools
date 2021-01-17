<template>
  <div
    class="wraper"
    :class="{ 'is-full-height': fullHeight, 'dark-theme': App.isDarkTheme }"
  >
    <slot />
  </div>
</template>
<script>
export default {
  name: "Wraper",
  inject: ["App"],
  props: {
    fullHeight: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {};
  },
  methods: {}
};
</script>
<style lang="scss" scoped>
.wraper {
  padding: 15px;
  &.is-full-height {
    min-height: 100%;
  }
  background: #fff;
  border-radius: 2px;
  &.dark-theme {
    background: $dark_main_color;
  }
}
</style>
