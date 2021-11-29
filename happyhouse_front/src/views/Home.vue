<template>
  <v-container fluid style="width: 100%">
    <v-carousel v-model="model" cycle height="600px">
      <v-carousel-item v-for="img in imgs" :key="img">
        <v-sheet color="#e0eeee" height="600px" tile>
          <img :src="img" fluid style="width: 100%" />
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
  </v-container>
</template>

<script>
export default {
  name: "Main",
  data: () => ({
    model: 0,
    imgs: [
      require("@/assets/home1.jpeg"),
      require("@/assets/home2.jpeg"),
      require("@/assets/home3.png"),
    ],
  }),
};
</script>

<style scoped></style>
