import Vue from "vue";
import VueRouter from "vue-router";
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import getPageTitle from '@/utils/get-page-title'
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
import axios from "axios";

Vue.use(VueRouter);

const routes = [
  {
    path: "/login",
    component: LoginPage,
  },
  {
    path: "/",
    component: Home,
    redirect: '/Info',
    meta: {
      requireAuth: true,
    },
    children: [
      {
        path: "/Info",
        component: InfoPage,
        meta: {
          title: '系统首页',
          requireAuth: true,
        },
      },
      {
        path: "/Consumer",
        component: ConsumerPage,
        meta: {
          title: '用户管理',
          requireAuth: true,
        },
      },
      {
        path: "/Singer",
        component: SingerPage,
        meta: {
          title: '歌手管理',
          requireAuth: true,
        },
      },
      {
        path: "/SongList",
        component: SongListPage,
        meta: {
          title: '歌单管理',
          requireAuth: true,
        },
      },
      {
        path: "/Song",
        component: SongPage,
        meta: {
          title: '歌曲管理',
          requireAuth: true,
        },
      },
      {
        path: "/ListSong",
        component: ListSongPage,
        meta: {
          title: '歌单歌曲管理',
          requireAuth: true,
        },
      },
      {
        path: "/collect",
        component: Collect,
        meta: {
          title: '用户收藏管理',
          requireAuth: true,
        },
      },
      {
        path: "/comment",
        component: Comment,
        meta: {
          title: '用户评论管理',
          requireAuth: true,
        },
      },
    ],
  },
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

NProgress.configure({ showSpinner: false })

const whiteList = ['/login']

router.beforeEach(async (to, from, next) => {
  NProgress.start()
  document.title = getPageTitle(to.meta.title)
  if (to.meta.requireAuth) {
    let token = localStorage.getItem("token")
    let username = localStorage.getItem("username")
    if (token) {
      await axios.get(`/api/checkLoginState/${username}`).then(res => {
        if (res.data.data) {
          if (to.path === '/login') {
            next({ path: '/' })
            NProgress.done()
          } else {
            next()
          }
        } else {
          localStorage.removeItem("token")
          localStorage.removeItem("username")
          next({path: '/login'})
          NProgress.done()
        }
      })
    } else {
      if (whiteList.indexOf(to.path) !== -1) {
        next()
      } else {
        next({path: '/login'})
        NProgress.done()
      }
    }
  } else {
    next()
  }

})

router.afterEach(() => {
  NProgress.done()
})

export default router
