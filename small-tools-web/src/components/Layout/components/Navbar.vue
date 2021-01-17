<template>
  <div class="navbar" :class="{ darkTheme: isDarkTheme }">
    <div>
      <hamburger
        id="hamburger-container"
        :is-active="sidebar.opened"
        class="hamburger-container"
        @toggleClick="toggleSideBar"
      />
    </div>
    <div class="project-name">
      <span>Small Tools</span>
    </div>
    <div class="right-menu">
      <el-popover placement="bottom-end" width="100" trigger="hover">
        <ul class="theme-bar">
          <el-radio-group v-model="currentTheme" @change="changeTheme">
            <li>
              <el-radio :label="false">默认主题</el-radio>
            </li>
            <li>
              <el-radio :label="true">暗色主题</el-radio>
            </li>
          </el-radio-group>
        </ul>
        <span slot="reference">
          <i v-if="isDarkTheme" class="icon-theme default" />
          <i v-else class="icon-theme dark" />
        </span>
      </el-popover>

      <el-dropdown
        class="avatar-container right-menu-item hover-effect"
        trigger="click"
      >
        <div class="avatar-wrapper">
          <el-avatar v-if="avatarUrl" :src="avatarUrl" :size="40" />
          <el-avatar v-else :src="defaultAvatar" :size="40" />
          <span style="float:right;margin-top:-10px;">{{ nickname }} </span>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/">
            <el-dropdown-item>首 页</el-dropdown-item>
          </router-link>
          <router-link to="/system/personal-center">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="changePsw"
            >修改密码</el-dropdown-item
          >
          <a target="_blank" href="https://gitee.com/zhengqingya">
            <el-dropdown-item>Gitee</el-dropdown-item>
          </a>
          <a target="_blank" href="https://github.com/zhengqingya">
            <el-dropdown-item>GitHub</el-dropdown-item>
          </a>
          <router-link to="/other/anonymity">
            <el-dropdown-item>提建议</el-dropdown-item>
          </router-link>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">退出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <change-psw ref="changePsw" />
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import ChangePsw from "@/components/ChangePsw";
import defaultAvatar from "@/assets/images/timg.jpg";
import Hamburger from "@/components/Hamburger";

export default {
  components: { ChangePsw, Hamburger },
  data() {
    return {
      currentTheme: false,
      defaultAvatar: defaultAvatar
    };
  },
  computed: {
    ...mapGetters(["sidebar", "alarmCount", "isDarkTheme"]),
    userName() {
      return this.$store.state.user.username;
    },
    nickname() {
      const name = this.$store.state.user.nickname;
      if (name === undefined || name === "") {
        return "无名氏";
      } else {
        return name;
      }
    },
    avatarUrl() {
      return this.$store.state.user.avatar || "@/assets/images/timg.jpg";
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.currentTheme = this.isDarkTheme;
    });
    // this.$store.dispatch('app/getUserInfo')
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch("app/toggleSideBar");
    },
    changeTheme() {
      this.$store.dispatch("settings/changeTheme");
    },
    changePsw() {
      this.$refs.changePsw.open();
    },
    async logout() {
      await this.$store.dispatch("user/logout");
      this.$router.push(`/login?redirect=${this.$route.fullPath}`);
    }
  }
};
</script>

<style lang="scss" scoped>
.icon-theme {
  height: 25px;
  width: 25px;

  &.default {
    background: url("../../../assets/images/light-skin.png") no-repeat;
  }

  &.dark {
    background: url("../../../assets/images/dark-skin.png") no-repeat;
  }
}

.theme-bar {
  li {
    margin: 5px 5px;
  }
}

.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .project-name {
    height: 50px;
    line-height: 50px;
    float: left;
    padding-left: 15px;

    span {
      vertical-align: middle;
      font-size: 22px;
      color: #021b32;
      font-weight: bold;
    }
  }

  .right-menu {
    position: absolute;
    // float: right;
    right: 10px;
    height: 100%;
    line-height: 50px;
    margin-right: 40px;

    &:focus {
      outline: none;
    }

    .avatar-container {
      margin-left: 10px;
      .avatar-wrapper {
        height: 20%;
        margin-left: 10px;
        margin-top: 5px;
        position: relative;
        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: 8px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}

.darkTheme {
  background: $dark_main_color;

  .project-name {
    span {
      color: #fff;
    }
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .avatar-container {
      margin-left: 10px;
      .avatar-wrapper {
        margin-left: 10px;
        margin-top: 5px;
        position: relative;

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: 8px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
