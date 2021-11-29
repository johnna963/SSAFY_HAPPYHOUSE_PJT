<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form @submit="onSubmit" class="text-left">
            <b-form-group label="아이디:" label-for="userid">
              <b-form-input
                id="userid"
                v-model="user.userid"
                type="text"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이름:" label-for="username">
              <b-form-input
                id="username"
                v-model="user.username"
                type="text"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                id="userpwd"
                v-model="user.userpwd"
                type="password"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="email"
                v-model="user.email"
                type="email"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="submit"
              variant="primary"
              class="m-1"
              v-if="this.type === 'register'"
              >회원가입</b-button
            >
            <b-button type="submit" variant="primary" class="m-1" v-else
              >정보 수정</b-button
            >
            <router-link :to="{ name: 'Home' }" class="link"
              ><b-button variant="danger" class="m-1"
                >돌아가기</b-button
              ></router-link
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { registerUser } from "@/api/member";

export default {
  name: "UserRegisterForm",
  data() {
    return {
      user: {
        userid: "",
        username: "",
        userpwd: "",
        email: "",
      },
    };
  },
  props: {
    type: { type: String },
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      if (this.user.userid == "") {
        alert("아이디를 입력하세요.");
        return;
      }
      if (this.user.username == "") {
        alert("이름을 입력하세요.");
        return;
      }
      if (this.user.userpwd == "") {
        alert("비밀번호를 입력하세요.");
        return;
      }
      if (this.user.email == "") {
        alert("email을 입력하세요.");
        return;
      } else {
        this.type === "register" ? this.registMember() : this.updateMember();
      }
    },
    registMember() {
      registerUser(
        {
          userid: this.user.userid,
          username: this.user.username,
          userpwd: this.user.userpwd,
          email: this.user.email,
        },
        ({ data }) => {
          let msg = " 회원가입을 실패했습니다.";
          if (data === "success") {
            msg = " 회원가입 성공! ";
          }
          alert(msg);
          this.$router.push({ name: "SignIn" });
        }
      );
    },
  },
};
</script>

<style></style>
