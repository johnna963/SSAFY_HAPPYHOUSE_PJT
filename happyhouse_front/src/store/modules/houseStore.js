import {
  sidoList,
  gugunList,
  dongList,
  AptList,
  houseList,
} from "@/api/house.js";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    dongs: [{ value: null, text: "선택하세요" }],
    houses: [
      {
        aptCode: null,
        aptName: null,
        dongName: null,
        siguCode: null,
        jibun: null,
        recentPrice: null,
      },
    ],
    dongHouses: [],
    house: [],
  },

  getters: {},

  mutations: {
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST: (state, dongs) => {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_DONG_LIST: (state) => {
      state.dongs = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_APT_LIST: (state) => {
      state.houses = [
        {
          aptCode: null,
          aptName: null,
          siguCode: null,
          dongName: null,
          jibun: null,
          recentPrice: null,
        },
      ];
    },
    CLEAR_HOUSE_DETAIL: (state) => {
      state.house = [];
    },
    SET_HOUSE_LIST: (state, houses) => {
      for (var i = 0; i < houses.length; i++) {
        var jsonObj = new Object();
        jsonObj.aptCode = houses[i].일련번호;
        jsonObj.aptName = houses[i].아파트;
        jsonObj.siguCode = houses[i].법정동시군구코드;
        jsonObj.dongName = houses[i].법정동;
        jsonObj.jibun = houses[i].지번;
        jsonObj.recentPrice = houses[i].거래금액;
        state.houses.push(jsonObj);
      }
      console.log("시도하우스 리스트");
      console.log(state.houses);
    },
    SET_DONGAPT_LIST: (state, dongHouses) => {
      for (var i = 0; i < dongHouses.length; i++) {
        var jsonObj = new Object();
        jsonObj.aptCode = dongHouses[i].aptCode;
        jsonObj.aptName = dongHouses[i].aptName;
        jsonObj.siguCode = dongHouses[i].dongCode.substring(0, 5);
        jsonObj.dongName = dongHouses[i].dongName;
        jsonObj.jibun = dongHouses[i].jibun;
        jsonObj.recentPrice = dongHouses[i].recentPrice;
        state.houses.push(jsonObj);
      }
      console.log("동하우스리스트");
      console.log(state.houses);
      state.dongHouses = dongHouses;
    },
    SET_DETAIL_HOUSE: (state, house) => {
      state.house = house;
    },
  },

  actions: {
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          console.log("getSide");
          console.log(data);
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGugun: ({ commit }, sidoCode) => {
      const params = {
        sido: sidoCode,
      };
      gugunList(
        params,
        ({ data }) => {
          console.log("getGugun");
          // console.log(data);
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDong: ({ commit }, gugunCode) => {
      const params = {
        gugun: gugunCode,
      };
      dongList(
        params,
        ({ data }) => {
          console.log("getDong");
          console.log(data);
          commit("SET_DONG_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getHouseList: ({ commit }, gugunCode) => {
      // vue cli enviroment variables 검색
      //.env.local file 생성.
      // 반드시 VUE_APP으로 시작해야 한다.
      const SERVICE_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;
      // VUE_APP_APT_DEAL_API_KEY=9Xo0vlglWcOBGUDxH8PPbuKnlBwbWU6aO7%2Bk3FV4baF9GXok1yxIEF%2BIwr2%2B%2F%2F4oVLT8bekKU%2Bk9ztkJO0wsBw%3D%3D
      //   const SERVICE_KEY =
      //     "9Xo0vlglWcOBGUDxH8PPbuKnlBwbWU6aO7%2Bk3FV4baF9GXok1yxIEF%2BIwr2%2B%2F%2F4oVLT8bekKU%2Bk9ztkJO0wsBw%3D%3D";
      const params = {
        LAWD_CD: gugunCode,
        DEAL_YMD: "202110",
        serviceKey: decodeURIComponent(SERVICE_KEY),
      };
      houseList(
        params,
        (response) => {
          console.log("gugunHouse");
          console.log(response.data.response.body.items.item);
          // console.log(response.data.response.body.items.item[0].법정동);
          commit("SET_HOUSE_LIST", response.data.response.body.items.item);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDongHouseList: ({ commit }, dongCode) => {
      const params = {
        dong: dongCode,
      };
      AptList(
        params,
        ({ data }) => {
          console.log("getDongApt");
          console.log(data);
          commit("SET_DONGAPT_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    detailHouse: ({ commit }, house) => {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_DETAIL_HOUSE", house);
    },
  },
};

export default houseStore;
