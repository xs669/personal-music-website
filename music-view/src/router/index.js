import Vue from "vue";
import Router from "vue-router";
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

Vue.use(Router);

// eslint-disable-next-line no-undef
const originalPush = Router.prototype.push;

// eslint-disable-next-line no-undef
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

export default new Router({
  routes: [
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
        requireAuth: true,
      },
    },
    {
      path: "/singer",
      name: "singer",
      component: Singer,
    },
    {
      path: "/songList",
      name: "songList",
      component: SongList,
    },
    {
      path: "/search",
      name: "search",
      component: Search,
    },
    {
      path: "/lyric",
      name: "lyric",
      component: Lyric,
    },
    {
      path: "/login",
      name: "login",
      component: Login,
    },
    {
      path: "/register",
      name: "register",
      component: Register,
    },
    {
      path: "/setting",
      name: "setting",
      component: Setting,
      meta: {
        requireAuth: true,
      },
    },
    {
      path: "/singerAlbum",
      name: "singerAlbum",
      component: SingerAblum,
    },
    {
      path: "/songListAlbum",
      name: "songListAlbum",
      component: SongListAblum,
    },
    {
      path: "/leaderBoard",
      name: "leaderBoard",
      component: LeaderBoard,
    },
  ],
  scrollBehavior() {
    return { x: 0, y: 0 };
  },
});
