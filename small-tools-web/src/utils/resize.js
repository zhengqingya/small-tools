export default {
  watch: {
    isOpen(val) {
      setTimeout(() => {
        this.chart.forceFit()
      }, 300)
    },
  },
  computed: {
    isOpen() {
      return this.$store.getters.sidebar.opened
    },
  },
}
