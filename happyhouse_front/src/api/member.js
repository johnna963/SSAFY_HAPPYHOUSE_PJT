import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

// 회원가입
function registerUser(user, success, fail) {
  api.post(`/user/signup`, JSON.stringify(user)).then(success).catch(fail);
}

// 회원 정보 수정
function modifyUser(user, success, fail) {
  api.put(`/user/modify`, JSON.stringify(user)).then(success).catch(fail);
}

// 탈퇴
function deleteUser(userid, success, fail) {
  api.delete(`/user/delete/${userid}`).then(success).catch(fail);
}
// function logout(success, fail)

// 비밀번호 찾기
function findPw(userid, success, fail) {
  api.get(`/user/findPassword/${userid}`).then(success).catch(fail);
}

// 회원 정보 조회
function listMember(param, success, fail) {
  api.get(`/user`, { params: param }).then(success).catch(fail);
}

export {
  login,
  findById,
  registerUser,
  modifyUser,
  deleteUser,
  findPw,
  listMember,
};
