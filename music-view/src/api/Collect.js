import axios from "../plugins/axios";

export function addCollect(Collect) {
  return axios({
    url: "collect/add",
    method: "POST",
    data: {
      ...Collect,
    },
  });
}

export function cancelCollect(CollectVo) {
  return axios({
    url: "collect/cancel",
    method: "DELETE",
    data: {
      ...CollectVo,
    },
  });
}

export function getCollectBySongId(CollectVo) {
  return axios({
    url: "collect/getCollectBySongId",
    method: "POST",
    data: {
      ...CollectVo,
    },
  });
}

export function getAllCollectByConsumerId(userId) {
  return axios({
    url: `collect/collectOfConsumerId/${userId}`,
    method: "GET",
  });
}

export function getCollectBySongListId(CollectVo) {
  return axios({
    url: "collect/getCollectBySongListId",
    method: "POST",
    data: {
      ...CollectVo,
    },
  });
}

export function getAllCollectSongListByUserId(userId) {
  return axios({
    url: `collect/collectSongListOfUserId/${userId}`,
    method: "GET",
  });
}
