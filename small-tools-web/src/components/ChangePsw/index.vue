<template>
  <el-dialog
    :visible.sync="dialogVisible"
    :modal-append-to-body="false"
    :close-on-click-modal="false"
    title="修改密码"
    width="35%"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="输入当前密码:" prop="password">
        <el-input v-model="form.password" :type="passw">
          <svg-icon slot="suffix" :icon-class="icon" @click.native="showPass" />
        </el-input>
      </el-form-item>
      <el-form-item label="输入新密码:" prop="newPassword">
        <el-input
          v-model="form.newPassword"
          :type="passw1"
          placeholder="密码为6-16位字母或数字"
        >
          <svg-icon
            slot="suffix"
            :icon-class="icon1"
            @click.native="showPass1"
          />
        </el-input>
      </el-form-item>
      <el-form-item label="确认新密码:" prop="newPasswordAgain">
        <el-input
          v-model="form.newPasswordAgain"
          :type="passw1"
          placeholder="密码为6-16位字母或数字"
        >
          <svg-icon
            slot="suffix"
            :icon-class="icon1"
            @click.native="showPass1"
          />
        </el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" :loading="loading" @click="submitForm"
        >确 定</el-button
      >
    </span>
  </el-dialog>
</template>
<script>
import SvgIcon from '@/components/SvgIcon'
import { encryptByDES } from '@/utils'
export default {
  name: 'ChangePsw',
  components: { SvgIcon },
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.form.newPasswordAgain !== '') {
          this.$refs.form.validateField('newPasswordAgain')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.form.newPassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    var rulePass = (rule, value, callback) => {
      var patt = /^(\w){6,16}$/
      if (!patt.test(value)) {
        callback(new Error('密码为6-16位字母或数字'))
      }
      callback()
    }
    return {
      passw: 'password',
      passw1: 'password',
      icon: 'bi',
      icon1: 'bi',
      rules: {
        newPassword: [
          { validator: validatePass, trigger: 'blur' },
          { validator: rulePass, trigger: 'blur' },
        ],
        newPasswordAgain: [
          { validator: validatePass2, trigger: 'blur' },
          { validator: rulePass, trigger: 'blur' },
        ],
      },
      dialogVisible: false,
      userId: this.$store.state.user.userId,
      loading: false,
      form: {
        userId: this.$store.state.user.userId,
        password: undefined,
        newPassword: undefined,
        newPasswordAgain: undefined,
      },
    }
  },
  methods: {
    open() {
      this.dialogVisible = true
    },
    // 密码的隐藏和显示
    showPass() {
      // 点击图标是密码隐藏或显示
      if (this.passw === 'text') {
        this.passw = 'password'
        // 更换图标
        this.icon = 'bi'
      } else {
        this.passw = 'text'
        this.icon = 'kai'
      }
    },
    showPass1() {
      // 点击图标是密码隐藏或显示
      if (this.passw1 === 'text') {
        this.passw1 = 'password'
        // 更换图标
        this.icon1 = 'bi'
      } else {
        this.passw1 = 'text'
        this.icon1 = 'kai'
      }
    },
    submitForm() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          this.loading = true
          const key = this.$store.state.user.DES_KEY
          const oldPsw = encryptByDES(this.form.password, key)
          const newPassword = encryptByDES(this.form.newPassword, key)
          const userId = this.$store.state.user.userId
          let res = await this.$api.sys_user.updatePassword({
            password: oldPsw,
            newPassword: newPassword,
            userId: userId,
          })
          this.loading = false
          this.dialogVisible = false
          this.submitOk(res.msg, () => {
            this.logout()
          })
        } else {
          console.log('验证错误')
        }
      })
    },
    logout() {
      this.$store.dispatch('user/logout').then((res) => {
        this.$router.push({ path: '/login' })
      })
    },
  },
}
</script>
<style lang="scss" scoped></style>
