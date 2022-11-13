import axios from "../plugins/axios";

export function getAllSong() {
  return axios({
    url: "song/all",
    method: "GET",
  });
}

export function addSong(Song) {
  return axios({
    url: "song/add",
    method: "POST",
    data: {
      ...Song,
    },
  });
}

export function getAllSongBySingerId(id) {
  return axios({
    url: `song/allSong/${id}`,
    method: "GET",
  });
}

export function getAllSongByName(name) {
  return axios({
    url: `song/songOfName/${name}`,
    method: "GET",
  });
}

export function updateSong(Song) {
  return axios({
    url: "song/update",
    method: "PUT",
    data: {
      ...Song,
    },
  });
}

export function deleteSong(id) {
  return axios({
    url: `song/delete/${id}`,
    method: "DELETE",
  });
}

export function deleteAllSong(ids) {
  return axios({
    url: `song/deleteAll/${ids}`,
    method: "DELETE",
  });
}

export function getSongCount() {
  return axios({
    url: "song/getCount",
    method: "GET",
  });
}

export function download(url) {
  return axios({
    url: url,
    method: "GET",
    responseType: "blob",
  });
}

export function getAllSongBySongListId(id) {
  return axios({
    url: `song/all/${id}`,
    method: "GET",
  });
}

export function gerAllSongAndSingerNameBySingerId(singerId) {
  return axios({
    url: `song/songAndSingerName/${singerId}`,
    method: "GET",
  });
}

export function getSongByPlayCount() {
  return axios({
    url: "song/songOfPlayCount",
    method: "GET",
  });
}

export function addPlayCount(SongVo) {
  return axios({
    url: "song/addPlayCount",
    method: "POST",
    data: {
      ...SongVo,
    },
  });
}
