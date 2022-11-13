import axios from "../plugins/axios";

export function login(loginInfoDto) {
  return axios({
    url: "admin/login/status",
    method: "POST",
    data: {
      ...loginInfoDto,
    },
  });
}
