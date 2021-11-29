<template>
  <b-tr>
    <b-td>{{ userid }}</b-td>
    <b-td>{{ username }}</b-td>
    <b-td>{{ userpwd }}</b-td>
    <b-td>{{ email }}</b-td>
    <b-td>{{ joindate }}</b-td>
    <b-td
      ><b-button variant="outline-danger" size="sm" @click="removeMember"
        >회원삭제</b-button
      ></b-td
    >
  </b-tr>
</template>

<script>
// import moment from "moment";
import { deleteUser } from "@/api/member";

export default {
  name: "MemberListRow",
  props: {
    userid: String,
    username: String,
    userpwd: String,
    email: String,
    joindate: String,
  },
  computed: {},
  methods: {
    removeMember() {
      if (confirm("정말로 삭제?")) {
        deleteUser(this.userid, () => {
          this.$router.push({ name: "Home" });
        });
      }
      alert("삭제되었습니다.");
    },
  },
};
</script>

<style></style>
