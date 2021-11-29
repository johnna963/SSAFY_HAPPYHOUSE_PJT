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
                :placeholder="user.userid"
              ></b-form-input>
            </b-form-group>
            <b-button type="submit" variant="primary" class="m-1"
              >찾기</b-button
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
import { findPw } from "@/api/member";

export default {
  name: "UserRegisterForm",
  data() {
    return {
      user: {
        userid: "",
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
      } else {
        this.findPw();
      }
    },
    findPw() {
      var findpwd = "";
      findPw(this.user.userid, ({ data }) => {
        findpwd = data;
        let msg = " 비밀번호 찾기를 실패했습니다.";
        if (findpwd) {
          msg = " 비밀번호는 " + findpwd + " 입니다.";
        }
        alert(msg);
        this.$router.push({ name: "SignIn" });
      });
    },
  },
};
</script>

<style></style>
