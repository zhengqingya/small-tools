<template>
  <base-dialog
    :visible.sync="dialogRoleVisible"
    title="修改用户角色"
    width="700px"
  >
    <el-form ref="userForm" :model="userForm" label-width="100px">
      <my-base-table-cell>
        <my-base-cell-item label="用户名称：">{{
          userForm.nickname
        }}</my-base-cell-item>
        <my-base-cell-item label="用户账号：">{{
          userForm.username
        }}</my-base-cell-item>
      </my-base-table-cell>
    </el-form>
    <div style="margin-left: 0px; margin-top: 10px">
      <el-transfer
        v-model="userForm.roleIds"
        :data="roleList"
        :titles="['待选列表', '已选列表']"
        :props="{
          key: 'roleId',
          label: 'name',
        }"
      />
    </div>
    <div slot="footer">
      <el-button @click="dialogRoleVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitRoleForm">确 定</el-button>
    </div>
  </base-dialog>
</template>
<script>
export default {
  name: 'RolePermission',
  data() {
    return {
      dialogRoleVisible: false,
      roleList: [], // 角色列表
      userForm: {},
    }
  },
  methods: {
    open(data) {
      this.getRoleList()
      if (data.roleIds) {
        data.roleIds = '[' + data.roleIds + ']'
      }
      const computedRoleIds = data.roleIds ? JSON.parse(data.roleIds) : []
      this.userForm = Object.assign({}, data, { roleIds: computedRoleIds })
      this.dialogRoleVisible = true
    },
    async getRoleList() {
      let res = await this.$api.sys_role.list({ status: 1 })
      this.roleList = res.data
    },
    async submitRoleForm() {
      // 把数组项拼接成字符串，并以分号隔开
      // this.userForm.roleIds = this.userForm.roleIds.join(",");
      this.userForm.roleIdList = this.userForm.roleIds
      let res = await this.$api.sys_user.saveRoleIds(this.userForm)
      this.$emit('saveSucc')
      this.dialogRoleVisible = false
      this.submitOk(res.msg)
    },
  },
}
</script>
<style lang="scss" scoped></style>
