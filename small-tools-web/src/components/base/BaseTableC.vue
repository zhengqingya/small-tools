<!--
 * @Description:
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2019-12-25 09:51:13
 * @LastEditors: yanxin
 * @LastEditTime: 2020-06-30 14:45:31
 -->
<template>
  <div>
    <slot name="extra" :res="res"></slot>
    <el-table
      ref="baseTable"
      v-el-table-infinite-scroll="onReachBottom"
      v-loading="loading && res.records.length == 0"
      v-bind="$attrs"
      :class="{ pointer: pointer }"
      :data="res.records"
      :infinite-scroll-disabled="scrollDisabled"
      :infinite-scroll-immediate="true"
      :infinite-scroll-delay="300"
      :infinite-scroll-distance="20"
      border
      v-on="$listeners"
    >
      <template v-for="(item, index) in columns">
        <el-table-column v-if="item.slotName" :key="index" v-bind="item">
          <template slot-scope="scope">
            <slot :name="item.slotName" v-bind="scope"></slot>
          </template>
        </el-table-column>
        <el-table-column v-else :key="index" v-bind="item"> </el-table-column>
      </template>
      <template #append>
        <div
          v-if="!loading && res.current >= res.pages"
          class="el-loading-spinner"
        >
          <span v-if="res.total > 0" class="el-loading-text">没有更多了</span>
        </div>
        <template v-else>
          <div v-if="res.total > 0" class="el-loading-spinner">
            <i class="el-icon-loading"></i>
            <span class="el-loading-text">加载中</span>
          </div>
        </template>
      </template>
    </el-table>
  </div>
</template>
<script>
export default {
  name: 'BaseTableC',
  model: {
    event: 'load',
  },
  props: {
    columns: {
      type: Array,
      default() {
        return []
      },
    },
    params: {
      type: Object,
      default() {
        return {}
      },
    },
    api: {
      type: String,
      default: 'ownerUnitList',
    },
    pointer: {
      //行是否显示手指的手势
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      loading: true,
      pageParams: {
        pageNum: 0,
        pageSize: 20,
      },
      res: {
        current: 1,
        pages: 2,
        size: 10,
        total: 0,
        records: [],
      },
    }
  },
  computed: {
    scrollDisabled: function () {
      return this.res.current >= this.res.pages
    },
    apiMethod() {
      return this.api.split('.').reduce((acc, item) => {
        return acc[item]
      }, this.$api)
    },
  },
  methods: {
    scrollTop() {
      //回到顶部
      document.getElementsByClassName('el-table__body-wrapper')[0].scrollTop = 0
    },
    async getList() {
      this.loading = true
      let newRes = await this.apiMethod(this.params, this.pageParams)
      this.loading = false
      let oldRes = this.res
      if (this.pageParams.pageNum != 1) {
        newRes.records = [...oldRes.records, ...newRes.records]
      }
      this.res = newRes
    },
    //触底
    onReachBottom() {
      this.pageParams.pageNum = this.pageParams.pageNum + 1
      this.getList()
    },
    async refresh() {
      // let curentPageNum = this.pageParams.pageNum;
      // let oldPageNum = this.res.total;
      // //更新后的页数
      // let updatedPageNum = Math.ceil((this.res.total + 1) / this.pageParams.pageSize);
      // //如果更新后的页数比更新前页数多，则记录的页数为更新后的页数
      // if (updatedPageNum > oldPageNum && curentPageNum == oldPageNum) {
      //     curentPageNum = updatedPageNum;
      // }
      // for (let i = 0; i < curentPageNum; i++) {
      //     this.pageParams.pageNum = i + 1;
      //     await this.getList();
      // }
      //解决指令不能动态改变infinite-scroll-disabled，手动改变
      let elTableScrollWrapperClass = '.el-table__body-wrapper'
      let element = this.$refs.baseTable.$el
      let scrollElem = element.querySelector(elTableScrollWrapperClass)
      scrollElem.removeAttribute('infinite-scroll-disabled')
      this.res = {
        current: 1,
        pages: 2,
        size: 10,
        total: 0,
        records: [],
      }
      this.pageParams.pageNum = 1
      await this.getList()
    },
  },
}
</script>
<style lang="scss">
.pointer /deep/ .el-table__row {
  cursor: pointer;
}
.el-table th {
  background: #f5f7fa;
}
</style>
<style lang="scss" scoped>
.el-loading-spinner {
  position: relative;
  height: 30px;
  margin: 0;
  font-size: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  .el-loading-text {
    margin-left: 5px;
  }
}
</style>
