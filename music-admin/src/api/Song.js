import axios from "../plugins/axios";

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

export function getAllSongByNameAndSingerId(SongVo) {
  return axios({
    url: "song/songOfName",
    method: "POST",
    data: {
      ...SongVo,
    },
  });
}
