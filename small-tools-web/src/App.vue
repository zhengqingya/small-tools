<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "App",
  computed: {
    ...mapGetters(["isDarkTheme"])
  },
  provide() {
    return {
      App: this
    };
  },
  watch: {
    isDarkTheme(val) {
      const bodyEle = document.querySelector("body");
      if (val) {
        bodyEle.classList.add("cus-dark-theme");
      } else {
        bodyEle.classList.remove("cus-dark-theme");
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      if (this.isDarkTheme) {
        const bodyEle = document.querySelector("body");
        bodyEle.classList.add("cus-dark-theme");
      }
    });
  }
};
</script>
