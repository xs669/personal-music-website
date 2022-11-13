import axios from "../plugins/axios";

export function addComment(Comment) {
  return axios({
    url: "comment/add",
    method: "POST",
    data: {
      ...Comment,
    },
  });
}

export function updateCommentUp(CommentDto) {
  return axios({
    url: "comment/updateCommentUp",
    method: "PUT",
    data: {
      ...CommentDto,
    },
  });
}

export function cancelCommentUp(CommentDto) {
  return axios({
    url: "comment/cancelCommentUp",
    method: "PUT",
    data: {
      ...CommentDto,
    },
  });
}

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
