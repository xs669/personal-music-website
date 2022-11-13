const user = {
  state: {
    collectList: [],
    control: "#icon-xunhuanbofang",
    flag: 0,
    recentSongList: [],
  },
  getters: {
    recentSongList: (state) => {
      state.recentSongList = JSON.parse(
        window.sessionStorage.getItem("recentSongList")
      );
      return state.recentSongList;
    },
    collectList: (state) => {
      state.collectList = JSON.parse(
        window.sessionStorage.getItem("collectList")
      );
      return state.collectList;
    },
    control: (state) => {
      state.control = JSON.parse(window.sessionStorage.getItem("control"));
      return state.control;
    },
    flag: (state) => {
      state.flag = JSON.parse(window.sessionStorage.getItem("flag"));
      return state.flag;
    },
  },
  mutations: {
    setRecentSongList: (state, recentSongList) => {
      state.recentSongList = recentSongList;
      window.sessionStorage.setItem(
        "recentSongList",
        JSON.stringify(recentSongList)
      );
    },
    setCollectList: (state, collectList) => {
      state.collectList = collectList;
      window.sessionStorage.setItem("collectList", JSON.stringify(collectList));
    },
    setControl: (state, control) => {
      state.control = control;
      window.sessionStorage.setItem("control", JSON.stringify(control));
    },
    setFlag: (state, flag) => {
      state.flag = flag;
      window.sessionStorage.setItem("flag", JSON.stringify(flag));
    },
  },
};

export default user;
