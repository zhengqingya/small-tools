<!--
 * @Description: 
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-01-10 11:42:31
 * @LastEditors: yanxin
 * @LastEditTime: 2020-04-07 10:54:34
 -->
<template>
  <el-form
    ref="form"
    :model="form"
    v-bind="$attrs"
    class="form"
    v-on="$listeners"
  >
    <template v-for="(item, index) in columns">
      <slot v-if="item.type == 'custom'" :name="item.key">{{ item.key }}</slot>
      <el-form-item
        v-else
        :key="index"
        class="form-item"
        :label="item.label"
        :prop="item.key"
        :rules="item.rules"
        :style="{ width: item.width }"
      >
        <component
          :is="item.type"
          v-model="form[item.key]"
          v-bind="item.attr"
          :style="{
            width: item.attr && item.attr.width ? item.attr.width : '100%'
          }"
        ></component>
      </el-form-item>
    </template>

    <slot></slot>
  </el-form>
</template>
<script>
export default {
  name: "BaseForm",
  model: {
    prop: "value",
    event: "form-change"
  },
  props: {
    columns: {
      type: Array,
      default() {
        return [];
      }
    },
    value: {
      type: Object,
      default() {
        return {};
      }
    }
  },
  data() {
    return {
      form: {}
    };
  },
  watch: {
    form: function(val) {
      this.$emit("form-change", val);
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.form = this.value;
    },
    validate() {
      return new Promise(resolve => {
        this.$refs["form"].validate(valid => {
          if (valid) {
            resolve(true);
          }
        });
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.form {
  display: flex;
  flex-wrap: wrap;
}
.form-item {
  width: 100%;
}
</style>
