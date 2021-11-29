<template>
  <!-- 카카오맵 Vue -->
  <div>
    <div class="map-area">
      <div class="searchbox">
        <div>
          <input type="text" value="이태원" @keyup.enter="searchPlaces" />
        </div>
        <div class="results">
          <div
            class="place"
            v-for="rs in search.results"
            :key="rs.id"
            @click="clickmove(rs)"
          >
            <h4>{{ rs.place_name }}</h4>
            <div class="addr">{{ rs.address_name }}</div>
          </div>
        </div>
      </div>
    </div>
    <div id="map" style=" width: 100% height: 500px"></div>
    <div hidden>
      {{ houses }}
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const houseStore = "houseStore";
export default {
  name: "KakaoMap",
  data() {
    return {
      map: null,
      infowindow: null,
      markers: [],
      hmarkers: [],
      donginfo: null,
      container: Document,
      search: {
        keyword: null,
        pgn: null,
        results: [],
      },
      mapOption: {
        center: {
          lat: 33.450701,
          lng: 126.570667,
        },
        level: 8,
      },
    };
  },
  mounted() {
    window.kakao && window.kakao.maps ? this.initMap() : this.addScript();
  },
  updated() {
    this.addMarker(this.houses);
  },
  computed: {
    ...mapState(houseStore, ["dongHouses", "houses", "sigu"]),
  },
  methods: {
    ...mapActions(houseStore, ["getAddress"]),
    initMap() {
      this.container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(37.501253654080806, 127.0396131285909),
        level: 3,
      };
      this.map = new kakao.maps.Map(this.container, options);
      // console.log(this.map);
      //마커추가하려면 객체를 아래와 같이 하나 만든다.
      var marker = new kakao.maps.Marker({
        position: this.map.getCenter(),
      });
      this.infowindow = new kakao.maps.InfoWindow({ zindex: 1 });
      marker.setMap(this.map);
      this.hmarkers.push(marker);
    },
    addScript() {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=b914745728b6d25feea40ef781b3546e&libraries=services";
      document.head.appendChild(script);
    },
    addMarker(houses) {
      // var mapOption = {
      //   center: new kakao.maps.LatLng(33.450701, 126.570667),
      //   level: 4,
      // };
      var map_this = this.map;
      var initthis = this;
      // this.map = new kakao.maps.Map(this.container, mapOption);
      var geocoder = new kakao.maps.services.Geocoder();
      var imageSrc =
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      var address;
      this.removehMarker();
      if (houses.length == 0) {
        address = "서울특별시";
        geocoder.addressSearch(address, function (result, status) {
          if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            var marker = new kakao.maps.Marker({
              map: map_this,
              position: coords,
              clickable: true,
            });
            var infowindow = new kakao.maps.InfoWindow({
              content:
                '<div style="width:250px;height:50px;text-align:center;padding:6px 0;">' +
                "해당 도시에 거래 내역이 없습니다." +
                "</div>",
              removable: true,
            });
            kakao.maps.event.addListener(marker, "click", function () {
              infowindow.open(map_this, marker);
            });
            marker.setMap(map_this);
            map_this.setCenter(coords);
          }
        });
      } else {
        for (let i = 1; i < houses.length; i++) {
          const list = houses[i];
          var imageSize = new kakao.maps.Size(24, 35);
          var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
          address = list.dongName + " " + list.jibun + " ";
          this.donginfo = list.dongName;
          geocoder.addressSearch(address, function (result, status) {
            if (status === kakao.maps.services.Status.OK) {
              var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
              var marker = new kakao.maps.Marker({
                map: map_this,
                position: coords,
                image: markerImage,
                title: list.aptName,
                clickable: true,
              });
              var infowindow = new kakao.maps.InfoWindow({
                content:
                  "<div style='width:250px; height:50px;text-align:center;padding:6px 0;'>" +
                  address +
                  " " +
                  list.aptName +
                  "</div>",
                removable: true,
              });
              kakao.maps.event.addListener(marker, "click", function () {
                infowindow.open(map_this, marker);
              });
              marker.setMap(map_this);
              initthis.hmarkers.push(marker);
              map_this.setCenter(coords);
            }
          });
        }
      }
    },
    // 검색기능
    searchPlaces(e) {
      // console.log(e.target.value);
      const keyword = e.target.value.trim();
      if (keyword.length === 0) {
        return;
      }
      const ps = new window.kakao.maps.services.Places();
      ps.keywordSearch(this.donginfo + keyword, (data, status, pgn) => {
        this.search.keyword = keyword;
        this.search.pgn = pgn;
        this.search.results = data;
        this.showPlace(data);
      });
    },
    showPlace(place) {
      console.log("실행");
      console.log(place);
      var bounds = new kakao.maps.LatLngBounds();
      this.removeMarker();
      for (var i = 0; i < place.length; i++) {
        var placePos = new kakao.maps.LatLng(place[i].y, place[i].x);
        this.addSearchMarker(place[i], placePos, i);
        bounds.extend(placePos);
      }
      this.map.setBounds(bounds);
    },
    addSearchMarker(place, position, idx) {
      var init = this;
      var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          map: this.map,
          position: position, // 마커의 위치
          image: markerImage,
          clickable: true,
        });
      var infowindow = new kakao.maps.InfoWindow({
        content:
          "<div style='width:250px;height:100px;text-align:center;padding:6px 0;'>" +
          place.place_name +
          "<br>" +
          place.category_group_name +
          "</div>",
        removable: true,
      });
      kakao.maps.event.addListener(marker, "click", function () {
        infowindow.open(init.map, marker);
      });
      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다
      return marker;
    },
    clickmove(place) {
      var coords = new kakao.maps.LatLng(place.y, place.x);
      this.map.setCenter(coords);
    },
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    removehMarker() {
      for (var i = 0; i < this.hmarkers.length; i++) {
        this.hmarkers[i].setMap(null);
      }
      this.hmarkers = [];
    },
  },
};
</script>
<style>
#map {
  width: 100%;
  height: 600px;
}
.map-area {
  display: flex;
  position: relative;
}
.searchbox {
  position: absolute;
  top: 0;
  left: 0;
  height: 600px;
  z-index: 10000;
  background-color: #ffffffaa;
  width: 300px;
  display: flex;
  flex-direction: column;
}
.results {
  flex: 1 1 auto;
  overflow-y: auto;
}
.place {
  padding: 8px;
  cursor: pointer;
}
.h4 {
  margin: 0;
}
&:hover {
  background-color: aliceblue;
}
</style>
