import Vue from "vue";
import VueRouter from "vue-router";
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import getPageTitle from '@/utils/get-page-title'
import axios from "axios";

Vue.use(VueRouter);

const routes = [
  {
    path: "/login",
    component: () => import('@/pages/LoginPage'),
  },
  {
    path: "/",
    component: () => import('@/components/Home'),
    redirect: '/Info',
    meta: {
      requireAuth: true,
    },
    children: [
      {
        path: "/Info",
        component: () => import('@/pages/InfoPage'),
        meta: {
          title: '系统首页',
          requireAuth: true,
        },
      },
      {
        path: "/Consumer",
        component: () => import('@/pages/ConsumerPage'),
        meta: {
          title: '用户管理',
          requireAuth: true,
        },
      },
      {
        path: "/Singer",
        component: () => import('@/pages/SingerPage'),
        meta: {
          title: '歌手管理',
          requireAuth: true,
        },
      },
      {
        path: "/SongList",
        component: () => import('@/pages/SongListPage'),
        meta: {
          title: '歌单管理',
          requireAuth: true,
        },
      },
      {
        path: "/Song",
        component: () => import('@/pages/SongPage'),
        meta: {
          title: '歌曲管理',
          requireAuth: true,
        },
      },
      {
        path: "/ListSong",
        component: () => import('@/pages/ListSongPage'),
        meta: {
          title: '歌单歌曲管理',
          requireAuth: true,
        },
      },
      {
        path: "/collect",
        component: () => import('@/pages/Collect'),
        meta: {
          title: '用户收藏管理',
          requireAuth: true,
        },
      },
      {
        path: "/comment",
        component: () => import('@/pages/Comment'),
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
