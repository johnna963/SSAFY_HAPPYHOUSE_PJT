<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>정보 수정</h3></b-alert>
      </b-col>
    </b-row>
    <user-modify-form type="modify" />
  </b-container>
</template>

<script>
import UserModifyForm from "./child/UserModifyForm.vue";

export default {
  name: "MemberModify",
  components: {
    UserModifyForm,
  },
};
</script>

<style></style>
