<template>
  <section class="app-main" :class="{ 'no-crumbs': isNoCrumb }">
    <!-- <transition name="fade-transform" mode="out-in"> -->
    <router-view :key="key" />
    <!-- </transition> -->
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
    key() {
      return this.$route.path
    },
    isNoCrumb() {
      const isHome = this.$route.path.indexOf('dashboard') > -1
      return isHome
    },
  },
}
</script>

<style lang="scss" scoped>
.app-main {
  /*50 = navbar  */
  height: calc(100vh - 90px);
  width: 100%;
  position: relative;
  overflow-y: auto;
  &.no-crumbs {
    height: calc(100vh - 50px);
  }
}
.fixed-header + .app-main {
  padding-top: 50px;
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
</style>
