import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);
const store = new Vuex.Store({
  state: {
    HOST: "127.0.0.1:8888",
    isPlay: false, // 判断歌曲是否在播放
    url: "", // 歌曲地址
    id: "", // 歌曲id
    isLoginIn: false,
  },
  getters: {
    isLoginIn: (state) => {
      let isLoginIn = state.isLoginIn;
      if (!isLoginIn) {
        isLoginIn = JSON.parse(window.sessionStorage.getItem("isLoginIn"));
      }
      return isLoginIn;
    },
    isPlay: (state) => state.isPlay,
    url: (state) => state.url,
    id: (state) => state.id,
  },
  mutations: {
    setSearchSongListData: (state, searchSongListData) => {
      state.searchSongListData = searchSongListData;
      window.sessionStorage.setItem(
        "searchSongListData",
        JSON.stringify(searchSongListData)
      );
    },
    setLsLoginIn: (state, isLoginIn) => {
      state.isLoginIn = isLoginIn;
      window.sessionStorage.setItem("isLoginIn", JSON.stringify(isLoginIn));
    },
    setIsPlay: (state, isPlay) => {
      state.isPlay = isPlay;
    },
    setUrl: (state, url) => {
      state.url = url;
    },
    setId: (state, id) => {
      state.id = id;
    },
  },
});

export default store;
