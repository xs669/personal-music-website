import axios from "../plugins/axios";

export function addSongList(SongList) {
  return axios({
    url: "songList/add",
    method: "POST",
    data: {
      ...SongList,
    },
  });
}

export function getAllSongListByPage(currentPage) {
  return axios({
    url: `songList/all/${currentPage}`,
    method: "GET",
  });
}

export function getSongListByName(name) {
  return axios({
    url: `songList/songListOfName/${name}`,
    method: "GET",
  });
}

export function updateSongList(SongList) {
  return axios({
    url: "songList/update",
    method: "PUT",
    data: {
      ...SongList,
    },
  });
}

export function deleteSongList(id) {
  return axios({
    url: `songList/delete/${id}`,
    method: "DELETE",
  });
}

export function deleteAllSongList(ids) {
  return axios({
    url: `songList/deleteAll/${ids}`,
    method: "DELETE",
  });
}

export function getSongListCount() {
  return axios({
    url: "songList/getCount",
    method: "GET",
  });
}

export function getSongListCountByStyle() {
  return axios({
    url: "songList/getSongListCountByStyle",
    method: "GET",
  });
}

export function getSongListByStyle(style) {
  return axios({
    url: `songList/getSongListByStyle/${style}`,
    method: "GET",
  });
}

export function getAllStyle() {
  return axios({
    url: "songList/allStyle",
    method: "GET",
  });
}
