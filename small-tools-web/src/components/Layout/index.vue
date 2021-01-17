<template>
  <div id="app-wraper" :class="classObj" class="app-wrapper">
    <sidebar class="sidebar-container" />
    <div
      class="main-container"
      :class="{ 'dark-main-container': App.isDarkTheme }"
    >
      <div :class="{ 'fixed-header': fixedHeader }">
        <navbar />
      </div>
      <div v-show="showBreadcrumb" class="breadcrumb">
        <breadcrumb class="breadcrumb-container" />
      </div>
      <app-main />
    </div>
  </div>
</template>

<script>
import { Navbar, Sidebar, AppMain } from "./components";
import Breadcrumb from "@/components/Breadcrumb";
export default {
  name: "Layout",
  inject: ["App"],
  components: {
    Breadcrumb,
    Navbar,
    Sidebar,
    AppMain
  },
  data() {
    return {
      showBreadcrumb: true
    };
  },
  beforeRouteEnter(to, from, next) {
    if (to.name === "Dashboard") {
      next(vm => {
        vm.showBreadcrumb = false;
      });
    } else {
      next(vm => {
        vm.showBreadcrumb = true;
      });
    }
  },
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar;
    },
    device() {
      return this.$store.state.app.device;
    },
    fixedHeader() {
      return this.$store.state.settings.fixedHeader;
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened
      };
    }
  },
  methods: {}
};
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";
@import "~@/styles/variables.scss";

.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }
}
.breadcrumb {
  height: 40px;
}
.hideSidebar .fixed-header {
  width: calc(100% - 54px);
}
#app-wraper .dark-main-container {
  background: $dark_container_color;
}
</style>
