<template>
  <div class="app-container">
    <el-row style="height:100%;" :gutter="15">
      <el-col :span="6" style="height:100%;">
        <my-base-wraper full-height>
          <my-base-title-card title="字典类型">
            <el-button
              slot="append"
              v-has="'add'"
              type="primary"
              @click="addDictType"
              >添加</el-button
            >
            <el-tree
              :props="defaultProps"
              :data="dictionaryTree"
              highlight-current
              @node-click="handleNodeClick"
            />
          </my-base-title-card>
        </my-base-wraper>
      </el-col>
      <el-col :span="18">
        <my-base-wraper>
          <my-base-title-card title="字典类型信息">
            <el-button
              v-if="dictTypeData.name"
              slot="append"
              v-has="'edit'"
              type="primary"
              @click="updateDictType(dictTypeData)"
              >编辑</el-button
            >
            <el-button
              v-if="dictTypeData.name"
              slot="append"
              v-has="'delete'"
              type="danger"
              @click="deleteDictType"
              >删除</el-button
            >
            <my-base-table-cell>
              <my-base-cell-item label="字典类型名称">{{
                dictTypeData.name
              }}</my-base-cell-item>
              <my-base-cell-item label="字典类型编码">{{
                dictTypeData.code
              }}</my-base-cell-item>
              <my-base-cell-item
                v-if="dictTypeData.status == 1"
                label="字典类型状态"
                >启用</my-base-cell-item
              >
              <my-base-cell-item
                v-if="dictTypeData.status == 0"
                label="字典类型状态"
                >停用</my-base-cell-item
              >
            </my-base-table-cell>
          </my-base-title-card>
          <hr />
          <my-base-title-card style="margin-top: 10px;" title="字典列表">
            <el-button
              v-if="isShowAddDictButton"
              slot="append"
              v-has="'add'"
              type="primary"
              @click="addDict"
              >添加</el-button
            >
            <el-table
              v-loading.body="listLoading"
              :data="dicList"
              border
              :height="calcTableHeight"
            >
              <el-table-column prop="id" label="ID" />
              <el-table-column prop="name" label="字典名称" />
              <el-table-column prop="value" label="字典值" />
              <el-table-column prop="sort" label="展示排序" />
              <el-table-column label="操作" align="center" width="150">
                <template slot-scope="scope">
                  <el-button type="text" @click="updateDict(scope.row)"
                    >编辑</el-button
                  >
                  <my-base-delete-btn @ok="deleteDict(scope.row)" />
                </template>
              </el-table-column>
            </el-table>
          </my-base-title-card>
        </my-base-wraper>
      </el-col>
    </el-row>
    <edit-dict ref="editDict" @saveSucc="getDicList(dictTypeData)" />
    <edit-dict-type ref="editDictType" @saveSucc="getDictTree()" />
  </div>
</template>
<script>
import editDict from "./edit-dict";
import editDictType from "./edit-dict-type";
export default {
  name: "Dict",
  components: { editDict, editDictType },
  data() {
    return {
      isShowAddDictButton: false,
      dictionaryTree: [],
      defaultProps: {
        children: "children",
        label: "name"
      },
      dicList: [], // 字典列表
      dictTypeData: {},
      listLoading: false
    };
  },
  computed: {
    calcTableHeight() {
      const winHeight = document.documentElement.clientHeight;
      return Math.abs(winHeight - 360);
    }
  },
  mounted() {
    this.getDictTree();
  },
  methods: {
    async getDictTree() {
      let res = await this.$api.sys_dict_type.list();
      this.dictionaryTree = res.data;
      this.dictTypeData = {};
      this.dicList = [];
      this.isShowAddDictButton = false;
    },
    handleNodeClick(data) {
      this.dictTypeData = data;
      this.getDicList(this.dictTypeData);
      this.isShowAddDictButton = true;
    },
    async getDicList(data) {
      let res = await this.$api.sys_dict.listByCode(data.code);
      this.dicList = res.data;
    },
    addDict() {
      if (!this.dictTypeData.name) {
        this.$message.warning("请先选中要添加的字典类型");
        return;
      }
      var maxSort = 1;
      if (this.dicList.length > 0) {
        maxSort = Math.max(...this.dicList.map(e => e.sort)) + 1;
      }
      this.$refs.editDict.open("create", null, this.dictTypeData.id, maxSort);
    },
    updateDict(row) {
      this.$refs.editDict.open("update", row);
    },
    async deleteDict({ id }) {
      let res = await this.$api.sys_dict.delete(id);
      this.submitOk(res.msg);
      this.getDicList(this.dictTypeData);
    },
    // 下：数据字典类型操作 ======================
    addDictType() {
      this.$refs.editDictType.open("create");
    },
    updateDictType(row) {
      this.$refs.editDictType.open("update", row);
    },
    deleteDictType() {
      if (!this.dictTypeData.name) {
        this.$message.warning("请先选中要删除的字典类型");
        return;
      }
      this.$confirm("确定删除数据字典?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(async () => {
        let res = await this.$api.sys_dict_type.delete(this.dictTypeData.id);
        this.submitOk(res.msg);
        this.getDictTree();
      });
    }
  }
};
</script>
<style scoped></style>
