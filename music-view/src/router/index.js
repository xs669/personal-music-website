import Vue from "vue";
import VueRouter from "vue-router";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import getPageTitle from "@/utils/get-page-title";
import Home from "@/pages/Home";
import MyMusic from "../pages/MyMusic";
import Singer from "../pages/Singer";
import SongList from "../pages/SongList";
import Search from "../pages/Search";
import Lyric from "../pages/Lyric";
import Login from "../pages/Login";
import Register from "../pages/Register";
import Setting from "../pages/Setting";
import SingerAblum from "../pages/SingerAlbum";
import SongListAblum from "../pages/SongListAlbum";
import LeaderBoard from "../pages/LeaderBoard";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: Home,
  },
  {
    path: "/myMusic",
    name: "myMusic",
    component: MyMusic,
    meta: {
      title: '我的音乐',
      requireAuth: true,
    },
  },
  {
    path: "/singer",
    name: "singer",
    component: Singer,
    meta: {
      title: '歌手',
    },
  },
  {
    path: "/songList",
    name: "songList",
    component: SongList,
    meta: {
      title: '歌单',
    },
  },
  {
    path: "/search",
    name: "search",
    component: Search,
    meta: {
      title: '搜索',
    },
  },
  {
    path: "/lyric",
    name: "lyric",
    component: Lyric,
    meta: {
      title: '歌词',
    },
  },
  {
    path: "/login",
    name: "login",
    component: Login,
    meta: {
      title: '登录',
    },
  },
  {
    path: "/register",
    name: "register",
    component: Register,
    meta: {
      title: '注册',
    },
  },
  {
    path: "/setting",
    name: "setting",
    component: Setting,
    meta: {
      title: '个人中心',
      requireAuth: true,
    },
  },
  {
    path: "/singerAlbum",
    name: "singerAlbum",
    component: SingerAblum,
    meta: {
      title: '歌手详情',
    },
  },
  {
    path: "/songListAlbum",
    name: "songListAlbum",
    component: SongListAblum,
    meta: {
      title: '歌单详情',
    },
  },
  {
    path: "/leaderBoard",
    name: "leaderBoard",
    component: LeaderBoard,
    meta: {
      title: '排行榜',
    },
  },
];

const router = new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes,
});

NProgress.configure({ showSpinner: false });

const whiteList = ["/login"];

router.beforeEach(async (to, from, next) => {
  NProgress.start();
  document.title = getPageTitle(to.meta.title);
  if (to.meta.requireAuth) {
    let username = localStorage.getItem("consumerName");
    if (username) {
      if (to.path === "/login") {
        next({ path: "/" });
        NProgress.done();
      } else {
        next();
      }
    } else {
      if (whiteList.indexOf(to.path) !== -1) {
        next();
      } else {
        next({ path: "/login" });
        NProgress.done();
      }
    }
  } else {
    next();
  }
});

router.afterEach(() => {
  NProgress.done();
});

export default router;
