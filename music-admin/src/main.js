import Vue from "vue";
import App from "./App";
import router from "./router/index";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import "./assets/css/main.css";
import VePie from "v-charts/lib/pie.common";
import VeHistogram from "v-charts/lib/histogram.common";
import axios from "axios";
import store from "./store/store";

Vue.prototype.axios = axios;
Vue.use(ElementUI);
Vue.component(VePie.name, VePie);
Vue.component(VeHistogram.name, VeHistogram);

// 登录拦截
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    let username = localStorage.getItem("username");
    let token = localStorage.getItem("token");
    axios.get(`/api/checkLoginState/${username}`).then((res) => {
      if (res.data.data) {
        if (username && token) {
          next();
        } else {
          next({
            // 返回登录页面
            path: "/",
          });
        }
      } else {
        next({
          // 返回登录页面
          path: "/",
        });
      }
    });
  } else {
    next();
  }
});

// eslint-disable-next-line no-new
new Vue({
  el: "#app",
  router,
  store,
  render: (h) => h(App),
});
