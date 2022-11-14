<template>
  <div class="header">
    <div class="collapse-btn" @click="collapseChange">
      <i class="el-icon-menu"></i>
    </div>
    <div class="logo">music后台管理</div>
    <div class="header-right">
      <div
        class="btn-fullscreen"
        @click="handleFullScreen"
        style="cursor: pointer"
      >
        <el-tooltip
          :content="fullscreen ? '取消全屏' : '全屏'"
          placement="bottom"
        >
          <i class="el-icon-rank"></i>
        </el-tooltip>
      </div>
      <div class="user-avator">
        <img src="http://localhost/avatar/1.jpeg" alt="头像" />
      </div>
      <el-dropdown class="user-name" trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          {{ userName }}
          <i class="el-icon-caret-bottom"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import bus from "../assets/js/bus";
import { logout } from "@/api/login";

export default {
  name: "TheHeader",
  data() {
    return {
      collapse: false,
      fullscreen: false,
    };
  },
  computed: {
    userName() {
      return localStorage.getItem("username");
    },
  },
  methods: {
    handleCommand(command) {
      if (command === "logout") {
        logout().then((res) => {
          if (res.code === 200) {
            localStorage.removeItem("token");
            localStorage.removeItem("username");
            this.$message.success(res.msg);
            this.$router.push({ path: "/" });
          }
        });
      }
    },
    collapseChange() {
      // 侧边栏折叠
      this.collapse = !this.collapse;
      bus.$emit("collapse", this.collapse);
    },
    handleFullScreen() {
      // 全屏事件
      // 浏览器适配
      if (this.fullscreen) {
        if (document.exitFullscreen()) {
          document.exitFullscreen();
        } else if (document.webkitCancleFullScreen) {
          document.webkitCancleFullScreen();
        } else if (document.mozCancleFullScreen) {
          document.mozCancleFullScreen();
        } else if (document.msExitFullScreen) {
          document.msExitFullScreen();
        }
      } else {
        let element = document.documentElement;
        if (element.requestFullscree) {
          element.requestFullscreen();
        } else if (element.webkitRequestFullScreen) {
          element.webkitRequestFullScreen();
        } else if (element.mozRequestFullScreen) {
          element.mozRequestFullScreen();
        } else if (element.msRequestFullScreen) {
          element.msRequestFullScreen();
        }
      }
      this.fullscreen = !this.fullscreen;
    },
  },
};
</script>

<style scoped>
.header {
  position: relative;
  background-color: #253041;
  box-sizing: border-box;
  width: 100%;
  height: 70px;
  font-size: 22px;
  color: white;
}

.collapse-btn {
  float: left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 70px;
}

.header .logo {
  float: left;
  line-height: 70px;
}

.header-right {
  float: right;
  padding-right: 50px;
  display: flex;
  height: 70px;
  align-items: center;
}

.btn-fullscreen {
  margin-right: 5px;
  font-size: 24px;
  transform: rotate(45deg);
}

.user-avator {
  margin-left: 20px;
}

.user-avator img {
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50px;
}

.user-name {
  margin-left: 10px;
}

.el-dropdown-link {
  color: #fff;
  cursor: pointer;
}
</style>
