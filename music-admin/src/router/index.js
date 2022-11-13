import Vue from "vue";
import Router from "vue-router";
import LoginPage from "../pages/LoginPage";
import Home from "../components/Home";
import InfoPage from "../pages/InfoPage";
import ConsumerPage from "../pages/ConsumerPage";
import SingerPage from "../pages/SingerPage";
import SongListPage from "../pages/SongListPage";
import SongPage from "../pages/SongPage";
import ListSongPage from "../pages/ListSongPage";
import Collect from "../pages/Collect";
import Comment from "../pages/Comment";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      component: LoginPage,
    },
    {
      path: "/Home",
      component: Home,
      meta: {
        requireAuth: true,
      },
      children: [
        {
          path: "/Info",
          component: InfoPage,
          meta: {
            requireAuth: true,
          },
        },
        {
          path: "/Consumer",
          component: ConsumerPage,
          meta: {
            requireAuth: true,
          },
        },
        {
          path: "/Singer",
          component: SingerPage,
          meta: {
            requireAuth: true,
          },
        },
        {
          path: "/SongList",
          component: SongListPage,
          meta: {
            requireAuth: true,
          },
        },
        {
          path: "/Song",
          component: SongPage,
          meta: {
            requireAuth: true,
          },
        },
        {
          path: "/ListSong",
          component: ListSongPage,
          meta: {
            requireAuth: true,
          },
        },
        {
          path: "/collect",
          component: Collect,
          meta: {
            requireAuth: true,
          },
        },
        {
          path: "/comment",
          component: Comment,
          meta: {
            requireAuth: true,
          },
        },
      ],
    },
  ],
});
