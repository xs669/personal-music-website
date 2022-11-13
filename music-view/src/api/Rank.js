import axios from "../plugins/axios";

export function addRank(Rank) {
  return axios({
    url: "rank/add",
    method: "POST",
    data: {
      ...Rank,
    },
  });
}

export function getRankAvgScore(songListId) {
  return axios({
    url: `rank/getAvgScore/${songListId}`,
    method: "GET",
  });
}
