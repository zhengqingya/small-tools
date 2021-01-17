import defaultSettings from "@/settings";
import Cookies from "js-cookie";
const { showSettings, fixedHeader, sidebarLogo } = defaultSettings;
const state = {
  showSettings: showSettings,
  fixedHeader: fixedHeader,
  sidebarLogo: sidebarLogo,
  darkTheme: Cookies.get("dark_theme") ? !!+Cookies.get("dark_theme") : false
};

const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    if (state.hasOwnProperty(key)) {
      state[key] = value;
    }
  },
  CHANGE_THEME: state => {
    state.darkTheme = !state.darkTheme;
    if (state.darkTheme) {
      Cookies.set("dark_theme", 1);
    } else {
      Cookies.set("dark_theme", 0);
    }
  }
};

const actions = {
  changeSetting({ commit }, data) {
    commit("CHANGE_SETTING", data);
  },
  changeTheme({ commit }, data) {
    commit("CHANGE_THEME");
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions
};
