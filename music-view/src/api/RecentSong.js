import axios from "../plugins/axios";

export function addRecentSong(RecentSong) {
  return axios({
    url: "recentSong/add",
    method: "POST",
    data: {
      ...RecentSong,
    },
  });
}

export function getRecentSongByUserId(id) {
  return axios({
    url: `recentSong/recentSongOfUserId/${id}`,
    method: "GET",
  });
}

export function recommendSongList(id) {
  return axios({
    url: `recentSong/recommendSongList/${id}`,
    method: "GET",
  });
}

export function recommendSinger(id) {
  return axios({
    url: `recentSong/recommendSinger/${id}`,
    method: "GET",
  });
}
