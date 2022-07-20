import { defineStore } from 'pinia';
import { LoginFormData } from '@/types/api/system/login';
import { UserState } from '@/types/store/user';

import { localStorage } from '@/utils/storage';
import { login, logout } from '@/api/system/sys_login';
import { getUserPerm } from '@/api/system/user';
import { resetRouter } from '@/router';

const useUserStore = defineStore({
  id: 'user',
  state: (): UserState => ({
    userId: 0,
    openId: '',
    token: localStorage.get('token') || '',
    nickname: '',
    avatarUrl: '',
    roleNames: [],
    permissionTreeList: [],
  }),
  actions: {
    async RESET_STATE() {
      this.$reset();
    },
    /**
     * 登录
     */
    login(loginData: LoginFormData) {
      const { username, password, code, uuid } = loginData;
      return new Promise((resolve, reject) => {
        login({
          username: username.trim(),
          password: password,
          grant_type: 'captcha',
          code: code,
          uuid: uuid,
        }).then((response) => {
          const { tokenType, value } = response.data;
          const token = tokenType + ' ' + value;
          localStorage.set('token', token);
          this.token = token;
          resolve(token);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    /**
     *  获取用户信息（昵称、头像、角色集合、权限集合）
     */
    getUserInfo() {
      return new Promise((resolve, reject) => {
        getUserPerm().then(({ data }: any) => {
          if (!data) {
            return reject('Verification failed, please Login again.');
          }
          const { userId, openId, nickname, avatarUrl, roleNames, permissionTreeList } = data;
          this.userId = userId;
          this.openId = openId;
          this.nickname = nickname;
          this.avatarUrl = avatarUrl;
          this.roleNames = roleNames;
          this.permissionTreeList = permissionTreeList;
          resolve(data);
        }).catch((error: any) => {
          reject(error);
        });
      });
    },

    /**
     *  注销
     */
    logout() {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          localStorage.remove('token');
          this.RESET_STATE();
          resetRouter();
          resolve(null);
        }).catch((error) => {
          reject(error);
        });
      });
    },

    /**
     * 清除 Token
     */
    resetToken() {
      return new Promise((resolve) => {
        localStorage.remove('token');
        this.RESET_STATE();
        resolve(null);
      });
    },
  },
});

export default useUserStore;
