import axios from "../plugins/axios";

export function getSongNameBySingerId(singerId) {
  return axios({
    url: `listSong/songOfName/${singerId}`,
    method: "GET",
  });
}

export function getAllListSongBySongListId(id) {
  return axios({
    url: `listSong/all/${id}`,
    method: "GET",
  });
}

export function addListSong(ListSongVo) {
  return axios({
    url: "listSong/add",
    method: "POST",
    data: {
      ...ListSongVo,
    },
  });
}

export function deleteAllListSong(ids) {
  return axios({
    url: `listSong/deleteAll/${ids}`,
    method: "DELETE",
  });
}

export function deleteListSong(id) {
  return axios({
    url: `listSong/delete/${id}`,
    method: "DELETE",
  });
}

export function getListSongByName(ListSongByNameDto) {
  return axios({
    url: "listSong/listSongOfName",
    method: "POST",
    data: {
      ...ListSongByNameDto,
    },
  });
}
