<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>공지사항 목록</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-right" v-if="userInfo.userid == 'admin'">
        <b-button variant="outline-primary" @click="moveWrite()"
          >글쓰기</b-button
        >
      </b-col>
    </b-row>
    <b-row>
      <b-col v-if="notices.length">
        <b-table-simple hover responsive>
          <b-thead head-variant="dark">
            <b-tr>
              <b-th>글번호</b-th>
              <b-th>제목</b-th>
              <b-th>조회수</b-th>
              <b-th>작성자</b-th>
              <b-th>작성일</b-th>
            </b-tr>
          </b-thead>
          <tbody>
            <!-- 하위 component인 ListRow에 데이터 전달(props) -->
            <notice-list-row
              v-for="(notice, index) in notices"
              :key="index"
              v-bind="notice"
            />
          </tbody>
        </b-table-simple>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import NoticeListRow from "@/components/notice/child/NoticeListRow";
import { listNotice } from "@/api/notice.js";
import { mapState, mapMutations } from "vuex";

const memberStore = "memberStore";

export default {
  name: "NoticeList",
  components: {
    NoticeListRow,
  },
  data() {
    return {
      notices: [],
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    let param = {
      pg: 1,
      spp: 20,
      key: null,
      word: null,
    };
    listNotice(
      param,
      (response) => {
        this.notices = response.data;
      },
      (error) => {
        console.log(error);
      }
    );
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    moveWrite() {
      this.$router.push({ name: "NoticeWrite" });
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>
