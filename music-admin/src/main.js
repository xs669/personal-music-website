import Vue from "vue";
import App from "./App";
import router from "./router/index";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import "./assets/css/main.css";
import { VePie, VeHistogram, VeRing} from "v-charts/lib/index.esm";
import store from "./store/store";

Vue.use(ElementUI);
Vue.component(VePie.name, VePie);
Vue.component(VeHistogram.name, VeHistogram);
Vue.component(VeRing.name, VeRing);

// eslint-disable-next-line no-new
new Vue({
  el: "#app",
  router,
  store,
  render: (h) => h(App),
});
