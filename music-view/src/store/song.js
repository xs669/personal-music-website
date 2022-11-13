const song = {
  state: {
    listOfSongs: [],
    isPlay: false,
    url: "",
    id: "",
    playButtonUrl: "#icon-bofang",
    duration: 0,
    curTime: 0,
    changeTime: 0,
    title: 0,
    artist: "",
    picUrl: "",
    autoNext: true,
    lyric: [],
    tempList: {},
    listIndex: "",
    volume: 50,
    introduction: "",
  },
  getters: {
    introduction: (state) => {
      state.introduction = JSON.parse(
        window.sessionStorage.getItem("introduction")
      );
      return state.introduction;
    },
    listOfSongs: (state) => {
      state.listOfSongs = JSON.parse(
        window.sessionStorage.getItem("listOfSongs")
      );
      return state.listOfSongs;
    },
    isPlay: (state) => {
      state.isPlay = JSON.parse(window.sessionStorage.getItem("isPlay"));
      return state.isPlay;
    },
    url: (state) => {
      state.url = JSON.parse(window.sessionStorage.getItem("url"));
      return state.url;
    },
    id: (state) => {
      state.id = JSON.parse(window.sessionStorage.getItem("id"));
      return state.id;
    },
    playButtonUrl: (state) => {
      state.playButtonUrl = JSON.parse(
        window.sessionStorage.getItem("playButtonUrl")
      );
      return state.playButtonUrl;
    },
    duration: (state) => {
      state.duration = JSON.parse(window.sessionStorage.getItem("duration"));
      return state.duration;
    },
    curTime: (state) => {
      state.curTime = JSON.parse(window.sessionStorage.getItem("curTime"));
      return state.curTime;
    },
    changeTime: (state) => {
      state.changeTime = JSON.parse(
        window.sessionStorage.getItem("changeTime")
      );
      return state.changeTime;
    },
    title: (state) => {
      state.title = JSON.parse(window.sessionStorage.getItem("title"));
      return state.title;
    },
    artist: (state) => {
      state.artist = JSON.parse(window.sessionStorage.getItem("artist"));
      return state.artist;
    },
    picUrl: (state) => {
      state.picUrl = JSON.parse(window.sessionStorage.getItem("picUrl"));
      return state.picUrl;
    },
    autoNext: (state) => {
      state.autoNext = JSON.parse(window.sessionStorage.getItem("autoNext"));
      return state.autoNext;
    },
    lyric: (state) => {
      state.lyric = JSON.parse(window.sessionStorage.getItem("lyric"));
      return state.lyric;
    },
    tempList: (state) => {
      state.tempList = JSON.parse(window.sessionStorage.getItem("tempList"));
      return state.tempList;
    },
    listIndex: (state) => {
      state.listIndex = JSON.parse(window.sessionStorage.getItem("listIndex"));
      return state.listIndex;
    },
    volume: (state) => {
      state.volume = JSON.parse(window.sessionStorage.getItem("volume"));
      return state.volume;
    },
  },
  mutations: {
    setIntroduction: (state, introduction) => {
      state.introduction = introduction;
      window.sessionStorage.setItem(
        "introduction",
        JSON.stringify(introduction)
      );
    },
    setListOfSongs: (state, listOfSongs) => {
      state.listOfSongs = listOfSongs;
      window.sessionStorage.setItem("listOfSongs", JSON.stringify(listOfSongs));
    },
    setIsPlay: (state, isPlay) => {
      state.isPlay = isPlay;
      window.sessionStorage.setItem("isPlay", JSON.stringify(isPlay));
    },
    setUrl: (state, url) => {
      state.url = url;
      window.sessionStorage.setItem("url", JSON.stringify(url));
    },
    setId: (state, id) => {
      state.id = id;
      window.sessionStorage.setItem("id", JSON.stringify(id));
    },
    setPlayButtonUrl: (state, playButtonUrl) => {
      state.playButtonUrl = playButtonUrl;
      window.sessionStorage.setItem(
        "playButtonUrl",
        JSON.stringify(playButtonUrl)
      );
    },
    setDuration: (state, duration) => {
      state.duration = duration;
      window.sessionStorage.setItem("duration", JSON.stringify(duration));
    },
    setCurTime: (state, curTime) => {
      state.curTime = curTime;
      window.sessionStorage.setItem("curTime", JSON.stringify(curTime));
    },
    setChangeTime: (state, changeTime) => {
      state.changeTime = changeTime;
      window.sessionStorage.setItem("changeTime", JSON.stringify(changeTime));
    },
    setTitle: (state, title) => {
      state.title = title;
      window.sessionStorage.setItem("title", JSON.stringify(title));
    },
    setArtist: (state, artist) => {
      state.artist = artist;
      window.sessionStorage.setItem("artist", JSON.stringify(artist));
    },
    setPicUrl: (state, picUrl) => {
      state.picUrl = picUrl;
      window.sessionStorage.setItem("picUrl", JSON.stringify(picUrl));
    },
    setAutoNext: (state, autoNext) => {
      state.autoNext = autoNext;
      window.sessionStorage.setItem("autoNext", JSON.stringify(autoNext));
    },
    setLyric: (state, lyric) => {
      state.lyric = lyric;
      window.sessionStorage.setItem("lyric", JSON.stringify(lyric));
    },
    setTempList: (state, tempList) => {
      state.tempList = tempList;
      window.sessionStorage.setItem("tempList", JSON.stringify(tempList));
    },
    setListIndex: (state, listIndex) => {
      state.listIndex = listIndex;
      window.sessionStorage.setItem("listIndex", JSON.stringify(listIndex));
    },
    setVolume: (state, volume) => {
      state.volume = volume;
      window.sessionStorage.setItem("volume", JSON.stringify(volume));
    },
  },
};

export default song;
