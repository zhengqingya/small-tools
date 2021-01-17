<!--
 * @Description:
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-02-26 11:11:48
 * @LastEditors: yanxin
 * @LastEditTime: 2020-05-20 16:27:20
 -->
<template>
  <div>
    <el-select
      ref="select"
      v-bind="$attrs"
      :multiple="multiple"
      v-on="$listeners"
      @change="valueChange"
    >
      <el-option
        v-for="item in options"
        v-show="false"
        :key="item[defaultProps.value]"
        :label="item[defaultProps.label]"
        :value="item[defaultProps.value]"
      >
      </el-option>
      <el-input
        v-model="filterText"
        placeholder="输入关键字搜索"
        class="search-input"
      ></el-input>
      <el-tree
        ref="treeBox"
        :data="data"
        :props="defaultProps"
        :node-key="defaultProps.value"
        :show-checkbox="multiple"
        default-expand-all
        check-on-click-node
        :expand-on-click-node="false"
        :highlight-current="true"
        :check-strictly="defaultProps.checkStrictly"
        :filter-node-method="filterNode"
        @check-change="handerCheckChange"
      ></el-tree>
    </el-select>
  </div>
</template>
<script>
//树形结构转为一维数组
const getAll = function(data, children = "children", arr = []) {
  for (let item of data) {
    arr.push(item);
    if (item[children] && item[children].length > 0) {
      getAll(item[children], children, arr);
    }
  }
  return arr;
};
export default {
  name: "BaseTreeSelect",
  model: {
    event: "change"
  },
  props: {
    props: {
      type: Object,
      default() {
        return {};
      }
    },
    data: {
      type: Array,
      default() {
        return [];
      }
    }
  },
  data() {
    return {
      options: [],
      filterText: ""
    };
  },
  computed: {
    multiple: function() {
      let type = Object.prototype.toString.call(this.$attrs.value).slice(8, -1);
      return type === "Array";
    },
    defaultProps: function() {
      return Object.assign(
        {},
        {
          checkStrictly: false,
          value: "id", // ID字段名
          label: "label", // 显示名称
          children: "children" // 子级字段名
        },
        this.props
      );
    }
  },
  watch: {
    data: function() {
      this.init();
    },
    filterText(val) {
      this.$refs.treeBox.filter(val);
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      if (this.multiple) {
        this.$refs.treeBox.setCheckedKeys(this.$attrs.value); // 设置默认选中
      } else {
        this.$refs.treeBox.setCurrentKey(this.$attrs.value);
      }
      this.options = getAll(this.data, this.defaultProps.children);
    },
    handerCheckChange() {
      if (this.multiple) {
        this.handerMultiple();
      } else {
        this.handerSingle();
      }
    },
    handerSingle() {
      let key = this.$refs.treeBox.getCurrentKey();
      this.$emit("change", key);
      this.$refs.select.blur();
    },
    handerMultiple() {
      let nodes = this.$refs.treeBox.getCheckedNodes();
      let keys = nodes.reduce((acc, item) => {
        //如果父子不关联则选中所有的，如果父子关联，则只选中叶子节点
        let bol = this.defaultProps.checkStrictly ? true : !item.children;
        return bol ? [...acc, item[this.defaultProps.value]] : acc;
      }, []);
      this.$emit("change", keys);
    },
    valueChange(v) {
      this.$refs.treeBox.setCheckedKeys(v);
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    }
  }
};
</script>
<style>
.hidden {
  display: none;
}
.search-input {
  margin: 5px 10px;
  width: calc(100% - 20px);
}
</style>
