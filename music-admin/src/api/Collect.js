import axios from "../plugins/axios";

export function getAllCollectByUserId(userId) {
  return axios({
    url: `collect/collectOfUserId/${userId}`,
    method: "GET",
  });
}

export function delCollect(id) {
  return axios({
    url: `collect/del/${id}`,
    method: "DELETE",
  });
}

export function delAllCollect(ids) {
  return axios({
    url: `collect/delAll/${ids}`,
    method: "DELETE",
  });
}

export function searchCollectByUserId(searchDto) {
  return axios({
    url: "collect/searchOfUserId",
    method: "POST",
    data: {
      ...searchDto,
    },
  });
}
