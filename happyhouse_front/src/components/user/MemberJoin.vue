<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>회원 가입</h3></b-alert>
      </b-col>
    </b-row>
    <user-register-form type="register" />
  </b-container>
</template>

<script>
import UserRegisterForm from "./child/UserRegisterForm.vue";

export default {
  name: "MemberJoin",
  components: {
    UserRegisterForm,
  },
};
</script>

<style></style>
