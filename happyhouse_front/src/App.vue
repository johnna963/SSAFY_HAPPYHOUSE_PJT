<template>
  <v-app>
    <navi-bar />
    <router-view />
  </v-app>
</template>

<script>
import NaviBar from "./components/layout/NaviBar.vue";
export default {
  name: "App",
  components: {
    NaviBar,
  },
};
</script>
<style>
a:hover {
  text-decoration: none;
  font-weight: bold;
}

a.router-link-exact-active {
  color: #42b983;
}
</style>
