<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>회원 목록</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col v-if="members.length">
        <b-table-simple hover responsive>
          <b-thead head-variant="dark">
            <b-tr>
              <b-th>ID</b-th>
              <b-th>이름</b-th>
              <b-th>비밀번호</b-th>
              <b-th>이메일</b-th>
              <b-th>가입일</b-th>
              <b-th></b-th>
            </b-tr>
          </b-thead>
          <tbody>
            <!-- 하위 component인 ListRow에 데이터 전달(props) -->
            <member-list-row
              v-for="(member, index) in members"
              :key="index"
              v-bind="member"
            />
          </tbody>
        </b-table-simple>
      </b-col>
      <!-- <b-col v-else class="text-center">도서 목록이 없습니다.</b-col> -->
    </b-row>
  </b-container>
</template>

<script>
import MemberListRow from "@/components/user/child/MemberListRow";
import { listMember } from "@/api/member.js";

export default {
  name: "MemberList",
  components: {
    MemberListRow,
  },
  data() {
    return {
      members: [],
    };
  },
  created() {
    let param = {
      pg: 1,
      spp: 20,
      key: null,
      word: null,
    };
    listMember(
      param,
      (response) => {
        this.members = response.data;
      },
      (error) => {
        console.log(error);
      }
    );
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
