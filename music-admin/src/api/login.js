import axios from "../plugins/axios";

export function login(Admin) {
  return axios({
    url: "login",
    method: "POST",
    data: {
      ...Admin,
    },
  });
}

export function logout() {
  return axios({
    url: "logout",
    method: "POST",
  });
}
