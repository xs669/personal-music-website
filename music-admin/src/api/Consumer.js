import axios from "../plugins/axios";

export function getAllConsumer() {
  return axios({
    url: "consumer/all",
    method: "GET",
  });
}

export function updateConsumer(Consumer) {
  return axios({
    url: "consumer/update",
    method: "PUT",
    data: {
      ...Consumer,
    },
  });
}

export function getConsumerByName(name) {
  return axios({
    url: `consumer/consumerOfName/${name}`,
    method: "GET",
  });
}

export function addConsumer(Consumer) {
  return axios({
    url: "consumer/add",
    method: "POST",
    data: {
      ...Consumer,
    },
  });
}

export function deleteConsumer(id) {
  return axios({
    url: `consumer/delete/${id}`,
    method: "DELETE",
  });
}

export function deleteAllConsumer(ids) {
  return axios({
    url: `consumer/deleteAll/${ids}`,
    method: "DELETE",
  });
}

export function getConsumerCount() {
  return axios({
    url: "consumer/getCount",
    method: "GET",
  });
}

export function getConsumerCountBySex() {
  return axios({
    url: "consumer/getCountBySex",
    method: "GET",
  });
}
