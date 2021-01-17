<template>
  <my-base-wraper>
    <base-header>
      <el-select v-model="listQuery.status" placeholder="请选择" @change="refreshTableData">
        <el-option v-for="item in statusList" :key="item.status" :label="item.display_name" :value="item.status" />
      </el-select>
      <el-input v-model="listQuery.content" style="width:200px" placeholder="请输入查询内容" clearable @clear="refreshTableData"></el-input>
      <el-button v-has="'query'" type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button v-has="'add'" type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTable" api="st_other_anonymity.listPage" :params="listQuery">
      <el-table-column label="ID" prop="id" align="center"></el-table-column>
      <el-table-column width="260px" label="内容" show-overflow-tooltip prop="content" align="left"></el-table-column>
      <el-table-column label="匿名用户名" prop="anonymousUserName" align="center"></el-table-column>
      <el-table-column label="创建时间" prop="createTime" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | dateTimeFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否处理" prop="statusName" align="center">
        <template slot-scope="scope">
          <span :style="scope.row.status === 0 ? 'background-color:red;' : 'background-color:#00FFFF;'">{{ scope.row.statusName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="匿名处理人" prop="anonymousHandlerName" align="center"></el-table-column>
      <el-table-column label="备注" show-overflow-tooltip prop="remark" align="center"></el-table-column>

      <el-table-column label="处理时间" prop="handleTime" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.handleTime | dateTimeFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="180px">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status === 0" type="text" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button type="text" @click="handleData(scope.row)">处理</el-button>
          <el-button type="text" @click="handleDetail(scope.row)">详情</el-button>
          <my-base-delete-btn v-if="scope.row.status === 0" @ok="handleDelete(scope.row)"></my-base-delete-btn>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog :visible.sync="dialogVisible" :title="titleMap[dialogStatus]" top="50px" width="50%">
      <el-form ref="dataForm" :model="form" :rules="rules" label-width="100px">
        <div v-if="dialogStatus === 'add' || dialogStatus === 'update'">
          <el-form-item label="内容:" prop="content">
            <el-input v-model="form.content" type="textarea" :rows="10"></el-input>
          </el-form-item>
          <el-form-item label="匿名用户名:" prop="anonymousUserName">
            <el-input v-model="form.anonymousUserName"></el-input>
          </el-form-item>
        </div>
        <div v-if="dialogStatus === 'detail'">
          <my-base-table-cell label-width="100px">
            <my-base-cell-item label="ID:">{{ form.id }}</my-base-cell-item>
            <my-base-cell-item label="内容:">{{ form.content }}</my-base-cell-item>
            <my-base-cell-item label="匿名用户名:">{{ form.anonymousUserName }}</my-base-cell-item>
            <my-base-cell-item label="是否处理:">{{ form.statusName }}</my-base-cell-item>

            <my-base-cell-item label="创建时间:">{{ form.createTime | dateTimeFilter }}</my-base-cell-item>
            <div v-if="form.status === 1">
              <my-base-cell-item label="匿名处理人:">{{ form.anonymousHandlerName }}</my-base-cell-item>
              <my-base-cell-item label="备注:">{{ form.remark }}</my-base-cell-item>
              <my-base-cell-item label="处理时间:">{{ form.handleTime | dateTimeFilter }}</my-base-cell-item>
            </div>
          </my-base-table-cell>
        </div>
        <div v-if="dialogStatus === 'handle'">
          <el-form-item label="内容:">
            <el-input v-model="form.content" type="textarea" :rows="10" disabled></el-input>
          </el-form-item>
          <el-form-item label="匿名处理人:">
            <el-input v-model="form.anonymousHandlerName"></el-input>
          </el-form-item>
          <el-form-item label="备注:">
            <el-input v-model="form.remark" type="textarea" :rows="10"></el-input>
          </el-form-item>
        </div>
      </el-form>
      <div v-if="dialogStatus !== 'detail'" slot="footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </base-dialog>
  </my-base-wraper>
</template>

<script>
export default {
  name: "StOtherAnonymity",
  data() {
    return {
      dialogVisible: false,
      statusList: [
        { status: undefined, display_name: "全部" },
        { status: 0, display_name: "未处理" },
        { status: 1, display_name: "已处理" }
      ],
      listQuery: {
        status: undefined, //状态：是否处理 （0:未处理  1:已处理）
        content: ""
      },
      form: {
        id: undefined, //主键ID
        content: undefined, //内容
        anonymousUserName: undefined, //匿名用户名
        anonymousHandlerName: undefined, //匿名处理人
        status: undefined, //状态：是否处理 （0:未处理  1:已处理）
        remark: undefined //备注
      },
      dialogStatus: "",
      titleMap: {
        add: "添加",
        update: "编辑",
        handle: "处理",
        detail: "详情"
      },
      rules: {}
    };
  },
  created() {},
  methods: {
    refreshTableData() {
      this.$refs.baseTable.refresh();
    },
    handleAdd() {
      this.form = Object.assign({}, {});
      this.dialogStatus = "add";
      this.dialogVisible = true;
    },
    handleUpdate(row) {
      this.form = Object.assign({}, row);
      this.dialogStatus = "update";
      this.dialogVisible = true;
    },
    handleDetail(row) {
      this.form = Object.assign({}, row);
      this.dialogStatus = "detail";
      this.dialogVisible = true;
    },
    handleData(row) {
      this.form = Object.assign({}, row);
      this.dialogStatus = "handle";
      this.dialogVisible = true;
    },
    async handleDelete(row) {
      let res = await this.$api.st_other_anonymity.delete(row.id);
      this.refreshTableData();
      this.submitOk(res.message);
    },
    submitForm() {
      this.$refs.dataForm.validate(async valid => {
        if (valid) {
          let res;
          if (this.dialogStatus === "handle") {
            res = await this.$api.st_other_anonymity.handle(this.form);
          } else {
            res = await this.$api.st_other_anonymity[this.form.id ? "update" : "add"](this.form);
          }
          this.refreshTableData();
          this.submitOk(res.message);
          this.dialogVisible = false;
        }
      });
    }
  }
};
</script>
<style scoped></style>
