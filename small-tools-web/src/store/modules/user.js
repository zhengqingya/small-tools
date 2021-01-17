import api_sys_user from "@/api/modules/sys_user";
import { getToken, setToken, removeToken, removeAll } from "@/utils/auth";
import { resetRouter } from "@/router";
import { encryptByDES } from "@/utils";

const state = {
  token: getToken(),
  username: "",
  nickname: "",
  avatar: "",
  userId: "",
  roles: [],
  DES_KEY: "deskeyzq"
};

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_NAME: (state, username) => {
    state.username = username;
  },
  SET_NICKNAME: (state, nickname) => {
    state.nickname = nickname;
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar;
  },
  SET_USER_ID: (state, id) => {
    state.userId = id;
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles;
  }
};

const actions = {
  login({ commit, state }, userInfo) {
    const { username, password, token, ifThirdpartOauth } = userInfo;
    return new Promise(async (resolve, reject) => {
      if (ifThirdpartOauth === "true") {
        commit("SET_TOKEN", token);
        setToken(token);
        resolve();
      } else {
        await api_sys_user
          .login(username.trim(), encryptByDES(password, state.DES_KEY))
          .then(res => {
            const { data } = res;
            commit("SET_TOKEN", data.token);
            setToken(data.token);
            resolve();
          })
          .catch(error => {
            reject(error);
          });
      }
    });
  },

  getInfo({ commit, state, dispatch }) {
    return new Promise(async (resolve, reject) => {
      let res = await api_sys_user.getInfo(state.token);
      const { data } = res;
      if (!data) {
        reject("Verification failed, please Login again.");
      }
      const { roleNames, username, nickname, userId, avatar } = data;
      commit("SET_ROLES", roleNames);
      commit("SET_NAME", username);
      commit("SET_USER_ID", userId);
      commit("SET_AVATAR", avatar);
      commit("SET_NICKNAME", nickname);
      resolve(data);
    });
  },

  logout({ commit, state }) {
    return new Promise(async (resolve, reject) => {
      await api_sys_user.logout();
      commit("SET_TOKEN", "");
      commit("SET_ROLES", "");
      removeToken();
      removeAll();
      resetRouter();
      resolve();
    });
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit("SET_TOKEN", "");
      commit("SET_ROLES", []);
      removeToken();
      resolve();
    });
  }
};
export default {
  namespaced: true,
  state,
  mutations,
  actions
};
