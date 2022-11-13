<template>
  <div class="the-header">
    <div class="header-logo" @click="goHome">
      <svg class="icon" style="color: dodgerblue">
        <use xlink:href="#icon-yinle"></use>
      </svg>
      <span style="color: dodgerblue">music</span>
    </div>
    <ul class="navbar">
      <li
        :class="{ active: item.path === activeName }"
        v-for="item in navMsg"
        :key="item.name"
        @click="goPage(item.path)"
      >
        {{ item.name }}
      </li>
      <li>
        <div class="header-search">
          <input
            type="text"
            placeholder="搜索歌曲或歌单"
            v-model="keyWords"
            @keyup.enter="goSearch"
          />
          <div class="search-btn" @click="goSearch">
            <svg class="icon">
              <use xlink:href="#icon-sousuo"></use>
            </svg>
          </div>
        </div>
      </li>
      <li
        v-show="!loginIn"
        ref="List"
        :class="{ active: item.path === activeName }"
        v-for="item in loginMsg"
        :key="item.name"
        @click="goPage(item.path, item.name)"
      >
        {{ item.name }}
      </li>
    </ul>
    <div ref="Avatar" class="header-right" v-show="loginIn">
      <div id="user" @mouseenter="showMenuList">
        <img :src="avatar" alt="用户头像" />
      </div>
      <ul class="menu" v-show="showMenu">
        <li
          v-for="(item, index) in menuList"
          :key="index"
          @click="goMenuList(item.path)"
        >
          {{ item.name }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import navMsg from "../assets/data/header";
import loginMsg from "../assets/data/user";
import menuList from "../assets/data/consumerList";
import { mapGetters } from "vuex";
import { getAllSongByName } from "@/api/Song";
import { getSongListByName } from "@/api/SongList";

export default {
  name: "the-header",
  data() {
    return {
      navMsg: [],
      loginMsg: [],
      keyWords: "",
      navPath: [
        "/",
        "/songList",
        "/singer",
        "/myMusic",
        "/login",
        "/register",
        "/leaderBoard",
      ],
      routeValue: "",
      menuList: [],
      showMenu: false,
    };
  },
  created() {
    this.navMsg = navMsg;
    this.loginMsg = loginMsg;
    this.menuList = menuList;
    this.$store.commit("setLoginIn", this.loginIn);
  },
  computed: {
    ...mapGetters(["activeName", "loginIn", "avatar"]),
  },
  watch: {
    $route(val) {
      this.routeValue = val;
      if (!this.navPath.includes(val.path)) {
        this.$store.commit("setActiveName", false);
      } else {
        this.$store.commit("setActiveName", val.path);
      }
    },
  },
  methods: {
    getSearchData(val) {
      this.$router.push({ path: "/search", query: { keyWord: val } });
      getAllSongByName(val).then((res) => {
        if (res.data.code === 200) {
          this.$store.commit("setListOfSongs", res.data.data);
        } else {
          this.$store.commit("setListOfSongs", []);
        }
      });
      getSongListByName(val).then((res) => {
        if (res.data.code === 200) {
          this.$store.commit("setSearchSongListData", res.data.data);
        } else {
          this.$store.commit("setSearchSongListData", []);
        }
      });
    },
    goSearch() {
      this.getSearchData(this.keyWords);
    },
    goHome() {
      this.$router.push({ path: "/" });
      this.$store.commit("setActiveName", "/");
    },
    goPage(path) {
      if (path === "/myMusic") {
        if (localStorage.getItem("consumerName")) {
          this.$store.commit("setActiveName", path);
          this.$router.push({ path: path });
        } else {
          this.$message.warning("未登录, 不能访问!");
        }
      } else {
        this.$store.commit("setActiveName", path);
        this.$router.push({ path: path });
      }
    },
    goMenuList(path) {
      if (path === 0) {
        localStorage.removeItem("consumerName");
        this.$store.commit("setLoginIn", !this.loginIn);
        this.$store.commit("setUserId", "");
        this.$store.commit("setUserName", "");
        this.$store.commit("setAvatar", "");
        this.$router.push({ path: "/" });
      } else {
        this.$router.push({ path: path });
      }
    },
    showMenuList() {
      this.showMenu = !this.showMenu;
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../assets/css/the-header.scss";
</style>
