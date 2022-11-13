import axios from "../plugins/axios";

export function getAllCommentBySongId(songId) {
  return axios({
    url: `comment/commentOfSongId/${songId}`,
    method: "GET",
  });
}

export function getAllCommentBySongListId(songListId) {
  return axios({
    url: `comment/commentOfSongListId/${songListId}`,
    method: "GET",
  });
}

export function deleteComment(id) {
  return axios({
    url: `comment/delete/${id}`,
    method: "DELETE",
  });
}

export function deleteAllComment(ids) {
  return axios({
    url: `comment/deleteAll/${ids}`,
    method: "DELETE",
  });
}

export function getAllCommentByUserId(userId) {
  return axios({
    url: `comment/commentOfUserId/${userId}`,
    method: "GET",
  });
}

export function searchComment(searchVo) {
  return axios({
    url: "/comment/searchComment",
    method: "POST",
    data: {
      ...searchVo,
    },
  });
}
