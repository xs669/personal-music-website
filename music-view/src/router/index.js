import Vue from "vue";
import VueRouter from "vue-router";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import getPageTitle from "@/utils/get-page-title";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: () => import('@/pages/Home'),
  },
  {
    path: "/myMusic",
    name: "myMusic",
    component: () => import('@/pages/MyMusic'),
    meta: {
      title: '我的音乐',
      requireAuth: true,
    },
  },
  {
    path: "/singer",
    name: "singer",
    component: () => import('@/pages/Singer'),
    meta: {
      title: '歌手',
    },
  },
  {
    path: "/songList",
    name: "songList",
    component: () => import('@/pages/SongList'),
    meta: {
      title: '歌单',
    },
  },
  {
    path: "/search",
    name: "search",
    component: () => import('@/pages/Search'),
    meta: {
      title: '搜索',
    },
  },
  {
    path: "/lyric",
    name: "lyric",
    component: () => import('@/pages/Lyric'),
    meta: {
      title: '歌词',
    },
  },
  {
    path: "/login",
    name: "login",
    component: () => import('@/pages/Login'),
    meta: {
      title: '登录',
    },
  },
  {
    path: "/register",
    name: "register",
    component: () => import('@/pages/Register'),
    meta: {
      title: '注册',
    },
  },
  {
    path: "/setting",
    name: "setting",
    component: () => import('@/pages/Setting'),
    meta: {
      title: '个人中心',
      requireAuth: true,
    },
  },
  {
    path: "/singerAlbum",
    name: "singerAlbum",
    component: () => import('@/pages/SingerAlbum'),
    meta: {
      title: '歌手详情',
    },
  },
  {
    path: "/songListAlbum",
    name: "songListAlbum",
    component: () => import('@/pages/SongListAlbum'),
    meta: {
      title: '歌单详情',
    },
  },
  {
    path: "/leaderBoard",
    name: "leaderBoard",
    component: () => import('@/pages/LeaderBoard'),
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
