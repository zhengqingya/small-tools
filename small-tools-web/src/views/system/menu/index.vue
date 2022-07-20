<template>
  <div class="app-container">
    <el-row :gutter="15" style="height: 100%">
      <el-col :span="6" style="height: 100%">
        <base-wraper>
          <base-title-card title="菜单" style="padding-top: 0">
            <el-button slot="append" type="warning" @click="handleAddMenu">新增一级菜单</el-button>
            <!--菜单树-->
            <el-tree :data="treeData" :props="defaultProps" @node-click="handleNodeClick" />
          </base-title-card>
        </base-wraper>
      </el-col>
      <el-col :span="18" style="height: 100%">
        <base-wraper>
          <base-title-card title="菜单详情">
            <div v-if="currentClickMenu" slot="append">
              <el-button type="danger" @click="handleDelete">删除</el-button>
              <el-button type="primary" @click="handleEdit">编辑</el-button>
              <el-button type="primary" @click="addNextMenu">添加下级菜单</el-button>
            </div>
            <base-table-cell v-if="currentClickMenu" label-width="130px">
              <base-cell-item label="菜单编号">{{
                  currentClickMenu.menuId
              }}</base-cell-item>
              <base-cell-item label="菜单名称">{{
                  currentClickMenu.title
              }}</base-cell-item>
              <base-cell-item label="菜单链接">{{
                  currentClickMenu.path
              }}</base-cell-item>
              <base-cell-item label="重定向链接">{{
                  currentClickMenu.redirect
              }}</base-cell-item>
              <base-cell-item label="组件名">{{
                  currentClickMenu.component
              }}</base-cell-item>
              <base-cell-item label="菜单图标"><i :class="currentClickMenu.icon" />
                {{ currentClickMenu.icon }}</base-cell-item>
              <base-cell-item label="是否隐藏面包屑">{{
                  currentClickMenu.breadcrumb ? '显示' : '隐藏'
              }}</base-cell-item>
              <base-cell-item label="菜单状态">{{
                  currentClickMenu.status ? '启用' : '禁用'
              }}</base-cell-item>
              <base-cell-item label="显示顺序">{{
                  currentClickMenu.displayOrder
              }}</base-cell-item>
              <base-cell-item label="是否隐藏">{{
                  currentClickMenu.hidden ? '是' : '否'
              }}</base-cell-item>
              <base-cell-item label="是否一直显示">{{
                  currentClickMenu.alwaysShow ? '是' : '否'
              }}</base-cell-item>
              <base-cell-item label="上级菜单">{{
                  currentClickMenu.parentName
              }}</base-cell-item>
            </base-table-cell>
            <base-no-data v-else>请先选中左侧菜单</base-no-data>
          </base-title-card>
          <base-title-card v-if="hasEditBtnPermission" title="页面按钮" style="margin-top: 10px">
            <el-button slot="append" type="primary" @click="savePageBtn">保存权限按钮</el-button>
            <el-checkbox-group v-model="permission_buttons">
              <el-checkbox v-for="(item, index) in permission_buttons_list" :key="index" :label="item.id">{{ item.name
              }}</el-checkbox>
            </el-checkbox-group>
          </base-title-card>
        </base-wraper>
      </el-col>
    </el-row>
    <modifiy-menu ref="modifiyMenu" @handleSucc="getMenuTree" />
  </div>
</template>
<script>
import ModifiyMenu from './modifiyMenu.vue';
export default {
  name: 'Menu',
  components: { ModifiyMenu },
  data() {
    return {
      currentClickMenu: null,
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'title',
      },
      permission_buttons: [],
      permission_buttons_list: [],
    };
  },
  computed: {
    hasEditBtnPermission() {
      // 是否有编辑按钮的权限
      const hasChildren =
        this.currentClickMenu && this.currentClickMenu.children.length === 0;
      return hasChildren;
    },
  },
  mounted() {
    this.getMenuTree();
    this.getPermissonBtns();
  },
  methods: {
    async getMenuTree() {
      let res = await this.$api.sys_menu.menuTree();
      this.treeData = res.data;
      this.permission_buttons = [];
      this.currentClickMenu = null;
    },
    handleAddMenu() {
      this.$refs.modifiyMenu.open(1);
    },
    handleNodeClick(data) {
      this.currentClickMenu = data;
      if (this.hasEditBtnPermission) {
        this.getCurrentPageBtns(data.menuId);
      }
    },
    addNextMenu() {
      if (!this.currentClickMenu) {
        this.$message.error('请先选中你需要添加下级菜单的父级');
        return;
      }
      this.$refs.modifiyMenu.open(2, this.currentClickMenu);
    },
    handleEdit() {
      if (!this.currentClickMenu) {
        this.$message.error('请先选中你需要添加下级菜单的父级');
        return;
      }
      this.$refs.modifiyMenu.open(3, this.currentClickMenu);
    },
    async handleDelete() {
      if (!this.currentClickMenu) {
        this.$message.error('请先选中你需要删除的菜单');
        return;
      }
      let res = await this.$api.sys_menu.delete(this.currentClickMenu.menuId);
      this.submitOk(res.msg);
      this.getMenuTree();
    },
    async getPermissonBtns() {
      let res = await this.$api.sys_dict.listFromCacheByCode('permission_btn');
      this.permission_buttons_list = res.data;
    },
    async getCurrentPageBtns(menuId) {
      // 获取当前页面已经配置的按钮权限，用于回显
      let res = await this.$api.sys_menu.getBtnIdsByMenuId(menuId);
      this.permission_buttons = res.data;
    },
    async savePageBtn() {
      // 配置页面中的按钮，带权限
      const submitInfo = {
        btnIdList: this.permission_buttons,
        menuId: this.currentClickMenu.menuId,
      };
      let res = await this.$api.sys_menu.saveMenuBtnIds(submitInfo);
      this.submitOk(res.msg);
    },
  },
};
</script>
<style lang="scss" scoped>
</style>
