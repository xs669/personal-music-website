import axios from "../plugins/axios";

export function addSinger(Singer) {
  return axios({
    url: "singer/add",
    method: "POST",
    data: {
      ...Singer,
    },
  });
}

export function getAllSingerByPage(currentPage) {
  return axios({
    url: `singer/allSinger/${currentPage}`,
    method: "GET",
  });
}

export function getAllSinger() {
  return axios({
    url: "singer/allSinger",
    method: "GET",
  });
}

export function getSingerByName(name) {
  return axios({
    url: `singer/singerOfName/${name}`,
    method: "GET",
  });
}

export function updateSinger(Singer) {
  return axios({
    url: "singer/update",
    method: "PUT",
    data: {
      ...Singer,
    },
  });
}

export function deleteSinger(id) {
  return axios({
    url: `singer/delete/${id}`,
    method: "DELETE",
  });
}

export function deleteAllSinger(ids) {
  return axios({
    url: `singer/deleteAll/${ids}`,
    method: "DELETE",
  });
}

export function getSingerCount() {
  return axios({
    url: "singer/getCount",
    method: "GET",
  });
}

export function getSingerCountByLocation() {
  return axios({
    url: "singer/getCountByLocation",
    method: "GET",
  });
}

export function getSingerCountBySex() {
  return axios({
    url: "singer/getCountBySex",
    method: "GET",
  });
}
