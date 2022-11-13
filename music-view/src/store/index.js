import Vue from "vue";
import Vuex from "vuex";
import configure from "./configure";
import song from "./song";
import user from "./user";
Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    configure,
    song,
    user,
  },
});

export default store;
