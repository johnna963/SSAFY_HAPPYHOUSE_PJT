<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>비밀번호 찾기</h3></b-alert>
      </b-col>
    </b-row>
    <user-find-password-form type="findPw" />
  </b-container>
</template>

<script>
import UserFindPasswordForm from "./child/UserFindPasswordForm.vue";

export default {
  name: "MemberFindPw",
  components: {
    UserFindPasswordForm,
  },
};
</script>

<style></style>
