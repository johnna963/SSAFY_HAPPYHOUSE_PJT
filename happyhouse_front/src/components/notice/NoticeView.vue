<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>공지사항</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="listNotice">목록</b-button>
      </b-col>
      <b-col class="text-right" v-if="userInfo.userid == 'admin'">
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveModifyNotice"
          class="mr-2"
          >글수정</b-button
        >
        <b-button variant="outline-danger" size="sm" @click="removeNotice"
          >글삭제</b-button
        >
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h3>${notice.articleno}.
          ${notice.subject} [${notice.hit}]</h3><div><h6>${notice.userid}</div><div>${notice.regtime}</h6></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
// import moment from "moment";
import { getNotice, deleteNotice } from "@/api/notice";
import { mapState, mapMutations } from "vuex";

const memberStore = "memberStore";

export default {
  data() {
    return {
      notice: {},
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    message() {
      if (this.notice.content)
        return this.notice.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    getNotice(
      this.$route.params.articleno,
      (response) => {
        this.notice = response.data;
      },
      (error) => {
        console.log("삭제시 에러발생!!", error);
      }
    );
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    listNotice() {
      this.$router.push({ name: "NoticeList" });
    },
    moveModifyNotice() {
      this.$router.replace({
        name: "NoticeUpdate",
        params: { articleno: this.notice.articleno },
      });
    },
    removeNotice() {
      if (confirm("정말로 삭제?")) {
        deleteNotice(this.notice.articleno, () => {
          this.$router.push({ name: "NoticeList" });
        });
      }
    },
  },
};
</script>

<style></style>
